package ru.javawebinar.basejava.sql;

import ru.javawebinar.basejava.exception.ExistStorageException;
import ru.javawebinar.basejava.exception.StorageException;

import java.sql.SQLException;

public class SqlHelper {
    private final ConnectionFactory connectionFactory;

    public SqlHelper(ConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
    }

    public <T> T doAction(String sql, SqlAction<T> action) {
        try (var connection = connectionFactory.getConnection();
             var prepareStatement = connection.prepareStatement(sql)) {
            return action.action(prepareStatement);
        } catch (SQLException e) {
            if ("23505".equals(e.getSQLState())) {
                throw new ExistStorageException(e);
            }
            throw new StorageException(e);
        }
    }
}
