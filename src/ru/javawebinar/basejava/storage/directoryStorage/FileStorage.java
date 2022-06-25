package ru.javawebinar.basejava.storage.directoryStorage;

import ru.javawebinar.basejava.exception.StorageException;
import ru.javawebinar.basejava.storage.serializers.Serializer;

import java.io.*;
import java.nio.file.Path;
import java.util.Objects;

/**
 * File type Storage.
 * Search key type - object File.
 */
public abstract class FileStorage extends AbstractDirectoryStorage<File, File> {

    public FileStorage(File directory, Serializer serializer) {
        super(checkDirectory(Objects.requireNonNull(directory, " directory must not be null")), serializer);
    }

    private static File checkDirectory(File directory) {
        if (!directory.isDirectory()) {
            throw new IllegalArgumentException(directory.getAbsolutePath() + " is not directory");
        }
        if (!directory.canRead() || !directory.canWrite()) {
            throw new IllegalArgumentException(directory.getAbsolutePath() + " is not readable/writable");
        }
        return directory;
    }

    @Override
    protected Path getDirectoryPath() {
        return Path.of(storage.getAbsolutePath());
    }

    @Override
    protected File getKey(Path path) {
        return path.toFile();
    }

    @Override
    protected InputStream getInputStream(File file) throws IOException {
        return new BufferedInputStream(new FileInputStream(file));
    }

    @Override
    protected OutputStream getOutputStream(File file) {
        try {
            return new BufferedOutputStream(new FileOutputStream(file));
        } catch (FileNotFoundException e) {
            throw new StorageException("File not found", file.getName(), e);
        }
    }

    @Override
    protected String getFileName(File file) {
        return file.getName();
    }

    /**
     * @param file Written @SuppressWarnings("ResultOfMethodCallIgnored") for createNewFile() because the existence
     *             of the file was already checked in the isResumeExist method before calling the insert method.
     */
    @SuppressWarnings("ResultOfMethodCallIgnored")
    @Override
    protected void createNewFile(File file) throws IOException {
        file.createNewFile();
    }

    @Override
    protected Path getPath(File file) {
        return file.toPath();
    }

    @Override
    protected boolean isResumeExist(File file) {
        return file.exists();
    }

    @Override
    protected File getSearchKey(String uuid) {
        return new File(storage, uuid);
    }
}
