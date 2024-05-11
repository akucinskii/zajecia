package Zadania.Zadanie10;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

public class ChatServer {
    public static void main(String[] args) {
        try {
            ServerSocketChannel serverChannel = ServerSocketChannel.open();
            serverChannel.bind(new InetSocketAddress(8888));
            serverChannel.configureBlocking(false);

            // Selektor służący do monitorowania wielu kanałów wejscia/wyjscia. Pozwala na
            // sprawdzanie, które kanały są gotowe do odczytu/zapisu
            Selector selector = Selector.open();

            // Przypisanie selektora do kanału serwera
            // Zarejestrowanie kanału serwera do akceptowania połączeń przy użyciu selektora
            serverChannel.register(selector, SelectionKey.OP_ACCEPT);

            System.out.println("Serwer czatu uruchomiony na porcie 8888.");

            while (true) {
                int readyChannels = selector.select(); // Blokujące oczekiwanie na gotowość do obsługi zdarzeń na
                                                       // kanałach
                if (readyChannels == 0)
                    continue;

                Set<SelectionKey> selectedKeys = selector.selectedKeys(); // Pobranie zbioru kluczy z selektora
                Iterator<SelectionKey> keyIterator = selectedKeys.iterator(); // Iterator po zbiorze kluczy

                while (keyIterator.hasNext()) {
                    SelectionKey key = keyIterator.next(); // Pobranie kolejnego klucza

                    if (key.isAcceptable()) { // Sprawdzenie czy kanał jest gotowy do akceptacji nowego połączenia
                        // Akceptuj nowego klienta i zarejestruj go z selektorem
                        acceptClient(selector, serverChannel);
                    }

                    if (key.isReadable()) { // Sprawdzenie czy kanał jest gotowy do odczytu
                        try {
                            // Obsługa wejścia od klienta
                            handleClientInput(key, selector);
                        } catch (IOException e) {
                            // Obsługa wyjątku w przypadku błędu odczytu
                            System.out.println("Błąd odczytu od klienta: " + e.getMessage());
                            key.channel().close();
                            key.cancel();
                        }
                    }

                    keyIterator.remove(); // Usunięcie klucza z zestawu wybranych kluczy
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Metoda akceptująca nowego klienta i rejestrująca go z selektorem
    private static void acceptClient(Selector selector, ServerSocketChannel serverChannel) throws IOException {
        SocketChannel clientChannel = serverChannel.accept(); // Akceptowanie połączenia od klienta
        clientChannel.configureBlocking(false); // Ustawienie kanału klienta na nieblokujący
        clientChannel.register(selector, SelectionKey.OP_READ); // Rejestracja kanału klienta z selektorem do odczytu

        System.out.println("Nowy klient dołączył: " + clientChannel.getRemoteAddress());
    }

    // Metoda obsługująca wejście od klienta
    private static void handleClientInput(SelectionKey key, Selector selector) throws IOException {
        SocketChannel clientChannel = (SocketChannel) key.channel(); // Pobranie kanału klienta z klucza
        ByteBuffer buffer = ByteBuffer.allocate(1024); // Utworzenie bufora do odczytu danych
        int bytesRead = clientChannel.read(buffer); // Odczytanie danych z kanału do bufora

        if (bytesRead == -1) {
            // Klient zamknął połączenie
            System.out.println("Klient opuścił czat: " + clientChannel.getRemoteAddress());
            clientChannel.close(); // Zamknięcie kanału klienta
            key.cancel(); // Anulowanie klucza
        } else {
            // Odczytaj wiadomość i przekaż ją do innych klientów
            buffer.flip(); // Przygotowanie bufora do odczytu danych
            byte[] messageBytes = new byte[buffer.remaining()]; // Utworzenie tablicy na odczytane dane
            buffer.get(messageBytes); // Odczytanie danych z bufora do tablicy bajtów
            String message = new String(messageBytes); // Konwersja odczytanych danych na tekst

            System.out.println("Klient (" + clientChannel.getRemoteAddress() + "): " + message);

            // Przekaż wiadomość do innych klientów
            broadcastMessage(selector, clientChannel, message);
        }
    }

    // Metoda przesyłająca wiadomość do wszystkich klientów, z wyjątkiem nadawcy
    private static void broadcastMessage(Selector selector, SocketChannel sender, String message) throws IOException {
        Set<SelectionKey> keys = selector.keys(); // Pobranie zbioru kluczy z selektora
        for (SelectionKey key : keys) {
            if (key.isValid() && key.channel() instanceof SocketChannel && key.channel() != sender) {
                // Sprawdzenie czy klucz jest ważny, czy kanał jest instancją SocketChannel oraz
                // czy kanał nie jest nadawcą
                SocketChannel clientChannel = (SocketChannel) key.channel(); // Pobranie kanału klienta z klucza
                ByteBuffer buffer = ByteBuffer.wrap(message.getBytes()); // Umieszczenie wiadomości w buforze
                clientChannel.write(buffer); // Wysłanie wiadomości do klienta
            }
        }
    }
}
