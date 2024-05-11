package Zadania.Zadanie3;

public class Book {
    String title;
    Author autor;

    public Book(String title, Author autor) {
        this.title = title;
        this.autor = autor;
    }

    public Book(String title, String imie, String nazwisko) {
        this.title = title;
        this.autor = new Author(imie, nazwisko);
    }

    @Override
    public String toString() {
        return "Book{" +
                "title='" + title + '\'' +
                ", autor=" + autor +
                '}';
    }
}
