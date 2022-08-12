package ru.javawebinar.basejava.storage.serializers.interfaces;

import java.io.IOException;

@FunctionalInterface
public interface DataReader<T> {
    T read() throws IOException;
}
