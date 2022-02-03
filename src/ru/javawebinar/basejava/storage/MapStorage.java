package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

import java.util.HashMap;

public class MapStorage extends AbstractStorage<HashMap<Integer, Resume>> {
    protected MapStorage() {
        super(new HashMap<>());
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
        return storage.values().toArray(Resume[]::new);
    }

    @Override
    protected Resume getResume(int key) {
        return storage.get(key);
    }

    @Override
    protected int getIndex(String uuid) {
        Resume r = storage.get(uuid.hashCode());
        if (r != null) {
            return r.hashCode();
        }
        return -1;
    }

    @Override
    protected void insert(Resume resume, int index) {
        storage.put(resume.hashCode(), resume);
    }

    @Override
    protected void replace(int key, Resume resume) {
        storage.replace(key, resume);
    }

    @Override
    protected void remove(int key) {
        storage.remove(key);
    }
}
