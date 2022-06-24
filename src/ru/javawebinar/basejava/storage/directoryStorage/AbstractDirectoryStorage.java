package ru.javawebinar.basejava.storage.directoryStorage;

import ru.javawebinar.basejava.exception.StorageException;
import ru.javawebinar.basejava.model.Resume;
import ru.javawebinar.basejava.storage.AbstractStorage;
import ru.javawebinar.basejava.storage.serializers.Serializer;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;

/**
 * Base class for FileStorage and PathStorage classes.
 * Type of search key to Storage - File or Path type.
 */
public abstract class AbstractDirectoryStorage<T, K> extends AbstractStorage<T, K> {
    private final Serializer serializer;
    /**
     * @param storage parameter must be a directory only.
     */
    protected AbstractDirectoryStorage(T storage, Serializer serializer) {
        super(storage);
        this.serializer = serializer;
    }

    /**
     * @param function - the action will be applied to each path element in the directory stream.
     */
    private void forEachInDirectoryStream(Function<Path, ?> function) {
        Path directoryPath = getDirectoryPath();
        try (var directoryStream = Files.newDirectoryStream(directoryPath)) {
            directoryStream.forEach(function::apply);
        } catch (IOException e) {
            throw new StorageException("Storage read error " + directoryPath.toAbsolutePath(), e);
        }
    }

    protected abstract Path getDirectoryPath();

    @Override
    public int size() {
        return new AtomicInteger() {{
            forEachInDirectoryStream(path -> this.getAndIncrement());
        }}.get();
    }

    @Override
    public void clear() {
        forEachInDirectoryStream(path -> {
            try {
                Files.delete(path);
            } catch (IOException e) {
                throw new StorageException("Delete error" + path.toAbsolutePath(), path.toString(), e);
            }
            return null;
        });
    }

    @Override
    protected List<Resume> getAll() {
        return new ArrayList<>() {{
            forEachInDirectoryStream(path -> this.add(getResume(getKey(path))));
        }};
    }

    protected abstract K getKey(Path path);


    @Override
    protected Resume getResume(K searchKey) {
        try {
            return serializer.doRead(getInputStream(searchKey));
        } catch (IOException e) {
            throw new StorageException("File read error ", searchKey.toString(), e);
        }
    }

    protected abstract InputStream getInputStream(K searchKey) throws IOException;

    @Override
    protected void replace(K searchKey, Resume resume) {
        try {
            serializer.doWrite(resume, getOutputStream(searchKey));
        } catch (IOException e) {
            throw new StorageException("Update error ", searchKey.toString(), e);
        }
    }

    protected abstract OutputStream getOutputStream(K searchKey) throws IOException;
}
