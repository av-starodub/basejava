package ru.javawebinar.basejava.storage.directoryStorage;

import ru.javawebinar.basejava.model.Resume;
import ru.javawebinar.basejava.storage.AbstractStorage;
import ru.javawebinar.basejava.storage.serializers.Serializer;

import java.io.File;
import java.util.List;

public class FileStorage extends AbstractStorage<File, File> {
    private final Serializer serializer;

    protected FileStorage(File storage, Serializer serializer) {
        super(storage);
        this.serializer = serializer;
    }

    @Override
    protected File getSearchKey(String uuid) {
        return null;
    }

    @Override
    protected Resume getResume(File searchKey) {
        return null;
    }

    @Override
    protected List<Resume> getAll() {
        return null;
    }

    @Override
    protected void insert(Resume resume, File insertKey) {

    }

    @Override
    protected void replace(File searchKey, Resume resume) {

    }

    @Override
    protected void remove(File searchKey) {

    }

    @Override
    protected boolean isResumeExist(File searchKey) {
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
