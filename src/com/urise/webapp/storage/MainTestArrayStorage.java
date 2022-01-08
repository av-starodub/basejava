package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.Arrays;

/**
 * Test for your com.urise.webapp.storage.ArrayStorage implementation
 */
public class MainTestArrayStorage {
    private final ArrayStorage ARRAY_STORAGE;
    private final Resume r1 = new Resume();
    private final Resume r2 = new Resume();

    public MainTestArrayStorage() {
        ARRAY_STORAGE = new ArrayStorage();
        r1.setUuid("uuid1");
        r2.setUuid("uuid2");
    }

    private void printTestResult(String testDescription, boolean testResult) {
        System.out.printf("%s: %s\n", testResult ? "SUCCESS" : "FAIL", testDescription);
    }

    private void printSeparator() {
        System.out.println("------------------------------------");
    }

    private void afterEach() {
        ARRAY_STORAGE.clear();
    }

    public static void main(String[] args) {
        MainTestArrayStorage test = new MainTestArrayStorage();
        test.sizeTest();
        test.saveTest();
        test.getTest();
        test.deleteTest();
        test.getAllTest();
    }

    private void sizeTest() {
        System.out.println("SIZE TEST");

        String sizeTestDescription = "Returns size field value";
        boolean sizeTestResult = ARRAY_STORAGE.size() == 0;
        printTestResult(sizeTestDescription, sizeTestResult);

        printSeparator();
    }

    private void saveTest() {
        System.out.println("SAVE TEST");

        String firstTestDescription = "Doesn't add null to ARRAY_STORAGE";
        Resume resume = new Resume();
        ARRAY_STORAGE.save(resume);
        boolean firstTestResult = ARRAY_STORAGE.size() == 0;
        printTestResult(firstTestDescription, firstTestResult);

        String secondTestDescription = "Adds resume to ARRAY_STORAGE";
        ARRAY_STORAGE.save(r1);
        boolean secondTestResult = ARRAY_STORAGE.size() == 1;
        printTestResult(secondTestDescription, secondTestResult);

        String thirdTestDescription = "Doesn't add resume existing in ARRAY_STORAGE";
        ARRAY_STORAGE.save(r1);
        boolean thirdTestResult = ARRAY_STORAGE.size() == 1;
        printTestResult(thirdTestDescription, thirdTestResult);

        printSeparator();
        afterEach();
    }

    private void getTest() {
        System.out.println("GET TEST");

        String firstTestDescription = "Returns null if resume doesn't exist";
        boolean firstTestResult = ARRAY_STORAGE.get(r1.getUuid()) == null;
        printTestResult(firstTestDescription, firstTestResult);

        String secondTestDescription = "Returns resume if it exist";
        ARRAY_STORAGE.save(r1);
        Resume resumeFromStorage = ARRAY_STORAGE.get(r1.getUuid());
        boolean secondTestResult = resumeFromStorage.getUuid().equals(r1.getUuid());
        printTestResult(secondTestDescription, secondTestResult);

        printSeparator();
        afterEach();
    }

    private void deleteTest() {
        System.out.println("DELETE TEST");

        String firstTestDescription = "Removes resume from ARRAY_STORAGE";
        ARRAY_STORAGE.save(r1);
        ARRAY_STORAGE.save(r2);
        ARRAY_STORAGE.delete(r1.getUuid());
        boolean wasResumeDelete = ARRAY_STORAGE.get(r1.getUuid()) == null;
        boolean wasSizeChange = ARRAY_STORAGE.size() == 1;
        boolean firstTestResult = wasResumeDelete && wasSizeChange;
        printTestResult(firstTestDescription, firstTestResult);

        printSeparator();
        afterEach();
    }

    private void getAllTest() {
        System.out.println("GETALL TEST");

        String testDescription = "Returns new array with all resumes from ARRAY_STORAGE";
        ARRAY_STORAGE.save(r1);
        ARRAY_STORAGE.save(r2);
        Resume[] actual = ARRAY_STORAGE.getAll();
        Resume[] expected = {r1, r2};
        boolean isAll = actual.length == ARRAY_STORAGE.size();
        boolean testResult = Arrays.deepEquals(expected, actual) && isAll;
        printTestResult(testDescription, testResult);

        printSeparator();
        afterEach();
    }
}
