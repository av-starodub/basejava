package ru.javawebinar.basejava.model.sections;

import ru.javawebinar.basejava.model.interfaces.Section;

import java.util.Objects;

/**
 * The class to store simple text.
 */
public class TextSection implements Section<String> {
    private final String text;

    public TextSection(String text) {
        this.text = Objects.requireNonNull(text);
    }

    @Override
    public String getContent() {
        return text;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TextSection that = (TextSection) o;

        return text.equals(that.text);
    }

    @Override
    public int hashCode() {
        return text.hashCode();
    }

    @Override
    public String toString() {
        return text + "\n";
    }
}
