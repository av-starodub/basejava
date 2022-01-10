package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage extends AbstractArrayStorage {

    public ArrayStorage() {
        this.storage = new Resume[STORAGE_LIMIT];
    }

    protected int getIndex(String uuid) {
        for (int i = 0; i < size; i++) {
            if (uuid.equals(storage[i].getUuid())) {
                return i;
            }
        }
        return -1;
    }

    protected void insert(Resume resume) {
        storage[size] = resume;
    }

    protected void remove(int index) {
        size--;
        storage[index] = storage[size];
        storage[size] = null;
    }
}
