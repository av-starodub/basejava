package ru.javawebinar.basejava.storage.directoryStorage;

import ru.javawebinar.basejava.exception.StorageException;
import ru.javawebinar.basejava.model.Resume;
import ru.javawebinar.basejava.storage.directoryStorage.AbstractDirectoryStorage;
import ru.javawebinar.basejava.storage.serializers.Serializer;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;

/**
 * Path type Storage.
 * Search key type - object Path.
 */
public class PathStorage extends AbstractDirectoryStorage<Path, Path> {

    protected PathStorage(String directory, Serializer serializer) {
        super(checkDirectory(Objects.requireNonNull(directory, " directory must not be null")), serializer);
    }

    private static Path checkDirectory(String directory) {
        Path dir = Path.of(directory);
        if (!Files.isDirectory(dir) || !Files.isWritable(dir)) {
            throw new IllegalArgumentException(dir + " is not directory or is not writable");
        }
        return dir;
    }

    @Override
    protected Path getDirectoryPath() {
        return storage;
    }

    @Override
    protected Path getKey(Path path) {
        return path;
    }

    @Override
    protected InputStream getInputStream(Path path) throws IOException {
        return Files.newInputStream(path);
    }

    @Override
    protected OutputStream getOutputStream(Path path) throws IOException {
        return Files.newOutputStream(path);
    }

    @Override
    protected Path getSearchKey(String uuid) {
        return storage.resolve(uuid);
    }

    @Override
    protected void insert(Resume resume, Path path) {
        try {
            Files.createFile(path);
            replace(path, resume);
        } catch (IOException | StorageException e) {
            throw new StorageException("Create file error" + path.toAbsolutePath(), path.getFileName().toString(), e);
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
}
