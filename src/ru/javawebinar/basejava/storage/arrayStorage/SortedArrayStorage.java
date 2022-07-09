package ru.javawebinar.basejava.storage.arrayStorage;

import ru.javawebinar.basejava.model.Resume;
import ru.javawebinar.basejava.storage.arrayStorage.AbstractArrayStorage;

import java.util.Arrays;
import java.util.Comparator;

/**
 * Sorted array based storage for Resumes
 */
public class SortedArrayStorage extends AbstractArrayStorage {

    @Override
    protected Integer getSearchKey(String uuid) {
        Resume r = new Resume(uuid, "dummy");
        return Arrays.binarySearch(storage, 0, size, r, Comparator.comparing(Resume::getUuid));
    }

    @Override
    protected void insert(Resume resume, Integer insertionPoint) {
        int index = -(insertionPoint + 1);
        System.arraycopy(storage, index, storage, index + 1, size - index);
        storage[index] = resume;
    }

    @Override
    protected void remove(Integer index) {
        System.arraycopy(storage, index + 1, storage, index, size - index);
    }
}
