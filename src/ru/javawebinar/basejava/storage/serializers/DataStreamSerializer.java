package ru.javawebinar.basejava.storage.serializers;

import ru.javawebinar.basejava.exception.StorageException;
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
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

public class DataStreamSerializer implements Serializer {

    @Override
    public Resume doRead(InputStream is) throws IOException {
        try (DataInputStream dis = new DataInputStream(is)) {
            Resume resume = new Resume(dis.readUTF(), dis.readUTF());
            int contactsSize = dis.readInt();
            resume.setContacts(new EnumMap<>(ContactType.class) {{
                for (int idx = 0; idx < contactsSize; idx++) {
                    put(ContactType.valueOf(dis.readUTF()), dis.readUTF());
                }
            }});
            resume.setSections(new EnumMap<>(SectionType.class) {{
                put(SectionType.valueOf(dis.readUTF()), new TextSection(dis.readUTF()));
                put(SectionType.valueOf(dis.readUTF()), new TextSection(dis.readUTF()));
                int achievementSize = dis.readInt();

                put(SectionType.valueOf((dis.readUTF())), new ListStringSection(new ArrayList<>() {{
                    for (int idx = 0; idx < achievementSize; idx++) {
                        add(dis.readUTF());
                    }
                }}));
                int qualificationsSize = dis.readInt();
                put(SectionType.valueOf(dis.readUTF()), new ListStringSection(new ArrayList<>() {{
                    for (int idx = 0; idx < qualificationsSize; idx++) {
                        add(dis.readUTF());
                    }
                }}));
                int experienceSize = dis.readInt();
                put(SectionType.valueOf(dis.readUTF()), readListItemSectionFromUTF(dis, experienceSize));
                int educationSize = dis.readInt();
                put(SectionType.valueOf(dis.readUTF()), readListItemSectionFromUTF(dis, educationSize));
            }});
            return resume;
        }
    }

    private ListItemSection readListItemSectionFromUTF(DataInputStream dis, int sectionSize) throws IOException {
        return new ListItemSection(new ArrayList<>() {{
            for (int idx = 0; idx < sectionSize; idx++) {
                add(new Item(
                        new EnumMap<>(HeaderType.class) {{
                            put(HeaderType.valueOf(dis.readUTF()), dis.readUTF());
                            put(HeaderType.valueOf(dis.readUTF()), dis.readUTF());
                        }},
                        new ArrayList<>() {{
                            int eduInfoSize = dis.readInt();
                            for (int idx = 0; idx < eduInfoSize; idx++) {
                                add(new Info() {{
                                    save(new EnumMap<>(InfoType.class) {{
                                        put(InfoType.valueOf(dis.readUTF()), dis.readUTF());
                                        put(InfoType.valueOf(dis.readUTF()), dis.readUTF());
                                        put(InfoType.valueOf(dis.readUTF()), dis.readUTF());
                                        put(InfoType.valueOf(dis.readUTF()), dis.readUTF());
                                    }});
                                }});
                            }
                        }}
                ));
            }

        }});
    }

    @Override
    public void doWrite(Resume resume, OutputStream os) throws IOException {
        try (DataOutputStream dos = new DataOutputStream(os)) {
            dos.writeUTF(resume.getUuid());
            dos.writeUTF(resume.getFullName());
            var contacts = resume.getContacts();
            dos.writeInt(contacts.size());
            for (Map.Entry<ContactType, String> entry : contacts.getAll()) {
                dos.writeUTF(String.valueOf(entry.getKey()));
                dos.writeUTF(entry.getValue());
            }
            var sections = resume.getSections();
            TextSection personal = (TextSection) sections.get(SectionType.PERSONAL);
            String personalContent = personal.getContent();
            dos.writeUTF(String.valueOf(SectionType.PERSONAL));
            dos.writeUTF(personalContent);
            var objective = sections.get(SectionType.OBJECTIVE);
            String objectiveContent = (String) objective.getContent();
            dos.writeUTF(String.valueOf(SectionType.OBJECTIVE));
            dos.writeUTF(objectiveContent);
            var achievement = (ListStringSection) sections.get(SectionType.ACHIEVEMENT);
            List<String> achievementContent = achievement.getContent();
            dos.writeInt(achievementContent.size());
            dos.writeUTF(String.valueOf(SectionType.ACHIEVEMENT));
            for (String content : achievementContent) {
                dos.writeUTF(content);
            }
            ListStringSection qualifications = (ListStringSection) sections.get(SectionType.QUALIFICATIONS);
            List<String> qualificationsContent = qualifications.getContent();
            dos.writeInt(qualificationsContent.size());
            dos.writeUTF(String.valueOf(SectionType.QUALIFICATIONS));
            for (String content : qualificationsContent) {
                dos.writeUTF(content);
            }
            ListItemSection experience = (ListItemSection) sections.get(SectionType.EXPERIENCE);
            List<Item> expContent = experience.getContent();
            dos.writeInt(expContent.size());
            dos.writeUTF(String.valueOf(SectionType.EXPERIENCE));
            writeItemToUTF(dos, expContent);

            ListItemSection education = (ListItemSection) sections.get(SectionType.EDUCATION);
            List<Item> eduContent = education.getContent();
            dos.writeInt(eduContent.size());
            dos.writeUTF(String.valueOf(SectionType.EDUCATION));
            writeItemToUTF(dos, eduContent);

        }
    }

    private void writeItemToUTF(DataOutputStream dos, List<Item> eduContent) throws IOException {
        for (Item edu : eduContent) {
            var header = edu.getHeader();
            dos.writeUTF(String.valueOf(HeaderType.TITLE));
            dos.writeUTF(header.get(HeaderType.TITLE));
            dos.writeUTF(String.valueOf(HeaderType.LINK));
            dos.writeUTF(header.get(HeaderType.LINK));
            var eduInfo = edu.getInfo();
            dos.writeInt(eduInfo.size());
            for (Info ieBlock : eduInfo) {
                dos.writeUTF(String.valueOf(InfoType.START));
                dos.writeUTF(ieBlock.get(InfoType.START));
                dos.writeUTF(String.valueOf(InfoType.END));
                dos.writeUTF(ieBlock.get(InfoType.END));
                dos.writeUTF(String.valueOf(InfoType.HEADER));
                dos.writeUTF(ieBlock.get(InfoType.HEADER));
                dos.writeUTF(String.valueOf(InfoType.DESCRIPTION));
                dos.writeUTF(ieBlock.get(InfoType.DESCRIPTION));
            }
        }
    }

    private byte[] serialize(Object obj) throws IOException {
        try (ByteArrayOutputStream bis = new ByteArrayOutputStream()) {
            try (ObjectOutputStream ois = new ObjectOutputStream(bis)) {
                ois.writeObject(obj);
                return bis.toByteArray();
            }
        }
    }

    private Object deserialize(byte[] bytes) throws IOException {
        try (ByteArrayInputStream bis = new ByteArrayInputStream(bytes)) {
            try (ObjectInputStream ois = new ObjectInputStream(bis)) {
                return ois.readObject();
            } catch (ClassNotFoundException e) {
                throw new StorageException("Resume read error", e);
            }
        }
    }
}
