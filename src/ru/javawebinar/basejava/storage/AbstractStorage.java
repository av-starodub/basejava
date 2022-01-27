package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.ExistStorageException;
import ru.javawebinar.basejava.exception.NotExistStorageException;
import ru.javawebinar.basejava.model.Resume;

public abstract class AbstractStorage<T> implements Storage {
    protected final T storage;

    protected AbstractStorage(T storage) {
        this.storage = storage;
    }

    private boolean isNotNull(Resume resume) {
        return resume != null;
    }

    private boolean isResumeExist(int index) {
        return index >= 0;
    }

    /**
     * @param expectationForError the result of method isResumeExist is necessary to
     *                            throw an exception in the context of the call checkResumeExist.
     * @return getIndex result.
     */
    protected int checkResumeExist(String uuid, boolean expectationForError) {
        int indexOfResume = getIndex(uuid);
        if (expectationForError && isResumeExist(indexOfResume)) {
            throw new ExistStorageException(uuid);
        }
        if (!expectationForError && !isResumeExist(indexOfResume)) {
            throw new NotExistStorageException(uuid);
        }
        return indexOfResume;
    }

    @Override
    public void save(Resume resume) {
        if (isNotNull(resume)) {
            insert(resume, checkResumeExist(resume.getUuid(), true));
        }
    }

    @Override
    public Resume get(String uuid) {
        return getResume(checkResumeExist(uuid, false));
    }

    @Override
    public void update(Resume resume) {
        if (isNotNull(resume)) {
            replace(checkResumeExist(resume.getUuid(), false), resume);
        }
    }

    protected abstract int getIndex(String uuid);

    protected abstract Resume getResume(int index);

    protected abstract void insert(Resume resume, int index);

    protected abstract void replace(int index, Resume resume);
}
