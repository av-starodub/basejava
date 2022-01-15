package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

import java.util.Arrays;

/**
 * Sorted array based storage for Resumes
 */
public class SortedArrayStorage extends AbstractArrayStorage {

    public SortedArrayStorage() {
        this.storage = new Resume[STORAGE_LIMIT];
    }

    @Override
    protected int getIndex(String uuid) {
        Resume r = new Resume();
        r.setUuid(uuid);
        return Arrays.binarySearch(storage, 0, size, r);
    }

    @Override
    protected void insert(Resume resume, int insertionPoint) {
        System.arraycopy(storage, insertionPoint,
                storage, insertionPoint + 1,
                size - insertionPoint
        );
        storage[insertionPoint] = resume;
    }

    @Override
    protected void remove(int resumeIndex) {
        System.arraycopy(storage, resumeIndex + 1,
                storage, resumeIndex,
                size - resumeIndex - 1
        );
        size--;
        storage[size] = null;
    }
}
