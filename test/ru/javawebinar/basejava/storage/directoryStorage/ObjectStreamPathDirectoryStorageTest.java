package ru.javawebinar.basejava.storage.directoryStorage;

import ru.javawebinar.basejava.storage.AbstractStorageTest;
import ru.javawebinar.basejava.storage.serializers.ObjectSerializer;

public class ObjectStreamPathDirectoryStorageTest extends AbstractStorageTest {
    public ObjectStreamPathDirectoryStorageTest() {
        super(new PathDirectoryStorage(PATH_STORAGE_DIR, new ObjectSerializer()));
    }
}
