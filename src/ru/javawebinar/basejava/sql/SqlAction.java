package ru.javawebinar.basejava.sql;

import java.sql.PreparedStatement;
import java.sql.SQLException;

@FunctionalInterface
public interface SqlAction<T> {
    T action(PreparedStatement ps) throws SQLException;
}
