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
        System.out.printf("Expected: %s \n", expectedMessage);
        System.out.print("           Actual: ");
    }

    private void printTestNumber(int number) {
        System.out.printf("Test %d : ", number);
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
        test.updateTest();
    }

    private void sizeTest() {
        System.out.println("SIZE TEST");

        printTestNumber(1);
        String testDescription = "Returns size field value";
        boolean testResult = ARRAY_STORAGE.size() == 0;
        printTestResult(testDescription, testResult);

        printSeparator();
    }

    private void saveTest() {
        System.out.println("SAVE TEST");

        printTestNumber(1);
        saveDoesNotAddNullTest();

        printTestNumber(2);
        saveAddResumeTest();

        printTestNumber(3);
        saveResumeExistingErrorMessageTest();

        printTestNumber(4);
        saveStorageOverflowErrorMessageTest();

        printSeparator();
    }

    private void saveDoesNotAddNullTest() {
        String testDescription = "Doesn't add null to ARRAY_STORAGE";
        Resume resume = new Resume();
        ARRAY_STORAGE.save(resume);
        boolean testResult = ARRAY_STORAGE.size() == 0;
        printTestResult(testDescription, testResult);
        afterEach();
    }

    private void saveAddResumeTest() {
        String testDescription = "Adds resume to ARRAY_STORAGE";
        ARRAY_STORAGE.save(r1);
        boolean testResult = ARRAY_STORAGE.size() == 1;
        printTestResult(testDescription, testResult);
        afterEach();
    }

    private void saveResumeExistingErrorMessageTest() {
        ARRAY_STORAGE.save(r1);
        String expectedMessage = "ERROR: This resume already exists in storage";
        printErrorTest(expectedMessage);
        ARRAY_STORAGE.save(r1);
        afterEach();
    }

    private void saveStorageOverflowErrorMessageTest() {
        String expectedMessage = "ERROR: The storage is full";
        printErrorTest(expectedMessage);
        for (int i = 0; i <= ARRAY_STORAGE.size(); i++) {
            Resume r = new Resume();
            r.setUuid(String.format("%d", i));
            ARRAY_STORAGE.save(r);
        }
        afterEach();
    }

    private void getTest() {
        System.out.println("GET TEST");

        printTestNumber(1);
        getErrorMessageTest();

        printTestNumber(2);
        getReturnsExistingResume();

        printSeparator();
    }

    private void getErrorMessageTest() {
        String expectedMessage = "ERROR: No such resume in storage";
        printErrorTest(expectedMessage);
        ARRAY_STORAGE.get(r1.getUuid());
    }

    private void getReturnsExistingResume() {
        String testDescription = "Returns resume if it exist";
        ARRAY_STORAGE.save(r1);
        Resume resumeFromStorage = ARRAY_STORAGE.get(r1.getUuid());
        boolean testResult = resumeFromStorage.getUuid().equals(r1.getUuid());
        printTestResult(testDescription, testResult);
        afterEach();
    }

    private void deleteTest() {
        System.out.println("DELETE TEST");

        printTestNumber(1);
        deleteExistingResumeTest();

        printSeparator();
    }

    private void deleteExistingResumeTest() {
        String testDescription = "Removes resume from ARRAY_STORAGE";
        ARRAY_STORAGE.save(r1);
        ARRAY_STORAGE.save(r2);
        ARRAY_STORAGE.delete(r1.getUuid());
        boolean wasResumeDelete = ARRAY_STORAGE.get(r1.getUuid()) == null;
        boolean wasSizeChange = ARRAY_STORAGE.size() == 1;
        boolean testResult = wasResumeDelete && wasSizeChange;
        printTestResult(testDescription, testResult);
        afterEach();
    }

    private void getAllTest() {
        System.out.println("GETALL TEST");

        printTestNumber(1);
        getAllExistingResumesTest();

        printTestNumber(2);
        getAllReturnsEmptyArrayTest();

        printSeparator();
    }

    private void getAllExistingResumesTest() {
        String testDescription = "Returns new array with all resumes from ARRAY_STORAGE";
        ARRAY_STORAGE.save(r1);
        ARRAY_STORAGE.save(r2);
        Resume[] actualArray = ARRAY_STORAGE.getAll();
        Resume[] expectedArray = {r1, r2};
        boolean isAll = actualArray.length == ARRAY_STORAGE.size();
        boolean testResult = Arrays.deepEquals(expectedArray, actualArray) && isAll;
        printTestResult(testDescription, testResult);
        afterEach();
    }

    private void getAllReturnsEmptyArrayTest() {
        String testDescription = "Returns empty array for empty ARRAY_STORAGE";
        Resume[] actualArray = ARRAY_STORAGE.getAll();
        int expectedArrayLength = 0;
        boolean testResult = actualArray.length == expectedArrayLength;
        printTestResult(testDescription, testResult);
        afterEach();
    }

    private void updateTest() {
        System.out.println("UPDATE TEST");

        printTestNumber(1);
        updateExistingResumeTest();

        printTestNumber(2);
        updateErrorMessageTest();

        printTestNumber(3);
        updateDoNothingWithNull();

        printSeparator();
    }

    private void updateExistingResumeTest() {
        String testDescription = "Updates existing resume in ARRAY_STORAGE";
        ARRAY_STORAGE.save(r1);
        Resume updated = new Resume();
        updated.setUuid("uuid1");
        ARRAY_STORAGE.update(updated);
        boolean testResult = ARRAY_STORAGE.get("uuid1").equals(updated);
        printTestResult(testDescription, testResult);
        afterEach();
    }

    private void updateErrorMessageTest() {
        String expectedMessage = "ERROR: No such resume in storage";
        printErrorTest(expectedMessage);
        ARRAY_STORAGE.update(r1);
    }

    private void updateDoNothingWithNull() {
        String testDescription = "Updates do nothing with null parameter";
        Resume updated = new Resume();
        try {
            ARRAY_STORAGE.update(updated);
            printTestResult(testDescription, true);
        } catch (NullPointerException e) {
            printTestResult(testDescription, false);
        }
    }
}
