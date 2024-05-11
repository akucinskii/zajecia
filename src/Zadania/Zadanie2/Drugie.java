package Zadania.Zadanie2;

public class Drugie {
    public static void main(String[] args) {
        MathOperation dodawanie = (a, b) -> (a+b)+"";
        MathOperation dodawanie2 = (a, b) -> {return (a+b)+"";};
        MathOperation mnozenie = (c, d) -> {
            int z = c*d;
          return z+"";
        };
        String test = dodawanie.operacja(4, 7);
        System.out.println(test);
        String test2 = dodawanie2.operacja(4, 7);
        System.out.println(test2);
        String test3 = mnozenie.operacja(4, 7);
        System.out.println(test3);
    }
}
