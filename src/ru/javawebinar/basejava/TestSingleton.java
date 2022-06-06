package ru.javawebinar.basejava;

import ru.javawebinar.basejava.model.enumKeyTypes.SectionType;

/**
 * gkislin
 * 20.07.2016
 */
public class TestSingleton {
    private static TestSingleton instance;

    public static TestSingleton getInstance() {
        if (instance == null) {
            //noinspection InstantiationOfUtilityClass
            instance = new TestSingleton();
        }
        return instance;
    }

    private TestSingleton() {
    }

    public static void main(String[] args) {
        //noinspection ResultOfMethodCallIgnored
        TestSingleton.getInstance().toString();
        Singleton instance = Singleton.valueOf("INSTANCE");
        System.out.println(instance.ordinal());

        for (SectionType type : SectionType.values()) {
            System.out.println(type.getTitle());
        }
    }

    public enum Singleton {
        INSTANCE
    }
}
