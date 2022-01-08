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

    private void printErrorTest(String expectedMessage) {
        System.out.printf("Expected method message: %s \n", expectedMessage);
        System.out.print("          Actual method message: ");
    }

    private void printTestNumber(int number) {
        System.out.printf("Test %d: ", number);
    }

    private void printSeparator() {
        System.out.println("------------------------------------");
        System.out.println();
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
        test.updateTest();
    }

    private void sizeTest() {
        System.out.println("SIZE TEST");

        printTestNumber(1);
        String sizeTestDescription = "Returns size field value";
        boolean sizeTestResult = ARRAY_STORAGE.size() == 0;
        printTestResult(sizeTestDescription, sizeTestResult);

        printSeparator();
    }

    private void saveTest() {
        System.out.println("SAVE TEST");

        printTestNumber(1);
        String firstTestDescription = "Doesn't add null to ARRAY_STORAGE";
        Resume resume = new Resume();
        ARRAY_STORAGE.save(resume);
        boolean firstTestResult = ARRAY_STORAGE.size() == 0;
        printTestResult(firstTestDescription, firstTestResult);

        printTestNumber(2);
        String secondTestDescription = "Adds resume to ARRAY_STORAGE";
        ARRAY_STORAGE.save(r1);
        boolean secondTestResult = ARRAY_STORAGE.size() == 1;
        printTestResult(secondTestDescription, secondTestResult);

        printTestNumber(3);
        String thirdTestDescription = "Doesn't add resume existing in ARRAY_STORAGE";
        ARRAY_STORAGE.save(r1);
        boolean thirdTestResult = ARRAY_STORAGE.size() == 1;
        printTestResult(thirdTestDescription, thirdTestResult);

        printSeparator();
        afterEach();
    }

    private void getTest() {
        System.out.println("GET TEST");

        printTestNumber(1);
        String firstTestDescription = "Returns null if resume doesn't exist";
        boolean firstTestResult = ARRAY_STORAGE.get(r1.getUuid()) == null;
        printTestResult(firstTestDescription, firstTestResult);

        printTestNumber(2);
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

        printTestNumber(1);
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

        printTestNumber(1);
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

    private void updateTest() {
        System.out.println("UPDATE TEST");

        printTestNumber(1);
        String firstTestDescription = "Updates resume in ARRAY_STORAGE";
        Resume resume = new Resume();
        resume.setUuid("uuid");
        ARRAY_STORAGE.save(resume);
        Resume updated = new Resume();
        updated.setUuid("uuid");
        ARRAY_STORAGE.update(updated);
        boolean testResult = ARRAY_STORAGE.get("uuid").equals(updated);
        printTestResult(firstTestDescription, testResult);

        printTestNumber(2);
        String expectedMessage = "ERROR: No such resume in storage";
        printErrorTest(expectedMessage);
        ARRAY_STORAGE.update(r1);
        System.out.println();

        printSeparator();
        afterEach();
    }
}
