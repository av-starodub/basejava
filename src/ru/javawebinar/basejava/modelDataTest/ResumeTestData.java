package ru.javawebinar.basejava.modelDataTest;

import ru.javawebinar.basejava.model.Resume;
import ru.javawebinar.basejava.model.enumKeyTypes.ContactType;
import ru.javawebinar.basejava.model.enumKeyTypes.SectionType;
import ru.javawebinar.basejava.model.interfaces.Section;
import ru.javawebinar.basejava.model.sections.ListStringSection;
import ru.javawebinar.basejava.model.sections.TextSection;
import ru.javawebinar.basejava.modelDataTest.creators.ContactsCreator;
import ru.javawebinar.basejava.modelDataTest.creators.SectionsCreator;

import java.util.*;

import static ru.javawebinar.basejava.model.enumKeyTypes.SectionType.*;
import static ru.javawebinar.basejava.modelDataTest.creators.ResumeCreator.createResume;

public class ResumeTestData {
    public static void main(String[] args) {
        new ResumeTestData().run();
    }

    public void run() {
        checkContactsAddedCorrectly();
        checkSectionsAddedCorrectly();
        checkContactsExtractedCorrectly();
        checkSectionsExtractedCorrectly();
        checkThrowWhenTryToModifySectionsContent();
        checkThrowWhenTryToModifyListSectionContent();
        checkUnmodifiableContacts();
        checkUnmodifiableSections();
    }

    private void doTest(Boolean assertion, String message) {
        if (assertion) {
            System.out.printf("TEST: %s - SUCCESS\n", message);
        } else {
            System.out.printf("TEST: %s - FAIL\n", message);
        }
    }

    public void checkContactsAddedCorrectly() {
        doTest(Objects.equals(
                ContactsCreator.create(), createResume().getContacts()), ResumeTestData.class.getDeclaredMethods()[0].getName()
        );
    }

    public void checkSectionsAddedCorrectly() {
        doTest(Objects.equals(
                SectionsCreator.create(), createResume().getSections()), ResumeTestData.class.getDeclaredMethods()[1].getName()
        );
    }

    public void checkContactsExtractedCorrectly() {
        Resume resume = createResume();
        EnumMap<ContactType, String> extracted = new EnumMap<>(ContactType.class) {{
            resume.getContacts().forEach(contact -> this.put(contact.getKey(), contact.getValue()));
        }};
        doTest(Objects.equals(
                extracted.entrySet(), resume.getContacts()), ResumeTestData.class.getDeclaredMethods()[2].getName());
    }

    public void checkSectionsExtractedCorrectly() {
        Resume resume = createResume();
        EnumMap<SectionType, Section> extracted = new EnumMap<>(SectionType.class) {{
            resume.getSections().forEach(section -> this.put(section.getKey(), section.getValue()));
        }};
        doTest(Objects.equals(
                extracted.entrySet(), resume.getSections()), ResumeTestData.class.getDeclaredMethods()[3].getName());
    }

    public void checkThrowWhenTryToModifySectionsContent() {
        Set<Map.Entry<SectionType, Section>> sections = createResume().getSections();
        try {
            sections.add(new AbstractMap.SimpleEntry<>(PERSONAL, new TextSection()));
            sections.clear();
        } catch (UnsupportedOperationException e) {
            doTest(Objects.equals(createResume().getSections(), sections), ResumeTestData.class.getDeclaredMethods()[4].getName() + " " + e);
            return;
        }
        doTest(false, ResumeTestData.class.getDeclaredMethods()[4].getName());
    }

    public void checkThrowWhenTryToModifyListSectionContent() {
        Set<Map.Entry<SectionType, Section>> sections = createResume().getSections();
        for (Map.Entry<SectionType, Section> section : sections) {
            if (section.getKey().equals(ACHIEVEMENT)) {
                ListStringSection achievement = (ListStringSection) section.getValue();
                List<String> content = achievement.getContent();
                try {
                    content.add("CHANGE!");
                } catch (UnsupportedOperationException e) {
                    doTest(Objects.equals(
                            createResume().getSections(), sections), ResumeTestData.class.getDeclaredMethods()[5].getName() + " " + e
                    );
                    return;
                }
                doTest(false, ResumeTestData.class.getDeclaredMethods()[5].getName());
            }
        }
    }

    public void checkUnmodifiableContacts() {
        Resume resume = createResume();
        for (Map.Entry<ContactType, String> contact : resume.getContacts()) {
            contact.setValue("NEW");
        }
        Set<Map.Entry<ContactType, String>> after = resume.getContacts();
        doTest(Objects.equals(createResume().getContacts(), after), ResumeTestData.class.getDeclaredMethods()[6].getName());
    }

    public void checkUnmodifiableSections() {
        Resume resume = createResume();
        for (Map.Entry<SectionType, Section> section : resume.getSections()) {
            section.setValue(new TextSection());
        }
        Set<Map.Entry<SectionType, Section>> after = resume.getSections();
        doTest(Objects.equals(createResume().getSections(), after), ResumeTestData.class.getDeclaredMethods()[7].getName());
    }
}
