package ru.javawebinar.basejava.storage.arrayStorage;

import ru.javawebinar.basejava.model.Resume;
import ru.javawebinar.basejava.storage.arrayStorage.AbstractArrayStorage;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage extends AbstractArrayStorage {

    protected Integer getSearchKey(String uuid) {
        for (int i = 0; i < size; i++) {
            if (uuid.equals(storage[i].getUuid())) {
                return i;
            }
        }
        return -1;
    }

    protected void insert(Resume resume, Integer index) {
        storage[size] = resume;
    }

    protected void remove(Integer index) {
        storage[index] = storage[size];
    }
}
