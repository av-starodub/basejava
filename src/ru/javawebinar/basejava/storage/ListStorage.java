package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.ExistStorageException;
import ru.javawebinar.basejava.exception.NotExistStorageException;
import ru.javawebinar.basejava.model.Resume;

import java.util.ArrayList;
import java.util.List;

public class ListStorage implements Storage {
    private final List<Resume> storage;

    public ListStorage() {
        storage = new ArrayList<>(10000);
    }

    private int getIndex(String uuid) {
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
    public void clear() {
        storage.clear();
    }

    @Override
    public void update(Resume resume) {
        String uuid = resume.getUuid();
        int index = getIndex(uuid);
        if (index == -1) {
            throw new NotExistStorageException(uuid);
        }
        storage.add(index, resume);
    }

    @Override
    public void save(Resume resume) {
        String uuid = resume.getUuid();
        if (getIndex(uuid) != -1) {
            throw new ExistStorageException(uuid);
        }
        storage.add(resume);
    }

    @Override
    public Resume get(String uuid) {
        int index = getIndex(uuid);
        if (index == -1) {
            throw new NotExistStorageException(uuid);
        }
        return storage.get(index);
    }

    @Override
    public void delete(String uuid) {
        if (!storage.removeIf(resume -> resume.getUuid().equals(uuid))) {
            throw new NotExistStorageException(uuid);
        }
    }

    @Override
    public Resume[] getAll() {
        return storage.toArray(Resume[]::new);
    }

    @Override
    public int size() {
        return storage.size();
    }
}
