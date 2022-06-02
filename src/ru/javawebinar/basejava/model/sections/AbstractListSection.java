package ru.javawebinar.basejava.model.sections;

import ru.javawebinar.basejava.model.interfaces.Section;

import java.util.List;

public class AbstractListSection<T> implements Section<List<T>> {
    @Override
    public List<T> getContent() {
        return null;
    }
}
