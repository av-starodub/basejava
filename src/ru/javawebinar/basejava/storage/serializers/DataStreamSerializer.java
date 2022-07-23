package ru.javawebinar.basejava.storage.serializers;

import ru.javawebinar.basejava.exception.StorageException;
import ru.javawebinar.basejava.model.Resume;
import ru.javawebinar.basejava.model.enumKeyTypes.*;
import ru.javawebinar.basejava.model.interfaces.*;
import ru.javawebinar.basejava.model.item.*;
import ru.javawebinar.basejava.model.sections.*;
import ru.javawebinar.basejava.storage.serializers.interfaces.*;

import java.io.*;
import java.util.*;

/**
 * The class implements Resume serialization using UTF-8.
 */
public class DataStreamSerializer implements Serializer {

    @Override
    public Resume doRead(InputStream is) throws IOException {
        try (DataInputStream dis = new DataInputStream(is)) {
            Resume resume = new Resume(dis.readUTF(), dis.readUTF());
            resume.setContacts(
                    readChapter(dis, ContactType.class,
                            () -> getEntry(ContactType.valueOf(dis.readUTF()), dis.readUTF()))
            );
            resume.setSections(
                    readChapter(dis, SectionType.class, () -> {
                        SectionType sectionType = SectionType.valueOf(dis.readUTF());
                        return getEntry(sectionType, readSection(dis, sectionType));
                    })
            );
            return resume;
        }
    }

    @Override
    public void doWrite(Resume resume, OutputStream os) throws IOException {
        try (DataOutputStream dos = new DataOutputStream(os)) {
            dos.writeUTF(resume.getUuid());
            dos.writeUTF(resume.getFullName());
            writeWithThrow(resume.getContacts().getAll(), dos, contactEntry ->
                    writeKeyValue(dos, getName(contactEntry.getKey()), contactEntry.getValue())
            );

            writeWithThrow(resume.getSections().getAll(), dos, sectionEntry -> {
                var sectionType = sectionEntry.getKey();
                var section = sectionEntry.getValue();
                dos.writeUTF(getName(sectionType));
                switch (sectionType) {
                    case PERSONAL, OBJECTIVE -> dos.writeUTF((String) section.getContent());
                    case ACHIEVEMENT, QUALIFICATIONS ->
                            writeWithThrow(((ListStringSection) section).getContent(), dos, dos::writeUTF);
                    case EXPERIENCE, EDUCATION ->
                            writeWithThrow(((ListItemSection) section).getContent(), dos, item -> {
                                writeWithThrow(item.getHeader().getAll(), dos, headerEntry ->
                                        writeKeyValue(dos, getName(headerEntry.getKey()), headerEntry.getValue())
                                );
                                writeWithThrow(item.getInfo(), dos, info ->
                                        writeWithThrow(info.getAll(), dos, block ->
                                                writeKeyValue(dos, getName(block.getKey()), block.getValue()))
                                );
                            });
                }
            });
        }
    }

    private String getName(KeyType key) {
        return String.valueOf(key);
    }

    private <K, V> Map.Entry<K, V> getEntry(K key, V value) {
        return new AbstractMap.SimpleEntry<>(key, value);
    }

    private <T> void writeWithThrow(Collection<T> collection, DataOutputStream dos, DataWriter<T> writer)
            throws IOException {
        dos.writeInt(collection.size());
        for (T element : collection) {
            writer.write(element);
        }
    }

    private void writeKeyValue(DataOutputStream dos, String key, String value) throws IOException {
        dos.writeUTF(key);
        dos.writeUTF(value);
    }

    private Section readSection(DataInputStream dis, SectionType type) throws IOException {
        switch (type) {
            case PERSONAL, OBJECTIVE -> {
                return new TextSection(dis.readUTF());
            }
            case ACHIEVEMENT, QUALIFICATIONS -> {
                return new ListStringSection(readListSection(dis, dis::readUTF));
            }
            case EXPERIENCE, EDUCATION -> {
                return new ListItemSection(
                        readListSection(dis, () -> new Item(
                                        readChapter(dis, HeaderType.class,
                                                () -> getEntry(HeaderType.valueOf(dis.readUTF()), dis.readUTF())
                                        ),
                                        readListSection(dis, () -> new Info() {{
                                            save(readChapter(dis, InfoType.class,
                                                    () -> getEntry(InfoType.valueOf(dis.readUTF()), dis.readUTF()))
                                            );
                                        }})
                                )
                        ));
            }
        }
        throw new StorageException("Section read error.");
    }

    private <K extends Enum<K>, V> EnumMap<K, V> readChapter(DataInputStream dis, Class<K> key, EntryReader<K, V> reader)
            throws IOException {
        int chapterSize = dis.readInt();
        return new EnumMap<>(key) {{
            for (int idx = 0; idx < chapterSize; idx++) {
                Map.Entry<K, V> entry = reader.read();
                put(entry.getKey(), entry.getValue());
            }
        }};
    }

    private <T> List<T> readListSection(DataInputStream dis, DataReader<T> reader) throws IOException {
        int sectionSize = dis.readInt();
        return new ArrayList<>() {{
            for (int idx = 0; idx < sectionSize; idx++) {
                add(reader.read());
            }
        }};
    }
}
