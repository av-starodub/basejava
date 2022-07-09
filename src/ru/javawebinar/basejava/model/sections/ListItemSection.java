package ru.javawebinar.basejava.model.sections;

import ru.javawebinar.basejava.model.item.Item;

import java.io.Serial;
import java.util.List;

/**
 * The class to store List of Items.
 */
public class ListItemSection extends AbstractListSection<Item> {
    @Serial
    private static final long serialVersionUID = 1L;

    public ListItemSection(List<Item> content) {
        super(content);
    }
}
