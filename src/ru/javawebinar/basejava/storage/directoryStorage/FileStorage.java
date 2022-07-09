package ru.javawebinar.basejava.storage.directoryStorage;

import ru.javawebinar.basejava.exception.StorageException;
import ru.javawebinar.basejava.model.Resume;
import ru.javawebinar.basejava.storage.AbstractStorage;
import ru.javawebinar.basejava.storage.serializers.Serializer;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class FileStorage extends AbstractStorage<File, File> {
    private final Serializer serializer;

    protected FileStorage(File directory, Serializer serializer) {
        super(checkDirectory(directory));
        this.serializer = serializer;
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

    private File[] getListFiles() {
        var files = storage.listFiles();
        if (Objects.isNull(files)) {
            throw new StorageException("Storage read error");
        }
        return files;
    }

    @Override
    public void clear() {
        Arrays.stream(getListFiles()).forEach(this::remove);
    }

    @Override
    public int size() {
        return getListFiles().length;
    }

    @Override
    protected List<Resume> getAll() {
        return new ArrayList<>() {{
            Arrays.stream(getListFiles()).forEach(file -> this.add(getResume(file)));
        }};
    }

    @Override
    protected File getSearchKey(String uuid) {
        return new File(storage, uuid);
    }

    @Override
    protected Resume getResume(File file) {
        try {
            return serializer.doRead(new BufferedInputStream(new FileInputStream(file)));
        } catch (IOException e) {
            throw new StorageException("File read error ", file.getName(), e);
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
        } catch (IOException e) {
            throw new StorageException("Create file error ", file.getName(), e);
        }
        replace(file, resume);
    }

    @Override
    protected void replace(File file, Resume resume) {
        try {
            serializer.doWrite(resume, new BufferedOutputStream(new FileOutputStream(file)));
        } catch (IOException e) {
            throw new StorageException("Write file error ", file.getName(), e);
        }
    }

    @Override
    protected void remove(File file) {
        if (!file.delete()) {
            throw new StorageException("Delete error" + file.getAbsolutePath(), file.getName());
        }
    }

    @Override
    protected boolean isResumeExist(File file) {
        return file.exists();
    }
}
