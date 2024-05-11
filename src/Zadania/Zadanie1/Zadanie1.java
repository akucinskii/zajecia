package Zadania.Zadanie1;

import java.util.ArrayList;
import java.util.List;

public class Zadanie1 {
    public static void main(String[] args) {
        List<Integer> listaZIntem = new ArrayList<>();
        listaZIntem.add(3);
        listaZIntem.add(1);
        listaZIntem.add(2);
        listaZIntem.add(4);
        listaZIntem.add(6);

        System.out.println(listaZIntem);
        listaZIntem = listaZIntem.stream().map(m -> m+2).toList();
        System.out.println(listaZIntem);


    }
}
