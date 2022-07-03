package ru.javawebinar.basejava.storage.directoryStorage;

import ru.javawebinar.basejava.model.Resume;
import ru.javawebinar.basejava.storage.AbstractStorage;
import ru.javawebinar.basejava.storage.serializers.Serializer;

import java.nio.file.Path;
import java.util.List;

public class PathStorage extends AbstractStorage<Path, Path> {
    private final Serializer serializer;

    protected PathStorage(String directory, Serializer serializer) {
        super(Path.of(directory));
        this.serializer = serializer;
    }

    @Override
    protected Path getSearchKey(String uuid) {
        return null;
    }

    @Override
    protected Resume getResume(Path searchKey) {
        return null;
    }

    @Override
    protected List<Resume> getAll() {
        return null;
    }

    @Override
    protected void insert(Resume resume, Path insertKey) {

    }

    @Override
    protected void replace(Path searchKey, Resume resume) {

    }

    @Override
    protected void remove(Path searchKey) {

    }

    @Override
    protected boolean isResumeExist(Path searchKey) {
        return false;
    }

    @Override
    public void clear() {

    }

    @Override
    public int size() {
        return 0;
    }
}
