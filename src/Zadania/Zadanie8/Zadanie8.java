package Zadania.Zadanie8;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class Zadanie8 {
    public static void main(String[] args) {
        stworzPlik("plik_utf8.txt", "UTF-8");
        stworzPlik("plik_cp1250.txt", "CP1250");
        // Wczytanie i wyświetlenie pliku zakodowanego w UTF-8
        wczytajIPokaz("plik_utf8.txt", "UTF-8");

        // Wczytanie i wyświetlenie pliku zakodowanego w CP-1250/WIN-1250
        wczytajIPokaz("plik_cp1250.txt", "CP1250");
    }

    private static void stworzPlik(String nazwaPliku, String kodowanie) {
        try {
            OutputStreamWriter writer = new OutputStreamWriter(
                    new FileOutputStream("./src/Zadania/Zadanie8/" + nazwaPliku), kodowanie);
            writer.write("Pierwsza linia, ĘŚąćź\n");
            writer.write("Druga linia ĘŚąćź\n");
            writer.write("Trzecia linia ĘŚąćź\n");
            writer.close();
        } catch (Exception e) {
            System.err.println("Błąd podczas tworzenia pliku: " + e.getMessage());
        }

    }

    private static void wczytajIPokaz(String nazwaPliku, String kodowanie) {
        try {
            BufferedReader br = new BufferedReader(
                    new InputStreamReader(new FileInputStream("./src/Zadania/Zadanie8/" + nazwaPliku), kodowanie));
            String linia;
            while ((linia = br.readLine()) != null) {
                System.out.println(linia);
            }
            br.close();
        } catch (Exception e) {
            System.err.println("Błąd podczas wczytywania pliku: " + e.getMessage());
        }
    }
}
