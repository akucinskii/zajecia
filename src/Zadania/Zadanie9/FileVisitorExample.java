package Zadania.Zadanie9;

import java.io.IOException;
import java.nio.file.FileVisitOption;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.EnumSet;

public class FileVisitorExample {

    public static void main(String[] args) {
        Path rootDir = Path.of("D:\\WSB\\zmien katalog");

        try {
            Files.walkFileTree(rootDir, EnumSet.noneOf(FileVisitOption.class), Integer.MAX_VALUE,
                    new SimpleFileVisitor());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
