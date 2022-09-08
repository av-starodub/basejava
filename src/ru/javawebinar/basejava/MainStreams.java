package ru.javawebinar.basejava;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class MainStreams {
    /**
     * Task: implement method via stream.
     *
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

    /**
     * Task: implement method via stream, the complexity of the algorithm should be O(N).
     *
     * @param integers array of numbers.
     * @return if the sum of all numbers is odd - list of even, else - list of odd.
     */
    public List<Integer> oddOrEven(List<Integer> integers) {
        Map<Integer, List<Integer>>
                groupedByParity = integers
                .stream()
                .collect(Collectors.groupingBy(integer -> integer % 2));

        return groupedByParity.get(1).size() % 2 != 0 ? groupedByParity.get(0) : groupedByParity.get(1);
    }
}
