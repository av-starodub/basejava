package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

import java.util.Arrays;

public abstract class AbstractArrayStorage implements Storage {
    protected static final int STORAGE_LIMIT = 10000;
    protected Resume[] storage;
    protected int size;

    protected boolean isNull(Resume resume) {
        return resume.getUuid() == null;
    }

    protected boolean isResumeExist(int index) {
        return index != -1;
    }

    protected boolean hasStorageFreeSpace() {
        return size < storage.length;
    }

    protected void printStorageOverflowErrorMessage(String uuid) {
        System.out.printf("ERROR: %s not added. The storage is full\n", uuid);
    }

    protected void printResumeSearchErrorMessage(boolean isFound, String uuid) {
        if (isFound) {
            System.out.printf("ERROR: %s already exists in storage\n", uuid);
        } else {
            System.out.printf("ERROR: %s no such in storage\n", uuid);
        }
    }

    public int size() {
        return size;
    }

    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    public Resume[] getAll() {
        return Arrays.copyOf(storage, size);
    }

    public Resume get(String uuid) {
        int indexOfResume = getIndex(uuid);
        if (!isResumeExist(indexOfResume)) {
            printResumeSearchErrorMessage(false, uuid);
            return null;
        }
        return storage[indexOfResume];
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
        insert(resume);
    }

    public void delete(String uuid) {
        int indexOfResume = getIndex(uuid);
        if (!isResumeExist(indexOfResume)) {
            printResumeSearchErrorMessage(false, uuid);
            return;
        }
        remove(indexOfResume);
    }

    protected abstract int getIndex(String uuid);

    protected abstract void insert(Resume resume);

    protected abstract void remove(int index);
}
