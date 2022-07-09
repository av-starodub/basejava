package ru.javawebinar.basejava.model.interfaces;

import java.io.Serializable;
import java.util.EnumMap;
import java.util.Map;
import java.util.Set;

public interface Chapter<K extends Enum<K>, V> extends Serializable {
    String getTitle(K key);

    void save(EnumMap<K, V> items);

    V get(K key);
    Set<Map.Entry<K, V>> getAll();
}
