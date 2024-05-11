package Zadania.Zadanie7;

import java.io.IOException;
import java.nio.channels.FileChannel;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.nio.file.attribute.UserPrincipal;

public class Zadanie7 {

    public static void main(String[] args) {
        // Tworzenie ścieżki do pliku
        Path path = Paths.get("test.txt");

        // Utworzenie pliku przy użyciu klasy Files
        try {
            Files.createFile(path);
            System.out.println("Utworzono plik: " + path);
        } catch (IOException e) {
            System.err.println("Błąd podczas tworzenia pliku: " + e.getMessage());
        }

        // Uzyskanie informacji o pliku przy użyciu klasy Files
        try {
            BasicFileAttributes attributes = Files.readAttributes(path, BasicFileAttributes.class);
            System.out.println("Rozmiar pliku: " + attributes.size());
            System.out.println("Czas ostatniej modyfikacji: " + attributes.lastModifiedTime());
        } catch (IOException e) {
            System.err.println("Błąd podczas odczytu atrybutów pliku: " + e.getMessage());
        }

        // Uzyskanie czasu ostatniej modyfikacji i ustawienie nowego czasu przy użyciu
        // klasy FileTime
        try {
            FileTime newModifiedTime = FileTime.fromMillis(System.currentTimeMillis());
            Files.setLastModifiedTime(path, newModifiedTime);
            System.out.println("Zaktualizowano czas ostatniej modyfikacji pliku: " + newModifiedTime);
        } catch (IOException e) {
            System.err.println("Błąd podczas aktualizacji czasu ostatniej modyfikacji pliku: " + e.getMessage());
        }

        // Uzyskanie właściciela pliku przy użyciu klasy FileOwnerAttributeView
        try {
            UserPrincipal owner = Files.getOwner(path);
            System.out.println("Właściciel pliku: " + owner);
        } catch (IOException e) {
            System.err.println("Błąd podczas uzyskiwania właściciela pliku: " + e.getMessage());
        }

        // Uzyskanie FileChannel dla pliku
        try (FileChannel fileChannel = FileChannel.open(path, StandardOpenOption.READ, StandardOpenOption.WRITE)) {
            // Wykonanie operacji na FileChannel
            System.out.println("FileChannel utworzony dla pliku: " + fileChannel);
        } catch (IOException e) {
            System.err.println("Błąd podczas uzyskiwania FileChannel: " + e.getMessage());
        }

        // Uzyskanie domyślnego FileSystem
        FileSystem defaultFileSystem = FileSystems.getDefault();
        System.out.println("Domyślny system plików: " + defaultFileSystem);

        // Pobranie dostępnych przechowalni w systemie plików
        Iterable<FileStore> fileStores = defaultFileSystem.getFileStores();
        System.out.println("Dostępne przechowalnie:");
        for (FileStore fileStore : fileStores) {
            System.out.println(fileStore);
        }

        // Pobranie korzeni systemu plików
        Iterable<Path> roots = defaultFileSystem.getRootDirectories();
        System.out.println("Korzenie systemu plików:");
        for (Path root : roots) {
            System.out.println(root);
        }
    }
}
