package ru.javawebinar.basejava.modelDataTest.testData;

import ru.javawebinar.basejava.model.sections.TextSection;

public class Personal {

    public static TextSection createSection() {
        return new TextSection(
                "Аналитический склад ума, сильная логика, креативность, инициативность. Пурист кода и архитектуры."
        );
    }
}
