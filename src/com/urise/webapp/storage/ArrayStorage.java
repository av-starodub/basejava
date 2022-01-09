package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    private final Resume[] storage;
    private int size;

    public ArrayStorage() {
        this.storage = new Resume[10000];
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

    private void printResumeSearchErrorMessage(boolean isFound) {
        if (isFound) {
            System.out.println("ERROR: This resume already exists in storage");
        } else {
            System.out.println("ERROR: No such resume in storage");
        }
    }

    public void save(Resume resume) {
        if (isNull(resume)) {
            return;
        }
        if (!hasStorageFreeSpace()) {
            System.out.println("ERROR: The storage is full");
            return;
        }
        if (isResumeExist(findIndex(resume.getUuid()))) {
            printResumeSearchErrorMessage(true);
            return;
        }
        storage[size] = resume;
        size++;
    }

    public void delete(String uuid) {
        int indexOfResume = findIndex(uuid);
        if (!isResumeExist(indexOfResume)) {
            printResumeSearchErrorMessage(false);
            return;
        }
        storage[indexOfResume] = storage[size - 1];
        storage[size - 1] = null;
        size--;
    }

    public Resume get(String uuid) {
        int indexOfResume = findIndex(uuid);
        if (!isResumeExist(indexOfResume)) {
            printResumeSearchErrorMessage(false);
            return null;
        }
        return storage[indexOfResume];
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    public Resume[] getAll() {
        return Arrays.copyOfRange(storage, 0, size);
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
        int indexOfResume = findIndex(resume.getUuid());
        if (!isResumeExist(indexOfResume)) {
            printResumeSearchErrorMessage(false);
            return;
        }
        storage[indexOfResume] = resume;
    }
}
