package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

import java.util.ArrayList;
import java.util.List;

public class ListStorage extends AbstractStorage<List<Resume>, Integer> {

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
    protected Resume getResume(Integer index) {
        return storage.get(index);
    }

    @Override
    protected Integer getSearchKey(String uuid) {
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
    protected void insert(Resume resume, Integer index) {
        storage.add(resume);
    }

    @Override
    protected void replace(Integer index, Resume resume) {
        storage.add(index, resume);
    }

    @Override
    protected void remove(Integer index) {
        storage.remove((int) index);
    }

    @Override
    protected boolean isResumeExist(Integer index) {
        return index >= 0;
    }

    @Override
    protected List<Resume> getAll() {
        return new ArrayList<>(storage.subList(0, size()));
    }
}
