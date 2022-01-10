package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage implements Storage {
    private static final int STORAGE_LIMIT = 10000;
    private final Resume[] storage;
    private int size;

    public ArrayStorage() {
        this.storage = new Resume[STORAGE_LIMIT];
    }

    private int findIndex(String uuid) {
        for (int i = 0; i < size; i++) {
            if (uuid.equals(storage[i].getUuid())) {
                return i;
            }
        }
        return -1;
    }

    private boolean isNull(Resume resume) {
        return resume.getUuid() == null;
    }

    private boolean isResumeExist(int index) {
        return index != -1;
    }

    private boolean hasStorageFreeSpace() {
        return size < storage.length;
    }

    private void printStorageOverflowErrorMessage(String uuid) {
        System.out.printf("ERROR: %s not added. The storage is full\n", uuid);
    }

    private void printResumeSearchErrorMessage(boolean isFound, String uuid) {
        if (isFound) {
            System.out.printf("ERROR: %s already exists in storage\n", uuid);
        } else {
            System.out.printf("ERROR: %s no such in storage\n", uuid);
        }
    }

    public void save(Resume resume) {
        if (isNull(resume)) {
            return;
        }
        String uuid = resume.getUuid();
        if (!hasStorageFreeSpace()) {
            printStorageOverflowErrorMessage(uuid);
            return;
        }
        if (isResumeExist(findIndex(uuid))) {
            printResumeSearchErrorMessage(true, uuid);
            return;
        }
        storage[size] = resume;
        size++;
    }

    public void delete(String uuid) {
        int indexOfResume = findIndex(uuid);
        if (!isResumeExist(indexOfResume)) {
            printResumeSearchErrorMessage(false, uuid);
            return;
        }
        size--;
        storage[indexOfResume] = storage[size];
        storage[size] = null;
    }

    public Resume get(String uuid) {
        int indexOfResume = findIndex(uuid);
        if (!isResumeExist(indexOfResume)) {
            printResumeSearchErrorMessage(false, uuid);
            return null;
        }
        return storage[indexOfResume];
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    public Resume[] getAll() {
        return Arrays.copyOf(storage, size);
    }

    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    public int size() {
        return size;
    }

    public void update(Resume resume) {
        if (isNull(resume)) {
            return;
        }
        String uuid = resume.getUuid();
        int indexOfResume = findIndex(uuid);
        if (!isResumeExist(indexOfResume)) {
            printResumeSearchErrorMessage(false, uuid);
            return;
        }
        storage[indexOfResume] = resume;
    }
}
