package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public abstract class AbstractMapStorage<K> extends AbstractStorage<HashMap<K, Resume>, K> {
    protected AbstractMapStorage(HashMap<K, Resume> storage) {
        super(storage);
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
    protected boolean isResumeExist(K searchKey) {
        return searchKey != null;
    }

    @Override
    protected K getSearchKey(String uuid) {
        K searchKey = getKey(uuid);
        Resume r = storage.get(searchKey);
        return isNotNull(r) ? searchKey : null;
    }

    protected abstract K getKey(String uuid);

    @Override
    protected void insert(Resume resume, K key) {
        storage.put(createKey(resume), resume);
    }

    protected abstract K createKey(Resume resume);

    @Override
    protected void replace(K searchKey, Resume resume) {
        storage.replace(searchKey, resume);
    }

    @Override
    protected void remove(K searchKey) {
        storage.remove(searchKey);
    }

    @Override
    protected Resume getResume(K searchKey) {
        return storage.get(searchKey);
    }

    @Override
    protected List<Resume> getAll() {
        return new ArrayList<>(storage.values());
    }
}
