package ru.javawebinar.basejava.model.sections;

import java.io.Serial;
import java.util.List;

/**
 * The class to store List of strings.
 */
public class ListStringSection extends AbstractListSection<String> {
    @Serial
    private static final long serialVersionUID = 1L;

    public ListStringSection(List<String> content) {
        super(content);
    }
}
