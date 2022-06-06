package ru.javawebinar.basejava.model.sections;

import ru.javawebinar.basejava.model.interfaces.Section;

import java.util.List;
import java.util.Objects;

import static java.util.stream.Collectors.counting;
import static java.util.stream.Collectors.groupingBy;

/**
 * Base class for all types of section which contains List as content.
 *
 * @param <T> any reference data type. For custom class T equals and hashcode() must be override.
 */
public abstract class AbstractListSection<T> implements Section<List<T>> {
    private final List<T> content;

    protected AbstractListSection(List<T> content) {
        this.content = checkContent(content);
    }

    private List<T> checkContent(List<T> content) {
        return Objects.requireNonNull(content).stream().filter(Objects::nonNull).toList();
    }
    @Override
    public List<T> getContent() {
        return content;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        List<?> that = ((AbstractListSection<?>) o).getContent();

        return Objects.equals(content.stream().collect(groupingBy(key -> key, counting())),
                that.stream().collect(groupingBy(key -> key, counting())));
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
