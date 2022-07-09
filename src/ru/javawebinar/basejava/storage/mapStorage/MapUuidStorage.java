package ru.javawebinar.basejava.storage.mapStorage;

import ru.javawebinar.basejava.storage.mapStorage.AbstractMapStorage;

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
