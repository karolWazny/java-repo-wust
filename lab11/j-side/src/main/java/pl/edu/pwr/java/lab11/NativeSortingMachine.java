package pl.edu.pwr.java.lab11;

import java.awt.*;
import java.util.Arrays;
import java.util.stream.Collectors;

public class NativeSortingMachine {

    static {
        System.loadLibrary("native");
    }

    private Double[] input = new Double[0];
    private Double[] result;
    private Boolean order = ASCENDING;

    public final static Boolean ASCENDING = true;
    public final static Boolean DESCENDING = false;

    public native Double[] sort(Double[] a, Boolean order);
    // zakładamy, że po stronie kodu natywnego będzie sortowana przekazana tablica a
    // (order=true oznacza rosnąco, order=false oznacza malejąco)
    // metoda powinna zwrócić posortowaną tablicę
    public native Double[] sort(Double[] a);
    // zakładamy, że drugi atrybut będzie pobrany z obiektu przekazanego do metody natywnej (czyli będzie brana wartość pole order)
    public native void getUserInputAndSort();
    // zakładamy, że po stronie natywnej utworzone zostanie okienko pozwalające zdefiniować zawartość tablicy do sortowania
    // oraz warunek określający sposób sortowania order.
    // wczytana tablica powinna zostać przekazana do obiektu Javy na pole a, zaś warunek sortowania powinien zostać przekazany
    // do pola orded
    // Wynik sortowania (tablica b w obiekcie Java) powinna wyliczać metoda Javy multi04
    // (korzystająca z parametrów a i order, wstawiająca wynik do b).

    public void sort(){
        result = sort(input);
    }

    public Boolean getOrder() {
        return order;
    }

    public void setOrder(Boolean order) {
        this.order = order;
    }

    public Double[] getResult() {
        return result.clone();
    }

    public Double[] getInput() {
        return input;
    }

    public void setInput(Double[] input) {
        this.input = input;
    }

    public void showDialog(){
        Frame f= new Frame();
        Dialog d = new Dialog(f , "Dialog Example", true);
        d.setLayout( new FlowLayout() );
        Button b = new Button ("OK");
        b.addActionListener (e -> d.setVisible(false));
        d.add( new Label ("Click button to continue."));
        d.add(b);
        d.setSize(300,300);
        d.setVisible(true);
    }

    public static void main(String[] args){
        Double[] input = {3.14, 2.18, 5.12, 3.3333, 2.79};
        NativeSortingMachine machine = new NativeSortingMachine();
        machine.setOrder(NativeSortingMachine.ASCENDING);
        Double[] resultAscending = machine.sort(input);
        machine.setOrder(NativeSortingMachine.DESCENDING);
        Double[] resultDescending = machine.sort(input);
        System.out.println("Ascending: " + Arrays.stream(resultAscending).collect(Collectors.toList()));
        System.out.println("Descending: " + Arrays.stream(resultDescending).collect(Collectors.toList()));
    }
}
