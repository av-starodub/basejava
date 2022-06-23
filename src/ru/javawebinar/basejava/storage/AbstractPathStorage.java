package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.StorageException;
import ru.javawebinar.basejava.model.Resume;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;

public abstract class AbstractPathStorage extends AbstractStorage<Path, Path> {
    protected AbstractPathStorage(String directory) {
        super(checkDirectory(directory));
    }

    private static Path checkDirectory(String directory) {
        Path dir = Path.of(directory);
        if (!Files.isDirectory(dir) || !Files.isWritable(dir)) {
            throw new IllegalArgumentException(dir + " is not directory or is not writable");
        }
        return dir;
    }
    private DirectoryStream<Path> getDirectoryStream() throws IOException {
        return Files.newDirectoryStream(storage);
    }

    /**
     * @param function - the action will be applied to each path element in the directory stream.
     */
    private void forEachInDirectoryStream(Function<Path, ?> function) {
        try (var directoryStream = getDirectoryStream()) {
            directoryStream.forEach(function::apply);
        } catch (IOException e) {
            throw new StorageException("Storage read error " + storage.toAbsolutePath(), e);
        }
    }

    @Override
    protected Path getSearchKey(String uuid) {
        return storage.resolve(uuid);
    }

    @Override
    protected Resume getResume(Path path) {
        try {
            return doRead(path);
        } catch (IOException e) {
            throw new StorageException("Path read error", path.toString() + path.toAbsolutePath(), e);
        }
    }

    protected abstract Resume doRead(Path path) throws IOException;

    @Override
    protected List<Resume> getAll() {
        return new ArrayList<>() {{
            forEachInDirectoryStream(path -> this.add(getResume(path)));
        }};
    }

    @Override
    protected void insert(Resume resume, Path path) {
        try {
            Files.createFile(path);
        } catch (IOException e) {
            throw new StorageException("Create file error" + path.toAbsolutePath(), path.getFileName().toString(), e);
        }
        try {
            doWrite(resume, path);
        } catch (IOException e) {
            throw new StorageException("Save error" + path.toAbsolutePath(), path.getFileName().toString(), e);
        }

    }

    protected abstract void doWrite(Resume resume, Path path) throws IOException;

    @Override
    protected void replace(Path path, Resume resume) {
        try {
            doWrite(resume, path);
        } catch (IOException e) {
            throw new StorageException("Update error" + path.toAbsolutePath(), path.getFileName().toString(), e);
        }
    }

    @Override
    protected void remove(Path path) {
        try {
            Files.delete(path);
        } catch (IOException e) {
            throw new StorageException("Delete error" + path.toAbsolutePath(), path.getFileName().toString(), e);
        }
    }

    @Override
    protected boolean isResumeExist(Path path) {
        return Files.exists(path);
    }

    @Override
    public void clear() {
        forEachInDirectoryStream(path -> {
            remove(path);
            return null;
        });
    }

    @Override
    public int size() {
        return new AtomicInteger() {{
            forEachInDirectoryStream(path -> this.getAndIncrement());
        }}.get();
    }
}
