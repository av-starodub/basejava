package ru.javawebinar.basejava.storage.mapStorage;

import ru.javawebinar.basejava.storage.mapStorage.AbstractMapStorage;

import java.util.HashMap;

/**
 * HashMap type storage.
 * Search key type - Integer.
 */
public class MapIntegerStorage extends AbstractMapStorage<Integer> {
    public MapIntegerStorage() {
        super(new HashMap<>());
    }

    @Override
    protected Integer getKey(String uuid) {
        return uuid.hashCode();
    }
}
