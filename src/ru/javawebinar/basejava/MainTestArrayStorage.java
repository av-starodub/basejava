package ru.javawebinar.basejava;

import ru.javawebinar.basejava.model.Resume;
import ru.javawebinar.basejava.storage.ArrayStorage;
import ru.javawebinar.basejava.storage.SortedArrayStorage;
import ru.javawebinar.basejava.storage.Storage;

import java.util.Arrays;

/**
 * Test for ru.javawebinar.basejava.storage.ArrayStorage
 * ru.javawebinar.basejava.storage.SortedArrayStorage
 */
public class MainTestArrayStorage {
    private final Storage ARRAY_STORAGE;
    private final Resume r1;
    private final Resume r2;

    public MainTestArrayStorage(Storage storage) {
        ARRAY_STORAGE = storage;
        r1 = new Resume("uuid1");
        r2 = new Resume("uuid2");
    }

    private void printTestResult(String testDescription, boolean testResult) {
        System.out.printf("%s: %s\n", testResult ? "SUCCESS" : "FAIL", testDescription);
    }

    private void printErrorTest(String expectedMessage) {
        System.out.printf("Expected: %s \n", expectedMessage);
        System.out.print("           Actual: ");
    }

    private void printTestNumber(int number) {
        System.out.printf("\nTest %d : ", number);
    }

    private void printSeparator() {
        System.out.println("\n------------------------------------\n");
    }

    private void afterEach() {
        ARRAY_STORAGE.clear();
    }

    private void runTests(String className) {
        System.out.printf("\nSTART %s tests", className);
        printSeparator();

        sizeTest();
        saveTest();
        getTest();
        deleteTest();
        getAllTest();
        updateTest();

        System.out.printf("FINISH %s tests\n", className);
    }

    public static void main(String[] args) {
        new MainTestArrayStorage(new ArrayStorage()).runTests("ArrayStorage");
        new MainTestArrayStorage(new SortedArrayStorage()).runTests("SortedArrayStorage");
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
        ARRAY_STORAGE.save(null);
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
        String expectedMessage = String.format(
                "ERROR: %s already exists in storage", r1.getUuid()
        );
        printErrorTest(expectedMessage);
        ARRAY_STORAGE.save(r1);
        afterEach();
    }

    private void saveStorageOverflowErrorMessageTest() {
        String expectedMessage = "ERROR: 10000 not added. The storage is full";
        printErrorTest(expectedMessage);
        for (int i = 0; i <= 10000; i++) {
            Resume r = new Resume(String.format("%d", i));
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
        String expectedMessage = String.format(
                "ERROR: %s no such in storage", r1.getUuid()
        );
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

        printTestNumber(2);
        deleteErrorMessageTest();

        printSeparator();
    }

    private void deleteExistingResumeTest() {
        String testDescription = "Removes resume from ARRAY_STORAGE";
        ARRAY_STORAGE.save(r1);
        ARRAY_STORAGE.save(r2);
        ARRAY_STORAGE.delete(r1.getUuid());
        boolean testResult = ARRAY_STORAGE.size() == 1;
        printTestResult(testDescription, testResult);
        System.out.print("get(r1): ");
        String expectedMessage = String.format(
                "ERROR: %s no such in storage", r1.getUuid()
        );
        printErrorTest(expectedMessage);
        ARRAY_STORAGE.get(r1.getUuid());
        afterEach();
    }

    private void deleteErrorMessageTest() {
        String expectedMessage = String.format(
                "ERROR: %s no such in storage", r1.getUuid()
        );
        printErrorTest(expectedMessage);
        ARRAY_STORAGE.delete(r1.getUuid());
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
        Resume[] actualArray = ARRAY_STORAGE.getAllSorted().toArray(new Resume[ARRAY_STORAGE.size()]);
        Resume[] expectedArray = {r1, r2};
        boolean isAll = actualArray.length == ARRAY_STORAGE.size();
        boolean testResult = Arrays.deepEquals(expectedArray, actualArray) && isAll;
        printTestResult(testDescription, testResult);
        afterEach();
    }

    private void getAllReturnsEmptyArrayTest() {
        String testDescription = "Returns empty array for empty ARRAY_STORAGE";
        Resume[] actualArray = ARRAY_STORAGE.getAllSorted().toArray(new Resume[ARRAY_STORAGE.size()]);
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

        printSeparator();
    }

    private void updateExistingResumeTest() {
        String testDescription = "Updates existing resume in ARRAY_STORAGE";
        ARRAY_STORAGE.save(r1);
        Resume updated = new Resume("uuid1");
        ARRAY_STORAGE.update(updated);
        boolean testResult = ARRAY_STORAGE.get("uuid1").equals(updated);
        printTestResult(testDescription, testResult);
        afterEach();
    }

    private void updateErrorMessageTest() {
        String expectedMessage = String.format(
                "ERROR: %s no such in storage", r1.getUuid()
        );
        printErrorTest(expectedMessage);
        ARRAY_STORAGE.update(r1);
    }

}
