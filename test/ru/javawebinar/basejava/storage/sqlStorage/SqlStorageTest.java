package ru.javawebinar.basejava.storage.sqlStorage;

import ru.javawebinar.basejava.Config;
import ru.javawebinar.basejava.storage.AbstractStorageTest;

public class SqlStorageTest extends AbstractStorageTest {
    public SqlStorageTest() {
        super(Config.get().getStorage());
    }
}
