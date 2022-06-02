package ru.javawebinar.basejava.model.chapters;

import ru.javawebinar.basejava.model.interfaces.Chapter;

import java.util.EnumMap;
import java.util.Map;
import java.util.Set;

public class AbstractEnumChapter<K extends Enum<K>, V> implements Chapter<K, V> {
    @Override
    public String getTitle(K key) {
        return null;
    }

    @Override
    public void addAll(EnumMap<K, V> items) {

    }

    @Override
    public Set<Map.Entry<K, V>> getAll() {
        return null;
    }
}
