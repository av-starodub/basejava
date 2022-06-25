package ru.javawebinar.basejava.storage.directoryStorage;

import ru.javawebinar.basejava.storage.AbstractStorageTest;

public class ObjectPathStorageTest extends AbstractStorageTest {
    public ObjectPathStorageTest() {
        super(new ObjectPathStorage(PATH__STORAGE_DIR));
    }
}
