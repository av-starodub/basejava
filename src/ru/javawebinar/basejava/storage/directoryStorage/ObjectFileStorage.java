package ru.javawebinar.basejava.storage.directoryStorage;

import ru.javawebinar.basejava.storage.serializers.ObjectSerializer;

import java.io.File;

public class ObjectFileStorage extends FileStorage {
    public ObjectFileStorage(File directory) {
        super(directory, new ObjectSerializer());
    }
}
