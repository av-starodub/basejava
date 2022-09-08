package ru.javawebinar.basejava;

import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;

public class MainStreamsTest {
    private final MainStreams ms;

    public MainStreamsTest() {
        ms = new MainStreams();
    }

    @Test
    public void minValueShouldReturnTheSmallestPossibleNumberOfUniqueDigits() {
        assertEquals(123, ms.minValue(new int[]{1, 2, 3, 3, 2, 3}));
    }

    @Test
    public void oddOrEvenShouldRemoveAllOddNumbers() {
        assertEquals(Arrays.asList(2, 2), ms.oddOrEven(Arrays.asList(2, 2, 1)));;
    }
    @Test
    public void oddOrEvenShouldRemoveAllEvenNumbers() {
        assertEquals(Arrays.asList(1, 1), ms.oddOrEven(Arrays.asList(2, 2, 1, 1)));;
    }

}
