package ru.javawebinar.basejava.storage;

import org.junit.Before;
import org.junit.Test;
import ru.javawebinar.basejava.exception.ExistStorageException;
import ru.javawebinar.basejava.exception.NotExistStorageException;
import ru.javawebinar.basejava.exception.StorageException;
import ru.javawebinar.basejava.model.Resume;

import static org.junit.Assert.*;

public abstract class AbstractArrayStorageTest {
    private final Storage storage;
    private final Resume r1;
    private final Resume r2;
    private final Resume r3;

    public AbstractArrayStorageTest(Storage storage) {
        this.storage = storage;
        r1 = new Resume("uuid1");
        r2 = new Resume("uuid2");
        r3 = new Resume("uuid3");
    }

    @Before
    public void setUp() {
        storage.clear();
        storage.save(r1);
        storage.save(r2);
        storage.save(r3);
    }

    @Test
    public void sizeShouldReturnStorageFieldValue() {
        assertEquals(3, storage.size());
    }

    @Test
    public void clearShouldAssignNullToCellsWithResume() {
        storage.clear();
        assertArrayEquals(new Resume[0], storage.getAll());
        assertEquals(0, storage.size());
    }

    @Test
    public void getAllShouldReturnAllResumesFromStorage() {
        Resume[] expected = {r1, r2, r3};
        assertArrayEquals(expected, storage.getAll());
    }

    @Test
    public void getShouldReturnExistingResume() {
        assertEquals(r1, storage.get("uuid1"));
    }

    @Test(expected = NotExistStorageException.class)
    public void getNotExistingResumeShouldThrowException() {
        storage.get("dummy");
    }

    @Test
    public void saveShouldAddResumeNotExistingInStorage() {
        Resume r = new Resume("uuid4");
        storage.save(r);
        assertEquals(r, storage.get("uuid4"));
        assertEquals(4, storage.size());
    }

    @Test(expected = ExistStorageException.class)
    public void saveExistingResumeShouldThrowException() {
        storage.save(r1);
    }

    @Test(expected = StorageException.class)
    public void saveToFullStorageShouldThrowException() {
        for (int i = 0; i < 10000; i++) {
            storage.save(new Resume(String.valueOf(i)));
        }
        fail("Overflow happened ahead of time");
        storage.save(new Resume("dummy"));
    }

    @Test
    public void deleteShouldRemoveExistingResume() {
        storage.delete("uuid2");
        Resume[] expected = {r1, r3};
        assertArrayEquals(expected, storage.getAll());
        assertEquals(2, storage.size());
    }

    @Test(expected = NotExistStorageException.class)
    public void deleteNotExistingResumeShouldThrowException() {
        storage.delete("dummy");
    }

    @Test
    public void updateShouldAddExistingResume() {
        Resume r = new Resume("uuid1");
        storage.update(r);
        assertEquals(r, storage.get("uuid1"));
    }

    @Test(expected = NotExistStorageException.class)
    public void updateNotExistingResumeShouldThrowException() {
        storage.update(new Resume("dummy"));
    }
}