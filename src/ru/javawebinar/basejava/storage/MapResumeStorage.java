package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

import java.util.HashMap;
import java.util.Map;

public class MapResumeStorage extends AbstractMapStorage<Resume> {
    public MapResumeStorage() {
        super(new HashMap<>());
    }

    @Override
    protected Resume getKey(String uuid) {
        for (Map.Entry<Resume, Resume> map : storage.entrySet()) {
            Resume r = map.getValue();
            if (r.getUuid().equals(uuid)) {
                return map.getKey();
            }
        }
        return null;
    }

    @Override
    protected Resume createKey(Resume resume) {
        return resume;
    }
}
