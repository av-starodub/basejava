package ru.javawebinar.basejava.storage;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import ru.javawebinar.basejava.storage.arrayStorage.ArrayStorageTest;
import ru.javawebinar.basejava.storage.arrayStorage.SortedArrayStorageTest;
import ru.javawebinar.basejava.storage.directoryStorage.ObjectStreamFileDirectoryStorageTest;
import ru.javawebinar.basejava.storage.directoryStorage.ObjectStreamFileStorageTest;
import ru.javawebinar.basejava.storage.directoryStorage.ObjectStreamPathDirectoryStorageTest;
import ru.javawebinar.basejava.storage.directoryStorage.ObjectStreamPathStorageTest;
import ru.javawebinar.basejava.storage.listStorage.ListStorageTest;
import ru.javawebinar.basejava.storage.mapStorage.MapIntegerStorageTest;
import ru.javawebinar.basejava.storage.mapStorage.MapResumeStorageTest;
import ru.javawebinar.basejava.storage.mapStorage.MapUuidStorageTest;

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
        ObjectStreamPathStorageTest.class
})
public class AllStorageTest {
}
