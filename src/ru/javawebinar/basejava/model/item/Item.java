package ru.javawebinar.basejava.model.item;

import ru.javawebinar.basejava.model.chapters.AbstractEnumChapter;
import ru.javawebinar.basejava.model.enumKeyTypes.HeaderType;
import ru.javawebinar.basejava.model.sections.ListInfoSection;

import java.util.EnumMap;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.counting;

/**
 * The class to store information about an organisation in a person's career.
 */
public class Item {
    private final Header header;
    private final ListInfoSection info;

    public Item(EnumMap<HeaderType, String> header, List<Info> info) {
        this.header = new Header() {{
            save(Objects.requireNonNull(header));
        }};
        this.info = new ListInfoSection(info);
    }

    /**
     * @return Collections.unmodifiableSet.
     * Attempting to modify will result in an UnsupportedOperationException in runtime.
     */
    public Header getHeader() {
        return header;
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
        List<Info> that = item.getInfo();
        if (info.getContent().size() != that.size()) return false;

        return Objects.equals(getInfo().stream().collect(Collectors.groupingBy(AbstractEnumChapter::getAll, counting())),
                that.stream().collect(Collectors.groupingBy(AbstractEnumChapter::getAll, counting())));
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
