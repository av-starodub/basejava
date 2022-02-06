package ru.javawebinar.basejava.storage;

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
