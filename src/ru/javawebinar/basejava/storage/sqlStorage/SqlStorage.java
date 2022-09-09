package ru.javawebinar.basejava.storage.sqlStorage;

import ru.javawebinar.basejava.exception.*;
import ru.javawebinar.basejava.model.Resume;
import ru.javawebinar.basejava.sql.SqlHelper;
import ru.javawebinar.basejava.storage.Storage;

import java.sql.*;
import java.util.*;

public class SqlStorage implements Storage {
    private final SqlHelper sqlHelper;

    public SqlStorage(String dbUrl, String dbUser, String dbPassword) {
        sqlHelper = new SqlHelper(() -> DriverManager.getConnection(dbUrl, dbUser, dbPassword));
    }

    @Override
    public void clear() {
        sqlHelper.doAction("delete from resume", PreparedStatement::execute);
    }

    @Override
    public void update(Resume resume) {
        sqlHelper.doAction("update resume set full_name=? where uuid=?", ps -> {
            ps.setString(1, resume.getFullName());
            ps.setString(2, resume.getUuid());
            if (isNotExist(ps)) {
                throw new NotExistStorageException(resume.getUuid());
            }
            return null;
        });
    }

    @Override
    public void save(Resume resume) {
        sqlHelper.doAction("insert into resume (uuid, full_name) values (?, ?)", ps -> {
            try {
                ps.setString(1, resume.getUuid());
                ps.setString(2, resume.getFullName());
                ps.execute();
            } catch (SQLException e) {
                throw new ExistStorageException(e);
            }
            return null;
        });
    }

    @Override
    public Resume get(String uuid) {
        return sqlHelper.doAction("select * from resume r where r.uuid =?", ps -> {
            ps.setString(1, uuid);
            ResultSet rs = ps.executeQuery();
            if (!rs.next()) {
                throw new NotExistStorageException(uuid);
            }
            return new Resume(uuid, rs.getString("full_name"));
        });
    }

    @Override
    public void delete(String uuid) {
        sqlHelper.doAction("delete from resume r where r.uuid =?", ps -> {
            ps.setString(1, uuid);
            if (isNotExist(ps)) {
                throw new NotExistStorageException(uuid);
            }
            return null;
        });
    }

    @Override
    public List<Resume> getAllSorted() {
        return sqlHelper.doAction("select * from resume", ps -> {
            ResultSet rs = ps.executeQuery();
            SortedSet<Resume> resumeSet = new TreeSet<>();
            while (rs.next()) {
                resumeSet.add(new Resume(rs.getString("uuid").trim(), rs.getString("full_name")));
            }
            return new ArrayList<>(resumeSet.stream().toList());
        });
    }

    @Override
    public int size() {
        return sqlHelper.doAction("select count(*) from resume", ps -> {
            ResultSet rs = ps.executeQuery();
            rs.next();
            return rs.getInt(1);
        });
    }

    private boolean isNotExist(PreparedStatement ps) throws SQLException {
        return ps.executeUpdate() == 0;
    }
}
