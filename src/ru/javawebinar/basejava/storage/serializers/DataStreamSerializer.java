package ru.javawebinar.basejava.storage.serializers;

import ru.javawebinar.basejava.exception.StorageException;
import ru.javawebinar.basejava.model.Resume;
import ru.javawebinar.basejava.model.chapters.Contacts;
import ru.javawebinar.basejava.model.chapters.Sections;
import ru.javawebinar.basejava.model.enumKeyTypes.ContactType;
import ru.javawebinar.basejava.model.enumKeyTypes.SectionType;
import ru.javawebinar.basejava.model.interfaces.Section;

import java.io.*;
import java.util.EnumMap;
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
            int sectionSize = dis.readInt();
            resume.setSections(new EnumMap<>(SectionType.class) {{
                for (int idx = 0; idx < sectionSize; idx++) {
                    SectionType key = SectionType.valueOf(dis.readUTF());
                    int length = dis.readInt();
                    byte[] bytes = dis.readNBytes(length);
                    put(key, (Section) deserialize(bytes));
                }
            }});
            return resume;
        }
    }

    @Override
    public void doWrite(Resume resume, OutputStream os) throws IOException {
        try (DataOutputStream dos = new DataOutputStream(os)) {
            dos.writeUTF(resume.getUuid());
            dos.writeUTF(resume.getFullName());
            Contacts contacts = resume.getContacts();
            dos.writeInt(contacts.size());
            for (Map.Entry<ContactType, String> entry : contacts.getAll()) {
                dos.writeUTF(String.valueOf(entry.getKey()));
                dos.writeUTF(entry.getValue());
            }
            Sections sections = resume.getSections();
            dos.writeInt(sections.size());
            for (Map.Entry<SectionType, Section> entry : sections.getAll()) {
                dos.writeUTF(String.valueOf(entry.getKey()));
                byte[] bytes = serialize(entry.getValue());
                dos.writeInt(bytes.length);
                dos.write(bytes);
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
