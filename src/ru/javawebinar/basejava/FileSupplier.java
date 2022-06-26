package ru.javawebinar.basejava;

import java.io.File;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.function.Function;

public class FileSupplier {
    private final File workDirectory = new File("./src/ru/javawebinar/basejava");

    public static void main(String[] args) {
        new FileSupplier().run();
    }

    private void run() {
        new FileSupplier().printFileNames("RECURSION", sort(getAllFileNamesRecursion(workDirectory)));
        new FileSupplier().printFileNames("CYCLE", sort(getAllFileNamesInCycle(workDirectory)));
        new FileSupplier().printDirectoryRepresentation(workDirectory, "");
    }

    private void printFileNames(String message, List<String> fileNames) {
        System.out.println(message);
        System.out.println("------------");
        fileNames.forEach(System.out::println);
        System.out.println();
    }

    private List<String> sort(List<String> fileNames) {
        List<String> names = new ArrayList<>(fileNames);
        Collections.sort(names);
        return names;
    }

    private void forEachFile(File dir, Function<File, ?> function) {
        try (DirectoryStream<Path> paths = Files.newDirectoryStream(Path.of(dir.getAbsolutePath()))) {
            paths.forEach(path -> {
                File file = path.toFile();
                if (file.isDirectory()) {
                    forEachFile(file, function);
                }
                if (file.isFile()) {
                    function.apply(file);
                }
            });
        } catch (IOException e) {
            System.out.println("Directory read error");
        }
    }

    private List<String> getAllFileNamesRecursion(File dir) {
        List<String> fileNames = new ArrayList<>();
        forEachFile(dir, file -> fileNames.add(file.getName()));
        return fileNames;
    }

    private List<String> getAllFileNamesInCycle(File dir) {
        List<String> fileNames = new ArrayList<>();
        List<File> files = new ArrayList<>(List.of(Objects.requireNonNull(dir.listFiles())));
        for (int i = 0; i < files.size(); i++) {
            File file = files.get(i);
            if (file.isDirectory()) {
                files.addAll(files.size(), List.of(Objects.requireNonNull(file.listFiles())));
            }
            if (file.isFile()) {
                fileNames.add(file.getName());
            }
        }
        return fileNames;
    }

    private void printDirectoryRepresentation(File dir, String indent) {
        if (dir != null && dir.exists()) {
            System.out.println(dir.isFile() ? indent + "f: " + dir.getName() : indent + "dir: " + dir.getName());
            if (dir.isDirectory()) {
                for (File subDir : Objects.requireNonNull(dir.listFiles())) {
                    printDirectoryRepresentation(subDir, indent + "    ");
                }
            }
        }
    }
}
