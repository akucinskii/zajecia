package przyklady.Strumienie;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class Strumienie1 {
    public static void main(String[] args) {
	List<Integer> numbers = new ArrayList<>();
        numbers.add(1);
        numbers.add(2);
        numbers.add(3);
        numbers.add(4);
        numbers.add(5);

        // Użycie wyrażenia lambda jako predykatu do sprawdzenia, czy wszystkie liczby są parzyste
        boolean allEven = numbers.stream().allMatch(n -> n % 2 == 0);

        System.out.println("Czy wszystkie liczby są parzyste? " + allEven);

        // Operacje pośrednie

        List<Integer> numbers2 = Arrays.asList(1, 2, 3, 4, 5, 6);


        // Filter
        List<Integer> evenNumbers = numbers2.stream().filter(n -> n % 2 == 0).collect(Collectors.toList());
        System.out.println("evenNumbers: " + evenNumbers);
		// evenNumbers zawiera [2, 4, 6]

        // Map
        List<String> names = Arrays.asList("Alice", "Bob", "Charlie");
        List<Integer> nameLengths = names.stream().map(String::length).collect(Collectors.toList());
        System.out.println("nameLengths: " + nameLengths);
		// nameLengths zawiera [5, 3, 7]


        // flatMap
        List<List<Integer>> lists = Arrays.asList(Arrays.asList(1, 2), Arrays.asList(3, 4));
        List<Integer> flattenedList = lists.stream().flatMap(Collection::stream).collect(Collectors.toList());
        System.out.println("flattenedList:" + flattenedList);
		// flattenedList zawiera [1, 2, 3, 4]

        // Distinct
        List<Integer> numbers3 = Arrays.asList(1, 2, 2, 3, 3, 4);
		List<Integer> distinctNumbers = numbers3.stream().distinct().collect(Collectors.toList());
        System.out.println("distinctNumbers: " + distinctNumbers);
		// distinctNumbers zawiera [1, 2, 3, 4]


        //sorted
        List<Integer> numbers4 = Arrays.asList(3, 1, 4, 1, 5, 9, 2, 6, 5, 3, 5);
		List<Integer> sortedNumbers = numbers4.stream().sorted().collect(Collectors.toList());
         System.out.println("sortedNumbers: " + sortedNumbers);
		// sortedNumbers zawiera [1, 1, 2, 3, 3, 4, 5, 5, 5, 6, 9]


        // ... i tak dalej 
    }
}
