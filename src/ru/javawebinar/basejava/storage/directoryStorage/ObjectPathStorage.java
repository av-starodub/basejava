package ru.javawebinar.basejava.storage.directoryStorage;

import ru.javawebinar.basejava.storage.serializers.ObjectSerializer;

public class ObjectPathStorage extends PathStorage{
    protected ObjectPathStorage(String directory) {
        super(directory, new ObjectSerializer());
    }
}
