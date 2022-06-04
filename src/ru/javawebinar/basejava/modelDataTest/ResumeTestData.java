package ru.javawebinar.basejava.modelDataTest;

import ru.javawebinar.basejava.model.Resume;
import ru.javawebinar.basejava.model.chapters.Contacts;
import ru.javawebinar.basejava.model.chapters.Sections;
import ru.javawebinar.basejava.model.enumKeyTypes.ContactType;
import ru.javawebinar.basejava.model.enumKeyTypes.HeaderType;
import ru.javawebinar.basejava.model.enumKeyTypes.InfoType;
import ru.javawebinar.basejava.model.enumKeyTypes.SectionType;
import ru.javawebinar.basejava.model.interfaces.Section;
import ru.javawebinar.basejava.model.item.Info;
import ru.javawebinar.basejava.model.item.Item;
import ru.javawebinar.basejava.model.sections.ListItemSection;
import ru.javawebinar.basejava.model.sections.ListStringSection;
import ru.javawebinar.basejava.model.sections.TextSection;
import ru.javawebinar.basejava.modelDataTest.creators.ContactsCreator;
import ru.javawebinar.basejava.modelDataTest.creators.SectionsCreator;

import java.util.*;

import static ru.javawebinar.basejava.model.enumKeyTypes.SectionType.*;
import static ru.javawebinar.basejava.modelDataTest.creators.ResumeCreator.createResume;

public class ResumeTestData {
    public static void main(String[] args) {
        new ResumeTestData().runTests();
        new ResumeTestData().printResume();
    }

    public void printResume() {
        System.out.println(createResume());
    }

    public void runTests() {
        checkContactsAddedCorrectly();
        checkSectionsAddedCorrectly();
        checkContactsExtractedCorrectly();
        checkSectionsExtractedCorrectly();
        checkUnmodifiableSectionContent();
        checkUnmodifiableListSectionContent();
        checkUnmodifiableContacts();
        checkUnmodifiableSections();
        checkItemsExtractedCorrectly();
        checkUnmodifiableChapterOfResumeWithAddAllChaptersMethod();
    }

    private void doTest(Boolean assertion, String message) {
        if (assertion) {
            System.out.printf("TEST: %s - SUCCESS\n", message);
        } else {
            System.out.printf("TEST: %s - FAIL\n", message);
        }
    }

    public void checkContactsAddedCorrectly() {
        doTest(
                Objects.equals(ContactsCreator.create(), createResume().getContacts().getAll()),
                ResumeTestData.class.getDeclaredMethods()[2].getName()
        );
    }

    public void checkSectionsAddedCorrectly() {
        doTest(
                Objects.equals(SectionsCreator.create(), createResume().getSections().getAll()),
                ResumeTestData.class.getDeclaredMethods()[3].getName()
        );
    }

    public void checkContactsExtractedCorrectly() {
        Resume resume = createResume();
        EnumMap<ContactType, String> extracted = new EnumMap<>(ContactType.class) {{
            resume.getContacts().getAll().forEach(contact -> this.put(contact.getKey(), contact.getValue()));
        }};
        doTest(
                Objects.equals(extracted.entrySet(), resume.getContacts().getAll()),
                ResumeTestData.class.getDeclaredMethods()[4].getName()
        );
    }

    public void checkSectionsExtractedCorrectly() {
        Resume resume = createResume();
        EnumMap<SectionType, Section> extracted = new EnumMap<>(SectionType.class) {{
            resume.getSections().getAll().forEach(section -> this.put(section.getKey(), section.getValue()));
        }};
        doTest(
                Objects.equals(extracted.entrySet(), resume.getSections().getAll()),
                ResumeTestData.class.getDeclaredMethods()[5].getName()
        );
    }

    public void checkUnmodifiableSectionContent() {
        Set<Map.Entry<SectionType, Section>> sections = createResume().getSections().getAll();
        try {
            sections.add(new AbstractMap.SimpleEntry<>(PERSONAL, new TextSection("NEW")));
        } catch (UnsupportedOperationException e) {
            doTest(
                    Objects.equals(createResume().getSections().getAll(), sections),
                    ResumeTestData.class.getDeclaredMethods()[6].getName() + " " + e
            );
            return;
        }
        doTest(false, ResumeTestData.class.getDeclaredMethods()[6].getName());
    }

    public void checkUnmodifiableListSectionContent() {
        ListStringSection achievement = (ListStringSection) createResume().getSections().get(ACHIEVEMENT);
        try {
            achievement.getContent().add("CHANGE!");
        } catch (UnsupportedOperationException e) {
            doTest(
                    Objects.equals(createResume().getSections().get(ACHIEVEMENT), achievement),
                    ResumeTestData.class.getDeclaredMethods()[7].getName() + " " + e
            );
            return;
        }
        doTest(false, ResumeTestData.class.getDeclaredMethods()[7].getName());
    }

    public void checkUnmodifiableContacts() {
        Resume resume = createResume();
        Contacts before = resume.getContacts();
        for (Map.Entry<ContactType, String> contact : before.getAll()) {
            try {
                contact.setValue("NEW");
            } catch (UnsupportedOperationException e) {
                Contacts after = resume.getContacts();
                doTest(
                        Objects.equals(before, after),
                        ResumeTestData.class.getDeclaredMethods()[8].getName()
                );
                return;
            }
            doTest(false, ResumeTestData.class.getDeclaredMethods()[8].getName());
        }
    }

    public void checkUnmodifiableSections() {
        Resume resume = createResume();
        Sections before = resume.getSections();
        for (Map.Entry<SectionType, Section> section : before.getAll()) {
            try {
                section.setValue(new TextSection("NEW"));
            } catch (UnsupportedOperationException e) {
                Sections after = resume.getSections();
                doTest(
                        Objects.equals(before, after),
                        ResumeTestData.class.getDeclaredMethods()[9].getName()
                );
                return;
            }
            doTest(false, ResumeTestData.class.getDeclaredMethods()[9].getName());
        }
    }

    public void checkItemsExtractedCorrectly() {
        List<Item> sours = ((ListItemSection) createResume().getSections().get(EDUCATION)).getContent();
        List<Item> extracted = new ArrayList<>();

        sours.forEach(item -> {
            EnumMap<HeaderType, String> header = new EnumMap<>(HeaderType.class);
            item.getHeader().getAll().forEach(entry -> header.put(entry.getKey(), entry.getValue()));

            List<Info> list = new ArrayList<>();
            EnumMap<InfoType, String> info = new EnumMap<>(InfoType.class);
            item.getInfo().forEach(blockInfo -> {
                blockInfo.getAll().forEach(entry -> info.put(entry.getKey(), entry.getValue()));
                list.add(new Info() {{
                    save(info);
                }});
            });
            extracted.add(new Item(header, list));
        });
        doTest(
                Objects.equals(sours, extracted), ResumeTestData.class.getDeclaredMethods()[10].getName()
        );
    }

    public void checkUnmodifiableChapterOfResumeWithAddAllChaptersMethod() {
        Resume resume = createResume();
        Sections before = resume.getSections();
        try {
            resume.getSections().save(new EnumMap<>(SectionType.class));
        } catch (UnsupportedOperationException e) {
            Sections after = resume.getSections();
            doTest(
                    Objects.equals(before, after),
                    ResumeTestData.class.getDeclaredMethods()[11].getName()
            );
            return;
        }
        doTest(false, ResumeTestData.class.getDeclaredMethods()[11].getName());
    }
}
