package ru.javawebinar.basejava.model.chapters;

import ru.javawebinar.basejava.model.interfaces.Chapter;

import java.util.*;

/**
 * Base class for Contacts and Sections chapter of Resume
 * and for Header and Info chapters of Item for ListItemSection.
 *
 * @param <K> Search key type - Enum.
 * @param <V> any reference data type.
 */

public abstract class AbstractEnumChapter<K extends Enum<K>, V> implements Chapter<K, V> {
    private final EnumMap<K, V> chapter;

    protected AbstractEnumChapter(EnumMap<K, V> chapter) {
        this.chapter = chapter;
    }

    @Override
    public String getTitle(K key) {
        return title(key);
    }

    protected abstract String title(K key);

    @Override
    public V get(K key) {
        return chapter.getOrDefault(key, getDefault());
    }

    protected abstract V getDefault();

    @Override
    public void save(EnumMap<K, V> items) {
        if (!chapter.isEmpty()) {
            throw new UnsupportedOperationException("Unable to execute addAll(). The content already exists.");
        }
        this.chapter.putAll(Objects.requireNonNull(items));
    }

    @Override
    public Set<Map.Entry<K, V>> getAll() {
        return Collections.unmodifiableMap(Objects.requireNonNull(chapter)).entrySet();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AbstractEnumChapter<?, ?> that = (AbstractEnumChapter<?, ?>) o;

        return getAll().equals(that.getAll());
    }

    @Override
    public int hashCode() {
        return chapter.hashCode();
    }

    @Override
    public String toString() {
        StringBuilder chapter = new StringBuilder();
        getAll().forEach((item) ->
                chapter.append(getTitle(item.getKey())).append("\n").append(item.getValue()).append("\n"));
        return chapter.toString();
    }
}
