package ru.javawebinar.basejava.storage;

import java.util.HashMap;

/**
 * HashMap type Storage.
 * Search key type - String uuid.
 */
public class MapUuidStorage extends AbstractMapStorage<String> {
    public MapUuidStorage() {
        super(new HashMap<>());
    }

    @Override
    protected String getKey(String uuid) {
        return uuid;
    }
}
