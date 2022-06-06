package ru.javawebinar.basejava.modelDataTest.testData;

import ru.javawebinar.basejava.model.sections.ListStringSection;

import java.util.ArrayList;

public class Achievement {

    public static ListStringSection createSection() {
        return new ListStringSection(new ArrayList<>() {{
            add("Организация команды и успешная реализация Java проектов для сторонних заказчиков");
            add("С 2013 года: разработка проектов \"Разработка Web приложения\",\"Java Enterprise\"");
        }});
    }
}
