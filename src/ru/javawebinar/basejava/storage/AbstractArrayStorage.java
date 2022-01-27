package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.NotExistStorageException;
import ru.javawebinar.basejava.exception.StorageException;
import ru.javawebinar.basejava.model.Resume;

import java.util.Arrays;

public abstract class AbstractArrayStorage extends AbstractStorage<Resume[]> {
    protected static final int STORAGE_LIMIT = 10000;
    protected int size;

    protected AbstractArrayStorage() {
        super(new Resume[STORAGE_LIMIT]);
    }

    private boolean hasStorageFreeSpace() {
        return size < storage.length;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    @Override
    public Resume[] getAll() {
        return Arrays.copyOf(storage, size);
    }

    @Override
    public void save(Resume resume) {
        if (!hasStorageFreeSpace()) {
            throw new StorageException("Storage overflow", resume.getUuid());
        }
        super.save(resume);
        size++;
    }

    @Override
    public void delete(String uuid) {
        int resumeIndex = getIndex(uuid);
        if (!isResumeExist(resumeIndex)) {
            throw new NotExistStorageException(uuid);
        }
        size--;
        remove(resumeIndex);
        storage[size] = null;
    }

    @Override
    protected void replace(int index, Resume resume) {
        storage[index] = resume;
    }

    @Override
    protected Resume getResume(int index) {
        return storage[index];
    }

    protected abstract void remove(int resumeIndex);
}
