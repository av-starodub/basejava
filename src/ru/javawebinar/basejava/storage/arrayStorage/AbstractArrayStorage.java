package ru.javawebinar.basejava.storage.arrayStorage;

import ru.javawebinar.basejava.exception.StorageException;
import ru.javawebinar.basejava.model.Resume;
import ru.javawebinar.basejava.storage.AbstractStorage;

import java.util.*;

/**
 * Base class for all Array type Storage.
 * Search key type - Integer.
 */
public abstract class AbstractArrayStorage extends AbstractStorage<Resume[], Integer> {
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
        size--;
        super.delete(uuid);
        storage[size] = null;
    }

    @Override
    protected boolean isResumeExist(Integer index) {
        return index >= 0;
    }

    @Override
    protected void replace(Integer index, Resume resume) {
        storage[index] = resume;
    }

    @Override
    protected Resume getResume(Integer index) {
        return storage[index];
    }

    /**
     * @return ArrayList, contains only Resumes in storage (without null)
     */
    @Override
    public List<Resume> getAll() {
        return new ArrayList<>(Arrays.asList(storage).subList(0, size));
    }
}
