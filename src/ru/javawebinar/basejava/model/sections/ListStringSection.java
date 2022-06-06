package ru.javawebinar.basejava.model.sections;

import java.util.List;

/**
 * The class to store List of strings.
 */
public class ListStringSection extends AbstractListSection<String> {
    public ListStringSection(List<String> content) {
        super(content);
    }
}
