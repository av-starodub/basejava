package ru.javawebinar.basejava.storage.serializers;

import ru.javawebinar.basejava.model.Resume;
import ru.javawebinar.basejava.model.enumKeyTypes.ContactType;
import ru.javawebinar.basejava.model.enumKeyTypes.HeaderType;
import ru.javawebinar.basejava.model.enumKeyTypes.InfoType;
import ru.javawebinar.basejava.model.enumKeyTypes.SectionType;
import ru.javawebinar.basejava.model.item.Info;
import ru.javawebinar.basejava.model.item.Item;
import ru.javawebinar.basejava.model.sections.ListItemSection;
import ru.javawebinar.basejava.model.sections.ListStringSection;
import ru.javawebinar.basejava.model.sections.TextSection;

import java.io.*;
import java.util.*;

import static ru.javawebinar.basejava.model.enumKeyTypes.SectionType.*;

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
                put(SectionType.valueOf(dis.readUTF()), readTextSection(dis));
                put(SectionType.valueOf(dis.readUTF()), readTextSection(dis));
                int achievementSize = dis.readInt();
                put(SectionType.valueOf((dis.readUTF())), readListStringSection(dis, achievementSize));
                int qualificationsSize = dis.readInt();
                put(SectionType.valueOf(dis.readUTF()), readListStringSection(dis, qualificationsSize));
                int experienceSize = dis.readInt();
                put(SectionType.valueOf(dis.readUTF()), readListItemSection(dis, experienceSize));
                int educationSize = dis.readInt();
                put(SectionType.valueOf(dis.readUTF()), readListItemSection(dis, educationSize));
            }});
            return resume;
        }
    }

    @Override
    public void doWrite(Resume resume, OutputStream os) throws IOException {
        try (DataOutputStream dos = new DataOutputStream(os)) {
            dos.writeUTF(resume.getUuid());
            dos.writeUTF(resume.getFullName());
            writeChapter(dos, resume.getContacts().getAll());

            var sections = resume.getSections();
            writeTextSection(dos, PERSONAL, (TextSection) sections.get(PERSONAL));
            writeTextSection(dos, OBJECTIVE, (TextSection) sections.get(OBJECTIVE));
            writeListStringSection(dos, ACHIEVEMENT, (ListStringSection) sections.get(ACHIEVEMENT));
            writeListStringSection(dos, QUALIFICATIONS, (ListStringSection) sections.get(QUALIFICATIONS));
            writeListItemSectionToUTF(dos, EXPERIENCE, (ListItemSection) sections.get(EXPERIENCE));
            writeListItemSectionToUTF(dos, EDUCATION, (ListItemSection) sections.get(EDUCATION));
        }
    }

    private <K extends Enum<K>> EnumMap<K, String> readChapter(DataInputStream dis, Class<K> key) throws IOException {
        int chapterSize = dis.readInt();
        return new EnumMap<>(key) {{
            for (int idx = 0; idx < chapterSize; idx++) {
                put(K.valueOf(key, dis.readUTF()), dis.readUTF());
            }
        }};
    }

    private <K extends Enum<K>> void writeChapter(DataOutputStream dos, Set<Map.Entry<K, String>> entries) throws IOException {
        dos.writeInt(entries.size());
        for (Map.Entry<K, String> entry : entries) {
            dos.writeUTF(java.lang.String.valueOf(entry.getKey()));
            dos.writeUTF(entry.getValue());
        }
    }

    private TextSection readTextSection(DataInputStream dis) throws IOException {
        return new TextSection(dis.readUTF());
    }

    private void writeTextSection(DataOutputStream dos, SectionType key, TextSection section) throws IOException {
        dos.writeUTF(java.lang.String.valueOf(key));
        dos.writeUTF(section.getContent());
    }


    private ListStringSection readListStringSection(DataInputStream dis, int sectionSize) throws IOException {
        return new ListStringSection(new ArrayList<>() {{
            for (int idx = 0; idx < sectionSize; idx++) {
                add(dis.readUTF());
            }
        }});
    }

    private void writeListStringSection(DataOutputStream dos, SectionType key, ListStringSection section) throws IOException {
        List<String> qualificationsContent = section.getContent();
        dos.writeInt(qualificationsContent.size());
        dos.writeUTF(java.lang.String.valueOf(key));
        for (String content : qualificationsContent) {
            dos.writeUTF(content);
        }
    }

    private ListItemSection readListItemSection(DataInputStream dis, int sectionSize) throws IOException {
        return new ListItemSection(new ArrayList<>() {{
            for (int idx = 0; idx < sectionSize; idx++) {
                add(new Item(readChapter(
                        dis, HeaderType.class),
                        new ArrayList<>() {{
                            int eduInfoSize = dis.readInt();
                            for (int idx = 0; idx < eduInfoSize; idx++) {
                                add(new Info() {{
                                    save(readChapter(dis, InfoType.class));
                                }});
                            }
                        }}
                ));
            }
        }});
    }

    private void writeListItemSectionToUTF(DataOutputStream dos, SectionType key, ListItemSection section) throws IOException {
        List<Item> content = section.getContent();
        dos.writeInt(content.size());
        dos.writeUTF(java.lang.String.valueOf(key));
        for (Item item : content) {
            var header = item.getHeader();
            writeChapter(dos, header.getAll());
            var info = item.getInfo();
            dos.writeInt(info.size());
            for (Info ieBlock : info) {
                writeChapter(dos, ieBlock.getAll());
            }
        }
    }
}
