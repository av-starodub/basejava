package ru.javawebinar.basejava.exception;

import java.sql.SQLException;

public class ExistStorageException extends StorageException {
    public ExistStorageException(String uuid) {
        super(String.format("Resume %s already exist", uuid), uuid);
    }

    public ExistStorageException(SQLException e) {
        super(e);
    }
}
