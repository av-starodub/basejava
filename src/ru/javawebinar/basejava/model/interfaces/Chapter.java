package ru.javawebinar.basejava.model.interfaces;

import java.util.EnumMap;
import java.util.Map;
import java.util.Set;

public interface Chapter<K extends Enum<K>, V> {
    String getTitle(K key);

    void addAll(EnumMap<K, V> items);

    Set<Map.Entry<K, V>> getAll();
}
