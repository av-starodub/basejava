package ru.javawebinar.basejava.model.sections;

import ru.javawebinar.basejava.model.item.Info;

import java.io.Serial;
import java.util.List;

public class ListInfoSection extends AbstractListSection<Info> {
    @Serial
    private static final long serialVersionUID = 1L;

    public ListInfoSection(List<Info> content) {
        super(content);
    }
}
