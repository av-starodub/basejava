package ru.javawebinar.basejava.storage.directoryStorage;

import ru.javawebinar.basejava.exception.StorageException;
import ru.javawebinar.basejava.model.Resume;
import ru.javawebinar.basejava.storage.AbstractStorage;
import ru.javawebinar.basejava.storage.serializers.Serializer;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class PathStorage extends AbstractStorage<Path, Path> {
    private final Serializer serializer;

    protected PathStorage(String directory, Serializer serializer) {
        super(checkDirectory(directory));
        this.serializer = serializer;
    }

    private static Path checkDirectory(String directory) {
        var dir = Path.of(directory);
        if (!Files.isDirectory(dir) || !Files.isWritable(dir)) {
            throw new IllegalArgumentException(dir + " is not directory or is not writable");
        }
        return dir;
    }

    private Stream<Path> getPathStream() {
        try {
            return Files.list(storage);
        } catch (IOException e) {
            throw new StorageException("Storage read error");
        }
    }

    @Override
    public void clear() {
        try (var stream = getPathStream()) {
            stream.forEach(this::remove);
        }
    }

    @Override
    public int size() {
        try (var stream = getPathStream()) {
            return (int) stream.count();
        }
    }

    @Override
    protected List<Resume> getAll() {
        return new ArrayList<>() {{
            try (var stream = getPathStream()) {
                stream.forEach(path -> this.add(getResume(path)));
            }
        }};
    }

    @Override
    protected Path getSearchKey(String uuid) {
        return storage.resolve(uuid);
    }

    @Override
    protected Resume getResume(Path path) {
        try {
            return serializer.doRead(Files.newInputStream(path));
        } catch (IOException e) {
            throw new StorageException("File read error ", path.getFileName().toString(), e);
        }
    }

    @Override
    protected void insert(Resume resume, Path path) {
        try {
            Files.createFile(path);
        } catch (IOException e) {
            throw new StorageException("Create file error", path.getFileName().toString(), e);
        }
        replace(path, resume);
    }

    @Override
    protected void replace(Path path, Resume resume) {
        try {
            serializer.doWrite(resume, Files.newOutputStream(path));
        } catch (IOException e) {
            throw new StorageException("Write file error", path.getFileName().toString(), e);
        }

    }

    @Override
    protected void remove(Path path) {
        try {
            Files.delete(path);
        } catch (IOException e) {
            throw new StorageException("Delete error" + path, path.getFileName().toString());
        }
    }

    @Override
    protected boolean isResumeExist(Path path) {
        return Files.exists(path);
    }
}
