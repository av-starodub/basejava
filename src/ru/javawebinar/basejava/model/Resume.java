package ru.javawebinar.basejava.model;

import ru.javawebinar.basejava.model.chapters.Contacts;
import ru.javawebinar.basejava.model.chapters.Sections;
import ru.javawebinar.basejava.model.enumKeyTypes.ContactType;
import ru.javawebinar.basejava.model.enumKeyTypes.SectionType;
import ru.javawebinar.basejava.model.interfaces.Section;

import java.util.EnumMap;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

/**
 * Initial resume class
 */
public class Resume implements Comparable<Resume> {

    // Unique identifier
    private final String uuid;
    private final String fullName;

    private final Contacts contacts;

    private final Sections sections;

    public Resume(String fullName) {
        this(UUID.randomUUID().toString(), fullName);
    }

    public Resume(String uuid, String fullName) {
        this.uuid = uuid;
        this.fullName = fullName;
        contacts = new Contacts();
        sections = new Sections();
    }

    public String getUuid() {
        return uuid;
    }

    public String getFullName() {
        return fullName;
    }

    public Set<Map.Entry<ContactType, String>> getContacts() {
        return contacts.getAll();
    }

    public Set<Map.Entry<SectionType, Section>> getSections() {
        return sections.getAll();
    }

    public void setContacts(EnumMap<ContactType, String> contacts) {
        this.contacts.addAll(contacts);
    }

    public void setSections(EnumMap<SectionType, Section> sections) {
        this.sections.addAll(sections);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Resume resume = (Resume) o;

        return uuid.equals(resume.uuid) && fullName.equals(resume.fullName);
    }

    @Override
    public int hashCode() {
        return uuid.hashCode();
    }

    @Override
    public String toString() {
        return String.format("%s\n%s\n%s\n%s", fullName, uuid, contacts, sections);
    }

    @Override
    public int compareTo(Resume o) {
        int result = fullName.compareTo(o.fullName);
        return result != 0 ? result : uuid.compareTo(o.uuid);
    }
}
