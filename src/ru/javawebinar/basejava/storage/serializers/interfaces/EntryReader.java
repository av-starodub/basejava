package ru.javawebinar.basejava.storage.serializers.interfaces;

import java.io.IOException;
import java.util.Map;

@FunctionalInterface
public interface EntryReader<K, V> {
    Map.Entry<K, V> read() throws IOException;
}
