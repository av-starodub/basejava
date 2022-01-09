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

    public void save(Resume resume) {
        if (resume.getUuid() == null) {
            return;
        }
        if (size == storage.length) {
            System.out.println("ERROR: The storage is full");
            return;
        }
        if (findIndex(resume.getUuid()) != -1) {
            System.out.println("ERROR: This resume already exists in storage");
            return;
        }
        storage[size] = resume;
        size++;
    }

    public void delete(String uuid) {
        int indexOfResume = findIndex(uuid);
        if (indexOfResume != -1) {
            storage[indexOfResume] = storage[size - 1];
            storage[size - 1] = null;
            size--;
        }
    }

    public Resume get(String uuid) {
        int indexOfResume = findIndex(uuid);
        return indexOfResume != -1 ? storage[indexOfResume] : null;
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
        int indexOfResume = findIndex(resume.getUuid());
        if (indexOfResume == -1) {
            System.out.println("ERROR: No such resume in storage");
        } else {
            storage[indexOfResume] = resume;
        }
    }
}
