package ru.javawebinar.basejava.model.sections;

import ru.javawebinar.basejava.model.interfaces.Section;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Base class for all types of section which contains List as content.
 *
 * @param <T> any reference data type of section.
 */
public class AbstractListSection<T> implements Section<List<T>> {
    private final List<T> content;

    protected AbstractListSection(List<T> content) {
        this.content = new ArrayList<>(Objects.requireNonNull(content));
    }

    @Override
    public List<T> getContent() {
        return List.copyOf(content);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AbstractListSection<?> that = (AbstractListSection<?>) o;

        return content.equals(that.content);
    }

    @Override
    public int hashCode() {
        return content.hashCode();
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        content.forEach(T -> result.append(T.toString()).append("\n"));
        return result.toString();
    }
}
