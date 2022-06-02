package ru.javawebinar.basejava.modelDataTest.testData;

import ru.javawebinar.basejava.model.sections.ListItemSection;

import java.util.ArrayList;

public class Education {

    public static ListItemSection createSection() {
        return new ListItemSection(new ArrayList<>());
    }
}