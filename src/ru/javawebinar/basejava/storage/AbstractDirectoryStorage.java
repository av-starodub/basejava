package ru.javawebinar.basejava.storage;

/**
 * Base class for all DirectoryStorage types. It must be a directory only.
 * Type of search key to Storage - File or Path type.
 */
public abstract class AbstractDirectoryStorage<T, K> extends AbstractStorage<T, K> {

    protected AbstractDirectoryStorage(T storage) {
        super(storage);
    }
}
