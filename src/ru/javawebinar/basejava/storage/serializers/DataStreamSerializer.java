package ru.javawebinar.basejava.storage.serializers;

import ru.javawebinar.basejava.model.Resume;
import ru.javawebinar.basejava.model.enumKeyTypes.ContactType;
import ru.javawebinar.basejava.model.enumKeyTypes.HeaderType;
import ru.javawebinar.basejava.model.enumKeyTypes.InfoType;
import ru.javawebinar.basejava.model.enumKeyTypes.SectionType;
import ru.javawebinar.basejava.model.interfaces.KeyType;
import ru.javawebinar.basejava.model.item.Info;
import ru.javawebinar.basejava.model.item.Item;
import ru.javawebinar.basejava.model.sections.ListItemSection;
import ru.javawebinar.basejava.model.sections.ListStringSection;
import ru.javawebinar.basejava.model.sections.TextSection;

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
            resume.setContacts(readChapter(dis, ContactType.class));
            resume.setSections(new EnumMap<>(SectionType.class) {{
                dis.readInt();
                put(SectionType.valueOf(dis.readUTF()), readTextSection(dis));
                put(SectionType.valueOf(dis.readUTF()), readTextSection(dis));
                put(SectionType.valueOf((dis.readUTF())), readListStringSection(dis, dis.readInt()));
                put(SectionType.valueOf(dis.readUTF()), readListStringSection(dis, dis.readInt()));
                put(SectionType.valueOf(dis.readUTF()), readListItemSection(dis, dis.readInt()));
                put(SectionType.valueOf(dis.readUTF()), readListItemSection(dis, dis.readInt()));
            }});
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

    private <T> void writeWithThrow(Collection<T> collection, DataOutputStream dos, DataWriter<T> writer) throws IOException {
        dos.writeInt(collection.size());
        for (T element : collection) {
            writer.write(element);
        }
    }

    private void writeKeyValue(DataOutputStream dos, String key, String value) throws IOException {
        dos.writeUTF(key);
        dos.writeUTF(value);
    }

    private <K extends Enum<K>> EnumMap<K, String> readChapter(DataInputStream dis, Class<K> key) throws IOException {
        int chapterSize = dis.readInt();
        return new EnumMap<>(key) {{
            for (int idx = 0; idx < chapterSize; idx++) {
                put(K.valueOf(key, dis.readUTF()), dis.readUTF());
            }
        }};
    }

    private TextSection readTextSection(DataInputStream dis) throws IOException {
        return new TextSection(dis.readUTF());
    }

    private ListStringSection readListStringSection(DataInputStream dis, int sectionSize) throws IOException {
        return new ListStringSection(new ArrayList<>() {{
            for (int idx = 0; idx < sectionSize; idx++) {
                add(dis.readUTF());
            }
        }});
    }

    private ListItemSection readListItemSection(DataInputStream dis, int sectionSize) throws IOException {
        return new ListItemSection(new ArrayList<>() {{
            for (int idx = 0; idx < sectionSize; idx++) {
                add(new Item(readChapter(
                        dis, HeaderType.class),
                        new ArrayList<>() {{
                            int infoSize = dis.readInt();
                            for (int idx = 0; idx < infoSize; idx++) {
                                add(new Info() {{
                                    save(readChapter(dis, InfoType.class));
                                }});
                            }
                        }}
                ));
            }
        }});
    }
}
