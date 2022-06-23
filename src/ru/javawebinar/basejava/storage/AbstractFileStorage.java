package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.StorageException;
import ru.javawebinar.basejava.model.Resume;

import java.io.File;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;

/**
 * Base class for all FileStorage types.
 * Type of search key to Storage - File. It must be a file only.
 */
public abstract class AbstractFileStorage extends AbstractDirectoryStorage<File, File> {

    /**
     * @param directory - storage data structure type - File. It must be a directory only.
     */
    protected AbstractFileStorage(File directory) {
        super(checkDirectory(directory));
    }

    private static File checkDirectory(File directory) {
        Objects.requireNonNull(directory, " directory must not be null");
        if (!directory.isDirectory()) {
            throw new IllegalArgumentException(directory.getAbsolutePath() + " is not directory");
        }
        if (!directory.canRead() || !directory.canWrite()) {
            throw new IllegalArgumentException(directory.getAbsolutePath() + " is not readable/writable");
        }
        return directory;
    }

    private DirectoryStream<Path> getDirectoryStream() throws IOException {
        return Files.newDirectoryStream(Path.of(storage.getAbsolutePath()));
    }

    /**
     * @param function - the action will be applied to each path element in the directory stream.
     */
    private void forEachInDirectoryStream(Function<Path, ?> function) {
        try (var directoryStream = getDirectoryStream()) {
            directoryStream.forEach(function::apply);
        } catch (IOException e) {
            throw new StorageException("Storage read error " + storage.getAbsolutePath(), e);
        }
    }

    @Override
    public int size() {
        return new AtomicInteger() {{
            forEachInDirectoryStream(path -> this.getAndIncrement());
        }}.get();
    }

    @Override
    public void clear() {
        forEachInDirectoryStream(path -> {
            remove(path.toFile());
            return null;
        });
    }

    @Override
    protected void remove(File file) {
        if (!file.delete()) {
            throw new StorageException("Delete error " + file.getAbsolutePath(), file.getName());
        }
    }

    @Override
    protected File getSearchKey(String uuid) {
        return new File(storage, uuid);
    }

    @Override
    protected boolean isResumeExist(File file) {
        return file.exists();
    }

    @Override
    protected Resume getResume(File file) {
        try {
            return doRead(file);
        } catch (IOException e) {
            throw new StorageException("File read error " + file.getAbsolutePath(), file.getName(), e);
        }
    }

    /**
     * @param file Written @SuppressWarnings("ResultOfMethodCallIgnored") for createNewFile() because the existence
     *             of the file was already checked in the isResumeExist method before calling the insert method.
     */
    @SuppressWarnings("ResultOfMethodCallIgnored")
    @Override
    protected void insert(Resume resume, File file) {
        try {
            file.createNewFile();
            doWrite(resume, file);
        } catch (IOException e) {
            throw new StorageException("Save error " + file.getAbsolutePath(), file.getName(), e);
        }
    }

    @Override
    protected void replace(File file, Resume resume) {
        try {
            doWrite(resume, file);
        } catch (IOException e) {
            throw new StorageException("Update error " + file.getAbsolutePath(), file.getName(), e);
        }
    }

    @Override
    protected List<Resume> getAll() {
        return new ArrayList<>() {{
            forEachInDirectoryStream(path -> this.add(getResume(path.toFile())));
        }};
    }

    protected abstract Resume doRead(File file) throws IOException;

    protected abstract void doWrite(Resume resume, File file) throws IOException;
}
