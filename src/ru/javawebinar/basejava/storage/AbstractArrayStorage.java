package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

import java.util.Arrays;

public abstract class AbstractArrayStorage implements Storage {
    protected static final int STORAGE_LIMIT = 10000;
    protected Resume[] storage = new Resume[STORAGE_LIMIT];
    protected int size;

    private boolean isNull(Resume resume) {
        return resume.getUuid() == null;
    }

    private boolean isResumeExist(int index) {
        return index >= 0;
    }

    private boolean hasStorageFreeSpace() {
        return size < storage.length;
    }

    private void printStorageOverflowErrorMessage(String uuid) {
        System.out.printf("ERROR: %s not added. The storage is full\n", uuid);
    }

    private void printResumeSearchErrorMessage(boolean wasFound, String uuid) {
        if (wasFound) {
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
        int resumeIndex = getIndex(uuid);
        if (!isResumeExist(resumeIndex)) {
            printResumeSearchErrorMessage(false, uuid);
            return null;
        }
        return storage[resumeIndex];
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
        int index = getIndex(uuid);
        if (isResumeExist(index)) {
            printResumeSearchErrorMessage(true, uuid);
            return;
        }
        insert(resume, index);
        size++;
    }

    public void delete(String uuid) {
        int resumeIndex = getIndex(uuid);
        if (!isResumeExist(resumeIndex)) {
            printResumeSearchErrorMessage(false, uuid);
            return;
        }
        size--;
        remove(resumeIndex);
        storage[size] = null;
    }

    public void update(Resume resume) {
        if (isNull(resume)) {
            return;
        }
        String uuid = resume.getUuid();
        int resumeIndex = getIndex(uuid);
        if (!isResumeExist(resumeIndex)) {
            printResumeSearchErrorMessage(false, uuid);
            return;
        }
        storage[resumeIndex] = resume;
    }

    protected abstract int getIndex(String uuid);

    protected abstract void insert(Resume resume, int insertionPoint);

    protected abstract void remove(int resumeIndex);
}
