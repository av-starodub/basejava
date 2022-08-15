package ru.javawebinar.basejava;

import java.util.Arrays;

public class MainStreams {
    /**
     * Task: implement method via stream.
     * @param values array of digits from 1 to 9.
     * @return the smallest possible number made up of unique digits.
     * For example {1,2,3,3,2,3} will return 123.
     */
    public int minValue(int[] values) {
        return Arrays.stream(values)
                .distinct()
                .sorted()
                .reduce(0, (a, b) -> Integer.parseInt(String.valueOf(a) + b));
    }
}
