package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

import java.util.ArrayList;
import java.util.List;

public class ListStorage extends AbstractStorage<List<Resume>> {

    public ListStorage() {
        super(new ArrayList<>());
    }

    @Override
    public int size() {
        return storage.size();
    }

    @Override
    public void clear() {
        storage.clear();
    }

    @Override
    public Resume[] getAll() {
        return storage.toArray(Resume[]::new);
    }

    @Override
    protected Resume getResume(int index) {
        return storage.get(index);
    }

    @Override
    protected int getIndex(String uuid) {
        int index = 0;
        for (Resume resume : storage) {
            if (resume.getUuid().equals(uuid)) {
                return index;
            }
            index++;
        }
        return -1;
    }

    @Override
    protected void insert(Resume resume, int index) {
        storage.add(resume);
    }

    @Override
    protected void replace(int index, Resume resume) {
        storage.add(index, resume);
    }

    @Override
    protected void remove(int index) {
        storage.remove(index);
    }
}
