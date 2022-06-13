package ru.javawebinar.basejava.storage;

import java.io.File;
import java.util.Objects;

public abstract class AbstractFileStorage extends AbstractStorage<File, File> {
    protected AbstractFileStorage(File directory) {
        super(checkParameter(directory));
    }

    private static File checkParameter(File directory) {
        Objects.requireNonNull(directory, "directory must not be null");
        if (!directory.isDirectory()) {
            throw new IllegalArgumentException(directory.getAbsolutePath() + "is not directory");
        }
        if (!directory.canRead() || !directory.canWrite()) {
            throw new IllegalArgumentException(directory.getAbsolutePath() + "is not readable/writable");
        }
        return directory;
    }
}
