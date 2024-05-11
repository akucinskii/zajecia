package Zadania.Zadanie6;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Zadanie6 {

    public static void main(String[] args) {
        Dodawanie();
        Fibonacci();

    }

    public static void Fibonacci() {
        List<Integer> numbers = generateNumbers(30); // Generowanie listy liczb

        long czasPoczatkowy = System.nanoTime();
        OperacjaStream(numbers);
        long CzasKoncowy = System.nanoTime();
        long czasStream = CzasKoncowy - czasPoczatkowy;

        System.out.println("Stream czas: " + czasStream + " ns");

        czasPoczatkowy = System.nanoTime();
        OperacjaParallelStream(numbers);
        CzasKoncowy = System.nanoTime();
        long czasParallelStream = CzasKoncowy - czasPoczatkowy;

        System.out.println("Parallel czas: " + czasParallelStream + " ns");

        System.out.println("Porownanie Fibonacci: " + (double)czasStream / czasParallelStream);


    }

    public static void Dodawanie() {
        List<Integer> numbers = generateNumbers(10000); 

        long czasPoczatkowy = System.nanoTime();
        sequentialSum(numbers);
        long czasKoncowy = System.nanoTime();
        long czasStream = czasKoncowy - czasPoczatkowy;
        System.out.println("Stream czas: " + czasStream + " ns");

        czasPoczatkowy = System.nanoTime();
        parallelSum(numbers);
        czasKoncowy = System.nanoTime();
        long czasParallelStream = czasKoncowy - czasPoczatkowy;
        System.out.println("ParallelStream czas: " + czasParallelStream + " ns");

        System.out.println("Porownanie dodawania: " + (double)czasStream / czasParallelStream);
    }


    // Funkcja obliczająca sumę sekwencyjnie
    private static int sequentialSum(List<Integer> numbers) {
        return numbers.stream()
                .reduce(0, Integer::sum);
    }

    // Funkcja obliczająca sumę równolegle
    private static int parallelSum(List<Integer> numbers) {
        return numbers.parallelStream()
                .reduce(0, Integer::sum);
    }

    // Funkcja generująca listę liczb całkowitych
    private static List<Integer> generateNumbers(int n) {
        return IntStream.range(0, n)
                .boxed()
                .collect(Collectors.toList());
    }

    // Funkcja symulująca złożoną operację obliczeniową na liście liczb, wykorzystującą sekwencyjne przetwarzanie
    private static long OperacjaStream(List<Integer> numbers) {
        return numbers.stream()
                .mapToLong(Zadanie6::complexOperation)
                .sum();
    }

    // Funkcja symulująca złożoną operację obliczeniową na liście liczb, wykorzystującą równoległe przetwarzanie
    private static long OperacjaParallelStream(List<Integer> numbers) {
        return numbers.parallelStream()
                .mapToLong(Zadanie6::complexOperation)
                .sum();
    }

    // Złożona operacja na liczbie (na potrzeby przykładu)
    private static long complexOperation(int num) {
        // Symulacja złożonej operacji obliczeniowej
        return fibonacci(num);
    }

    // Implementacja rekurencyjnej funkcji Fibonacciego
    private static long fibonacci(int n) {
        if (n <= 1) return n;
        else return fibonacci(n - 1) + fibonacci(n - 2);
    }
}