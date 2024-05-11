package Zadania.Zadanie9;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;

public class WatchServiceExample {
    public static void main(String[] args) {
        // zmien katalog
        Path directoryToWatch = Path
                .of("/Users/arturkucinski/Downloads/projekty_artur_k/java_zajecia_2/zajecia/src/Zadania/Zadanie9");

        try {
            // Utwórz instancję WatchService
            WatchService watchService = FileSystems.getDefault().newWatchService();

            // Zarejestruj katalog do monitorowania zmian
            directoryToWatch.register(watchService, StandardWatchEventKinds.ENTRY_CREATE,
                    StandardWatchEventKinds.ENTRY_DELETE, StandardWatchEventKinds.ENTRY_MODIFY);

            while (true) {
                // Oczekuj na zdarzenia
                WatchKey key = watchService.take();

                for (WatchEvent<?> event : key.pollEvents()) {
                    if (event.kind() == StandardWatchEventKinds.OVERFLOW) {
                        System.out.println("Zdarzenie OVERFLOW.");
                        continue;
                    }

                    // Wyświetl typ zdarzenia i nazwę zmienionego pliku
                    WatchEvent<Path> ev = (WatchEvent<Path>) event;
                    Path fileName = ev.context();
                    System.out.println("Zdarzenie: " + event.kind() + ", Plik: " + fileName);
                }

                // Zresetuj klucz, aby kontynuować monitorowanie
                boolean valid = key.reset();
                if (!valid) {
                    System.out.println("Zakończono monitorowanie katalogu.");
                    break;
                }
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
