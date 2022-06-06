package ru.javawebinar.basejava.model.sections;

import ru.javawebinar.basejava.model.item.Item;

import java.util.List;

/**
 * The class to store List of Items.
 */
public class ListItemSection extends AbstractListSection<Item> {
    public ListItemSection(List<Item> content) {
        super(content);
    }
}
