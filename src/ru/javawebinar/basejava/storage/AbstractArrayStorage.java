package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.ExistStorageException;
import ru.javawebinar.basejava.exception.NotExistStorageException;
import ru.javawebinar.basejava.exception.StorageException;
import ru.javawebinar.basejava.model.Resume;

import java.util.Arrays;

public abstract class AbstractArrayStorage implements Storage {
    protected static final int STORAGE_LIMIT = 10000;
    protected Resume[] storage = new Resume[STORAGE_LIMIT];
    protected int size;

    private boolean isNull(Resume resume) {
        return resume == null;
    }

    private boolean isResumeExist(int index) {
        return index >= 0;
    }

    private boolean hasStorageFreeSpace() {
        return size < storage.length;
    }

    public int size() {
        return size;
    }

    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    public Resume[] getAll() {
        return Arrays.copyOf(storage, size);
    }

    public Resume get(String uuid) {
        int resumeIndex = getIndex(uuid);
        if (!isResumeExist(resumeIndex)) {
            throw new NotExistStorageException(uuid);
        }
        return storage[resumeIndex];
    }

    public void save(Resume resume) {
        if (isNull(resume)) {
            return;
        }
        String uuid = resume.getUuid();
        if (!hasStorageFreeSpace()) {
            throw new StorageException("Storage overflow", uuid);
        }
        int index = getIndex(uuid);
        if (isResumeExist(index)) {
            throw new ExistStorageException(uuid);
        }
        insert(resume, index);
        size++;
    }

    public void delete(String uuid) {
        int resumeIndex = getIndex(uuid);
        if (!isResumeExist(resumeIndex)) {
            throw new NotExistStorageException(uuid);
        }
        size--;
        remove(resumeIndex);
        storage[size] = null;
    }

    public void update(Resume resume) {
        if (isNull(resume)) {
            return;
        }
        String uuid = resume.getUuid();
        int resumeIndex = getIndex(uuid);
        if (!isResumeExist(resumeIndex)) {
            throw new NotExistStorageException(uuid);
        }
        storage[resumeIndex] = resume;
    }

    protected abstract int getIndex(String uuid);

    protected abstract void insert(Resume resume, int insertionPoint);

    protected abstract void remove(int resumeIndex);
}
