package ru.javawebinar.basejava.model.item;

import ru.javawebinar.basejava.model.enumKeyTypes.HeaderType;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Objects;

/**
 * The class to store information about an organisation in a person's career.
 */
public class Item {
    private final Header header;
    private final List<Info> informationBlocks;

    public Item(EnumMap<HeaderType, String> header, List<Info> info) {
        this.header = new Header() {{
            addAll(Objects.requireNonNull(header));
        }};
        informationBlocks = new ArrayList<>(Objects.requireNonNull(info));
    }

    public Header getHeader() {
        return header;
    }

    public List<Info> getInfo() {
        return List.copyOf(informationBlocks);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Item item = (Item) o;

        if (!header.equals(item.header)) return false;
        return informationBlocks.equals(item.informationBlocks);
    }

    @Override
    public int hashCode() {
        int result = header.hashCode();
        result = 31 * result + informationBlocks.hashCode();
        return result;
    }

    private String toStringListInfo() {
        StringBuilder info = new StringBuilder();
        informationBlocks.forEach(i -> info.append(i).append("\n"));
        return info.toString();
    }

    @Override
    public String toString() {
        return header + toStringListInfo();
    }
}
