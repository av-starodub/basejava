package ru.javawebinar.basejava.storage.serializers;

import java.io.IOException;

@FunctionalInterface
public interface DataWriter<T> {
    void write(T data) throws IOException;
}
