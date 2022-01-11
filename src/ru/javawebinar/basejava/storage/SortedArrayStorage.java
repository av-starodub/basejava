package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

public class SortedArrayStorage extends AbstractArrayStorage {
    @Override
    protected int getIndex(String uuid) {
        return -1;
    }

    @Override
    protected void insert(Resume resume) {

    }

    @Override
    protected void remove(int index) {

    }
}
