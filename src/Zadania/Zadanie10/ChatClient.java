package Zadania.Zadanie10;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Set;

public class ChatClient {
    public static void main(String[] args) {
        try {
            BufferedReader console = new BufferedReader(new InputStreamReader(System.in));

            // Stworzenie kanału klienta, kanał to otwarte połączenie
            // pomiędzy procesami lub urządzeniami
            SocketChannel clientChannel = SocketChannel.open();
            // Połączenie z kanałem serwera na porcie 8888
            clientChannel.connect(new InetSocketAddress("localhost", 8888));
            clientChannel.configureBlocking(false);

            // Utworzenie selektora do monitorowania zdarzeń związanych z kanałem klienta
            // Selektor służy do monitorowania wielu kanałów wejscia/wyjscia. Pozwala na
            // sprawdzanie, które kanały są gotowe do odczytu/zapisu
            Selector selector = Selector.open();
            // Przypisanie selektora do kanału klienta
            clientChannel.register(selector, SelectionKey.OP_READ);

            // Wątek obsługujący wprowadzanie danych z konsoli i wysyłanie ich do serwera
            Thread inputThread = new Thread(() -> {
                try {
                    while (true) {
                        String message = console.readLine();
                        // To czyści ostatnią linię z konsoli i nadpisuje ją nową linią z dodatkiem "ja:
                        System.out.print("\033[1A\033[2K");
                        System.out.println("ja: " + message);

                        // Dorzucamy do wiadomosci adres klienta, który wysyła wiadomość aby inne kanaly
                        // wiedzialy kto wyslal wiadomosc
                        String messageToServer = clientChannel.getRemoteAddress() + ": " + message;
                        // buffer to sekwencja bajtów, która może być odczytana lub zapisana
                        ByteBuffer buffer = ByteBuffer.wrap(messageToServer.getBytes());

                        clientChannel.write(buffer);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            inputThread.start();

            // Pętla główna programu monitorująca zdarzenia związane z kanałami
            while (true) {
                int readyChannels = selector.select();
                if (readyChannels == 0)
                    continue;

                Set<SelectionKey> selectedKeys = selector.selectedKeys();
                java.util.Iterator<SelectionKey> keyIterator = selectedKeys.iterator();

                while (keyIterator.hasNext()) {
                    SelectionKey key = keyIterator.next(); // Pobranie kolejnego klucza

                    if (key.isReadable()) { // Sprawdzenie czy kanał jest gotowy do odczytu
                        handleServerMessage(key); // Obsługa otrzymanej wiadomości od serwera
                    }

                    keyIterator.remove(); // Usunięcie klucza z zestawu wybranych kluczy
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void handleServerMessage(SelectionKey key) throws IOException {
        SocketChannel clientChannel = (SocketChannel) key.channel(); // Pobranie kanału z klucza
        ByteBuffer buffer = ByteBuffer.allocate(1024); // Utworzenie bufora do odczytu danych
        int bytesRead = clientChannel.read(buffer); // Odczytanie danych z kanału do bufora

        if (bytesRead == -1) {
            // Serwer zamknął połączenie
            System.out.println("Serwer zakończył czat.");
            clientChannel.close(); // Zamknięcie kanału klienta
            key.cancel(); // Anulowanie klucza
            System.exit(0); // Wyjście z programu
        } else {
            buffer.flip(); // Przygotowanie bufora do odczytu danych
            byte[] messageBytes = new byte[buffer.remaining()]; // Utworzenie tablicy na odczytane dane
            buffer.get(messageBytes); // Odczytanie danych z bufora do tablicy bajtów
            String message = new String(messageBytes); // Konwersja odczytanych danych na tekst
            System.out.println(message); // Wyświetlenie otrzymanej wiadomości
        }
    }
}