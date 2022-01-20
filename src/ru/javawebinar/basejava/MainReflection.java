package ru.javawebinar.basejava;

import ru.javawebinar.basejava.model.Resume;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class MainReflection {

    public static void main(String[] args)
            throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        Resume r = new Resume();
        var clazz = r.getClass();
        Field field = clazz.getDeclaredFields()[0];
        field.setAccessible(true);
        System.out.println(field.getName());
        System.out.println(field.get(r));
        field.set(r, "new_uuid");
        // TODO : invoke r.toString via reflection
        Method toString = clazz.getMethod("toString");
        System.out.println(toString.invoke(r));
        System.out.println(r);
    }
}
