package ru.javawebinar.basejava.model.item;

import ru.javawebinar.basejava.model.enumKeyTypes.HeaderType;
import ru.javawebinar.basejava.model.sections.ListInfoSection;

import java.util.*;

/**
 * The class to store information about an organisation in a person's career.
 */
public class Item {
    private final Header header;
    private final ListInfoSection info;

    public Item(EnumMap<HeaderType, String> header, List<Info> info) {
        this.header = new Header() {{
            addAll(Objects.requireNonNull(header));
        }};
        this.info = new ListInfoSection(info);
    }

    /**
     * @return Collections.unmodifiableSet.
     * Attempting to modify will result in an UnsupportedOperationException in runtime.
     */
    public Set<Map.Entry<HeaderType, String>> getHeader() {
        return header.getAll();
    }
    /**
     * @return unmodifiable List.
     * Attempting to modify will result in an UnsupportedOperationException in runtime.
     */
    public List<Info> getInfo() {
        return info.getContent();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Item item = (Item) o;

        if (!header.equals(item.header)) return false;
        return info.equals(item.info);
    }

    @Override
    public int hashCode() {
        int result = header.hashCode();
        result = 31 * result + info.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return header + info.toString();
    }
}
