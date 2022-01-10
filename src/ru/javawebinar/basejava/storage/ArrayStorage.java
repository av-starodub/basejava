package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage extends AbstractArrayStorage {

    public ArrayStorage() {
        this.storage = new Resume[STORAGE_LIMIT];
    }

    protected int getIndex(String uuid) {
        for (int i = 0; i < size; i++) {
            if (uuid.equals(storage[i].getUuid())) {
                return i;
            }
        }
        return -1;
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
        if (isResumeExist(getIndex(uuid))) {
            printResumeSearchErrorMessage(true, uuid);
            return;
        }
        storage[size] = resume;
        size++;
    }

    public void delete(String uuid) {
        int indexOfResume = getIndex(uuid);
        if (!isResumeExist(indexOfResume)) {
            printResumeSearchErrorMessage(false, uuid);
            return;
        }
        size--;
        storage[indexOfResume] = storage[size];
        storage[size] = null;
    }

    public void update(Resume resume) {
        if (isNull(resume)) {
            return;
        }
        String uuid = resume.getUuid();
        int indexOfResume = getIndex(uuid);
        if (!isResumeExist(indexOfResume)) {
            printResumeSearchErrorMessage(false, uuid);
            return;
        }
        storage[indexOfResume] = resume;
    }
}
