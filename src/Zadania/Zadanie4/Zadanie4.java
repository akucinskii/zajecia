package Zadania.Zadanie4;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Zadanie4 {
   

    public static void main(String[] args) {
        // Tworzenie listy obiektów z zagnieżdżonymi listami
        List<List<? extends Number>> listOfLists = new ArrayList<>();
        listOfLists.add(Arrays.asList(1, 2, 3, 4)); // Lista Integer
        listOfLists.add(Arrays.asList(1.5, 2.5, 3.5, 4.5)); // Lista Double

        // Sumowanie wszystkich liczb używając stream()
        double sum = listOfLists.stream()
                .flatMap(List::stream) // Spłaszczanie listy do pojedynczego strumienia liczb
                .mapToDouble(Number::doubleValue) // Mapowanie na strumień liczb typu double
                .sum(); // Sumowanie

        System.out.println("Suma wszystkich liczb: " + sum);
    }
}


