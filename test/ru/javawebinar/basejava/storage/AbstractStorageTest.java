package ru.javawebinar.basejava.storage;

import org.junit.Before;
import org.junit.Test;
import ru.javawebinar.basejava.Config;
import ru.javawebinar.basejava.exception.ExistStorageException;
import ru.javawebinar.basejava.exception.NotExistStorageException;
import ru.javawebinar.basejava.model.Resume;

import java.io.File;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static ru.javawebinar.basejava.modelDataTest.creators.ResumeCreator.createResume;

/**
 * Base class to test all Storage types.
 */
public abstract class AbstractStorageTest {
    protected static final File FILE_STORAGE_DIR = Config.get().getStorageDir();
    protected static final String PATH_STORAGE_DIR = FILE_STORAGE_DIR.getAbsolutePath();

    protected final Storage storage;
    protected final Resume r1;
    protected final Resume r2;
    protected final Resume r3;

    public AbstractStorageTest(Storage storage) {
        this.storage = storage;
        r1 = createResume("uuid1", "B");
        r2 = createResume("uuid2", "C");
        r3 = createResume("uuid3", "D");
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
        Resume r = createResume("uuid4", "A");
        storage.save(r);
        compareActualArray(r, r1, r2, r3);
    }

    @Test
    public void checkThatResumesAreSortedByUuidIfTheirFullNamesMatch() {
        Resume r = createResume("uuid0", "C");
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
        Resume r = createResume("uuid4", "D");
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
        Resume r = createResume("uuid1", "dummy");
        storage.update(r);
        assertEquals(r, storage.get("uuid1"));
    }

    @Test(expected = NotExistStorageException.class)
    public void checkFailureUpdateNonExistentResume() {
        storage.update(createResume("uuid4", "dummy"));
    }
}
