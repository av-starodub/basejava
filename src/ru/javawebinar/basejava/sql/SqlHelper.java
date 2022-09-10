package ru.javawebinar.basejava.sql;

import ru.javawebinar.basejava.exception.ExistStorageException;
import ru.javawebinar.basejava.exception.StorageException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SqlHelper {
    private final ConnectionFactory connectionFactory;

    public SqlHelper(ConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
    }

    public <T> T doAction(String sql, SqlAction<T> action) {
        try (Connection conn = connectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            return action.action(ps);
        } catch (SQLException e) {
            if (isUniqueViolation(e)) {
                throw new ExistStorageException(e);
            }
            throw new StorageException(e);
        }
    }

    private boolean isUniqueViolation(SQLException e) {
        return "23505".equals(e.getSQLState());
    }
}
