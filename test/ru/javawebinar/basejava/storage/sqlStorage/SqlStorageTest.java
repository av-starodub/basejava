package ru.javawebinar.basejava.storage.sqlStorage;

import ru.javawebinar.basejava.storage.AbstractStorageTest;

public class SqlStorageTest extends AbstractStorageTest {
    public SqlStorageTest() {
        super(new SqlStorage(
                "jdbc:postgresql://localhost:5432/resumes",
                "postgres",
                "postgres")
        );
    }
}
