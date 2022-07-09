package ru.javawebinar.basejava.storage.directoryStorage;

import ru.javawebinar.basejava.storage.AbstractStorageTest;
import ru.javawebinar.basejava.storage.serializers.ObjectSerializer;

public class ObjectStreamPathStorageTest extends AbstractStorageTest {
    public ObjectStreamPathStorageTest() {
        super(new PathStorage(PATH_STORAGE_DIR, new ObjectSerializer()));
    }
}
