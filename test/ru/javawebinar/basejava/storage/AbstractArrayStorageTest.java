package ru.javawebinar.basejava.storage;

import org.junit.Test;
import ru.javawebinar.basejava.exception.StorageException;
import ru.javawebinar.basejava.model.Resume;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public abstract class AbstractArrayStorageTest extends AbstractStorageTest {

    public AbstractArrayStorageTest(Storage storage) {
        super(storage);
    }

    private void compareSize(int expected) {
        assertEquals(expected, storage.size());

    }

    @Test
    public void compareActualSizeAfterClearing() {
        storage.clear();
        compareSize(0);
    }

    @Test
    public void compareActualSizeAfterRemoving() {
        storage.delete("uuid2");
        compareSize(2);
    }

    @Test(expected = StorageException.class)
    public void checkThatExceptionThrowsWhenAddResumeToFullStorageOnly() {
        for (int i = storage.size(); i < AbstractArrayStorage.STORAGE_LIMIT; i++) {
            try {
                storage.save(new Resume(String.valueOf(i)));
            } catch (StorageException se) {
                fail("Overflow happened ahead of time");
            }
        }
        storage.save(new Resume("dummy"));
    }
}
