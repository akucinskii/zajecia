package Zadania.Zadanie5;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class Zadanie5 {
    public static void main (String[] args) {

    List<String> words = Arrays.asList("apple", "banana", "blueberry", "cherry", "blackberry");

        // Równoczesne filtrowanie wątków
        Optional<String> firstMatch = words.parallelStream()
                .filter(word -> word.startsWith("b"))
                .findFirst(); // Znajdź pierwsze dopasowanie

        Optional<String> anyMatch = words.parallelStream()
                .filter(word -> word.startsWith("b"))
                .findAny(); // Znajdź dowolne dopasowanie

        System.out.println("findFirst(): " + firstMatch.orElse("Brak dopasowania"));
        System.out.println("findAny(): " + anyMatch.orElse("Brak dopasowania"));
    }

}
