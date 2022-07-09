package ru.javawebinar.basejava.storage.directoryStorage;

import ru.javawebinar.basejava.storage.AbstractStorageTest;
import ru.javawebinar.basejava.storage.serializers.ObjectSerializer;

public class ObjectStreamFileDirectoryStorageTest extends AbstractStorageTest {
    public ObjectStreamFileDirectoryStorageTest() {
        super(new FileDirectoryStorage(FILE_STORAGE_DIR, new ObjectSerializer()));
    }
}
