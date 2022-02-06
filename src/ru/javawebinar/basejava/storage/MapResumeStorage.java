package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

import java.util.HashMap;

/**
 * HashMap type Storage.
 * Search key type - object Resume.
 */
public class MapResumeStorage extends AbstractMapStorage<Resume> {
    public MapResumeStorage() {
        super(new HashMap<>());
    }

    @Override
    protected Resume getKey(String uuid) {
        return new Resume(uuid, "dummy");
    }
}
