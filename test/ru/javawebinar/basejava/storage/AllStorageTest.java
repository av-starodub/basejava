package ru.javawebinar.basejava.storage;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import ru.javawebinar.basejava.storage.arrayStorage.ArrayStorageTest;
import ru.javawebinar.basejava.storage.arrayStorage.SortedArrayStorageTest;
import ru.javawebinar.basejava.storage.directoryStorage.*;
import ru.javawebinar.basejava.storage.listStorage.ListStorageTest;
import ru.javawebinar.basejava.storage.mapStorage.MapIntegerStorageTest;
import ru.javawebinar.basejava.storage.mapStorage.MapResumeStorageTest;
import ru.javawebinar.basejava.storage.mapStorage.MapUuidStorageTest;
import ru.javawebinar.basejava.storage.sqlStorage.SqlStorageTest;

/**
 * The class to run all Storage tests.
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({
        ArrayStorageTest.class,
        SortedArrayStorageTest.class,
        ListStorageTest.class,
        MapUuidStorageTest.class,
        MapResumeStorageTest.class,
        MapIntegerStorageTest.class,
        ObjectStreamFileDirectoryStorageTest.class,
        ObjectStreamPathDirectoryStorageTest.class,
        ObjectStreamFileStorageTest.class,
        ObjectStreamPathStorageTest.class,
        DataStreamFileStorageTest.class,
        DataStreamPathStorageTest.class,
        SqlStorageTest.class
})
public class AllStorageTest {
}
