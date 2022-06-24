package ru.javawebinar.basejava.model.interfaces;

import java.io.Serializable;

public interface Section<T> extends Serializable {
    T getContent();
}
