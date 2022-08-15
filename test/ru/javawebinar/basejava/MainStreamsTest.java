package ru.javawebinar.basejava;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class MainStreamsTest {
    private final MainStreams ms;

    public MainStreamsTest() {
        ms = new MainStreams();
    }

    @Test
    public void shouldReturnTheSmallestPossibleNumberOfUniqueDigits() {
        assertEquals(123, ms.minValue(new int[]{1,2,3,3,2,3}));
    }
}
