package ru.javawebinar.basejava.storage;

import org.junit.Before;
import org.junit.Test;
import ru.javawebinar.basejava.exception.ExistStorageException;
import ru.javawebinar.basejava.exception.NotExistStorageException;
import ru.javawebinar.basejava.model.Resume;

import static org.junit.Assert.*;

/**
 * Base class to test all Storage types.
 */
public abstract class AbstractStorageTest {
    protected final Storage storage;
    protected final Resume r1;
    protected final Resume r2;
    protected final Resume r3;

    public AbstractStorageTest(Storage storage) {
        this.storage = storage;
        r1 = new Resume("uuid1", "B");
        r2 = new Resume("uuid2", "C");
        r3 = new Resume("uuid3", "D");
    }

    @Before
    public void setUp() {
        storage.clear();
        storage.save(r1);
        storage.save(r2);
        storage.save(r3);
    }

    private void compareActualSize(int expectedSize) {
        assertEquals(expectedSize, storage.size());
    }

    private void compareActualArray(Resume... expected) {
        Resume[] actual = storage.getAllSorted().toArray(new Resume[storage.size()]);
        assertArrayEquals(expected, actual);
    }

    @Test
    public void checkActualSize() {
        compareActualSize(3);
    }

    @Test
    public void checkThatReturnedListContainsAllResumesFromTheStorage() {
        compareActualArray(r1, r2, r3);
    }

    @Test
    public void checkThatReturnedListIsSortedByName() {
        Resume r = new Resume("uuid4", "A");
        storage.save(r);
        compareActualArray(r, r1, r2, r3);
    }

    @Test
    public void checkThatResumesAreSortedByUuidIfTheirFullNamesMatch() {
        Resume r = new Resume("uuid0", "C");
        storage.save(r);
        compareActualArray(r1, r, r2, r3);
    }

    @Test
    public void checkStorageIsEmptyAfterClear() {
        storage.clear();
        compareActualArray();
        compareActualSize(0);
    }

    @Test
    public void compareTheReceivedResumeWithExistingInStorage() {
        assertEquals(r1, storage.get("uuid1"));
    }

    @Test(expected = NotExistStorageException.class)
    public void checkFailureGetNonExistentResume() {
        storage.get("dummy");
    }

    @Test
    public void checkThatNonExistentResumeIsAdded() {
        Resume r = new Resume("uuid4", "D");
        storage.save(r);
        assertEquals(r, storage.get("uuid4"));
        compareActualSize(4);
    }

    @Test(expected = ExistStorageException.class)
    public void checkFailureAddExistingResume() {
        storage.save(r1);
    }

    @Test
    public void checkThatExistingResumeIsRemoved() {
        storage.delete("uuid2");
        compareActualArray(r1, r3);
        compareActualSize(2);
    }

    @Test(expected = NotExistStorageException.class)
    public void checkFailureRemoveNonExistentResume() {
        storage.delete("dummy");
    }

    @Test
    public void checkThatExistingResumeIsUpdated() {
        Resume r = new Resume("uuid1", "dummy");
        storage.update(r);
        assertSame(r, storage.get("uuid1"));
    }

    @Test(expected = NotExistStorageException.class)
    public void checkFailureUpdateNonExistentResume() {
        storage.update(new Resume("dummy"));
    }
}
