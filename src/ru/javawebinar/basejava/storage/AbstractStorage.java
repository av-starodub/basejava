package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.ExistStorageException;
import ru.javawebinar.basejava.exception.NotExistStorageException;
import ru.javawebinar.basejava.model.Resume;

import java.util.List;

/**
 * Base class for all Storage types.
 *
 * @param <T> Storage data structure type.
 * @param <K> type of search key to Storage.
 */
public abstract class AbstractStorage<T, K> implements Storage {
    protected final T storage;

    protected AbstractStorage(T storage) {
        this.storage = storage;
    }

    protected boolean isNotNull(Resume resume) {
        return resume != null;
    }

    /**
     * @param expectationForError the result of method isResumeExist is necessary to
     *                            throw an exception in the context of the call checkResumeExist.
     * @return getSearchKey(String uuid) result.
     */
    private K checkResumeExist(String uuid, boolean expectationForError) {
        K searchKey = getSearchKey(uuid);
        if (expectationForError && isResumeExist(searchKey)) {
            throw new ExistStorageException(uuid);
        }
        if (!expectationForError && !isResumeExist(searchKey)) {
            throw new NotExistStorageException(uuid);
        }
        return searchKey;
    }

    @Override
    public void save(Resume resume) {
        if (isNotNull(resume)) {
            insert(resume, checkResumeExist(resume.getUuid(), true));
        }
    }

    @Override
    public void delete(String uuid) {
        remove(checkResumeExist(uuid, false));
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

    @Override
    public List<Resume> getAllSorted() {
        List<Resume> resumes = getAll();
        resumes.sort(Resume::compareTo);
        return resumes;
    }

    protected abstract K getSearchKey(String uuid);

    protected abstract Resume getResume(K searchKey);

    protected abstract List<Resume> getAll();

    protected abstract void insert(Resume resume, K insertKey);

    protected abstract void replace(K searchKey, Resume resume);

    protected abstract void remove(K searchKey);

    protected abstract boolean isResumeExist(K searchKey);
}
