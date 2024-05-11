package Zadania.Zadanie3;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class Trzecie {

    public static final Comparator<Book> BOOK_COMPARATOR = Comparator.comparing((Book b) -> b.autor.lastName)
            .thenComparing(b -> b.autor.firstName).thenComparing(b -> b.title);

    public static void main(String[] args) {
        List<Book> books = new ArrayList<>();
        Author adam = new Author("Adam", "Mickiewicz");
        books.add(new Book("Pan Tadeusz", adam));
        books.add(new Book("Dziady 4", adam));
        books.add(new Book("Dziady 2", adam));
        books.add(new Book("Dziady 3", adam));
        books.add(new Book("W pustyni i w puszczy", "Henryk", "Sienkiewicz"));

        books = books.stream().sorted(BOOK_COMPARATOR)
                .collect(Collectors.toList());
        System.out.println("books.stream().sorted(BOOK_COMPARATOR);");
        System.out.println(books);
        Collections.sort(books, BOOK_COMPARATOR);
        Collections.sort(books, (b1, b2) -> {
            int wynik = b1.autor.lastName.compareTo(b2.autor.lastName);
            if (wynik==0) {
                wynik = b1.autor.firstName.compareTo(b2.autor.firstName);
            }
            if (wynik==0) {
                wynik = b1.title.compareTo(b2.title);
            }
        return wynik;});

        System.out.println("Collections.sort(books, BOOK_COMPARATOR);");
        System.out.println(books);

        List<Author> authors = books.stream().map(book -> book.autor)
                .distinct()
                .toList();
        System.out.println(authors);
    }
}
