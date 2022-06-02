package ru.javawebinar.basejava.model.sections;

import ru.javawebinar.basejava.model.item.Info;

import java.util.List;

public class ListInfoSection extends AbstractListSection<Info> {
    public ListInfoSection(List<Info> content) {
        super(content);
    }
}
