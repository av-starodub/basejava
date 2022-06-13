package ru.javawebinar.basejava;

import java.io.File;
import java.util.*;

public class FileSupplier {

    public static void main(String[] args) {
        new FileSupplier().run();
    }

    private void run() {
        File workDirectory = new File("./src/ru/javawebinar/basejava");
        printFileNames(getAllFileNames(workDirectory));
        printSeparator();
        printFileNames(getAllFileNames(workDirectory, new ArrayList<>()));
        printSeparator();
        printStreamAllFileNames(workDirectory);
    }

    private void printSeparator() {
        System.out.println("---------------------------------------");
    }

    private void printFileNames(List<String> names) {
        names.forEach(System.out::println);
    }

    private List<String> getAllFileNames(File dir) {
        List<File> list = new ArrayList<>(List.of(Objects.requireNonNull(dir.listFiles())));
        List<String> fileNames = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            File file = list.get(i);
            if (file.isDirectory()) {
                list.addAll(list.size(), List.of(Objects.requireNonNull(file.listFiles())));
            }
            if (file.isFile()) {
                fileNames.add(file.getName());
            }
        }
        //Collections.sort(fileNames);
        return fileNames;
    }

    private List<String> getAllFileNames(File dir, List<String> accFileNames) {
        for (File file : Objects.requireNonNull(dir.listFiles())) {
            if (file.isDirectory()) {
                getAllFileNames(file, accFileNames);
            }
            if (file.isFile()) {
                accFileNames.add(file.getName());
            }
        }
        return accFileNames;
    }

    private void printStreamAllFileNames(File dir) {
        Arrays.stream(Objects.requireNonNull(dir.listFiles()))
                .forEach(file -> {
                    if (file.isDirectory()) {
                        printStreamAllFileNames(file);
                    }
                    if (file.isFile()) {
                        System.out.println(file.getName());
                    }
                });
    }
}
