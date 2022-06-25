package ru.javawebinar.basejava.storage.directoryStorage;

import ru.javawebinar.basejava.storage.AbstractStorageTest;
import ru.javawebinar.basejava.storage.serializers.ObjectSerializer;

public class ObjectStreamFileStorageTest extends AbstractStorageTest {
    public ObjectStreamFileStorageTest() {
        super(new FileStorage(FILE_STORAGE_DIR, new ObjectSerializer()));
    }
}
