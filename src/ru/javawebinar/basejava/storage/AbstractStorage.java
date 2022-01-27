package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.ExistStorageException;
import ru.javawebinar.basejava.exception.NotExistStorageException;
import ru.javawebinar.basejava.model.Resume;

public abstract class AbstractStorage<T> implements Storage {
    protected final T storage;

    protected AbstractStorage(T storage) {
        this.storage = storage;
    }

    private boolean isNull(Resume resume) {
        return resume == null;
    }

    protected boolean isResumeExist(int index) {
        return index >= 0;
    }

    @Override
    public void save(Resume resume) {
        if (isNull(resume)) {
            return;
        }
        String uuid = resume.getUuid();
        int index = getIndex(uuid);
        if (isResumeExist(index)) {
            throw new ExistStorageException(uuid);
        }
        insert(resume, index);
    }

    @Override
    public Resume get(String uuid) {
        int resumeIndex = getIndex(uuid);
        if (!isResumeExist(resumeIndex)) {
            throw new NotExistStorageException(uuid);
        }
        return getResume(resumeIndex);
    }

    @Override
    public void update(Resume resume) {
        if (isNull(resume)) {
            return;
        }
        String uuid = resume.getUuid();
        int resumeIndex = getIndex(uuid);
        if (!isResumeExist(resumeIndex)) {
            throw new NotExistStorageException(uuid);
        }
        replace(resumeIndex, resume);
    }

    protected abstract int getIndex(String uuid);

    protected abstract Resume getResume(int index);

    protected abstract void insert(Resume resume, int index);

    protected abstract void replace(int index, Resume resume);
}
