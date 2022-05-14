package pl.edu.pwr.java.lab11;

public class NativeSortingMachine {

    static {
        System.loadLibrary("native");
    }

    private Double[] input = new Double[0];
    private Double[] result;
    private Boolean order;

    public final static Boolean ASCENDING = true;
    public final static Boolean DESCENDING = false;

    public native Double[] sort01(Double[] a, Boolean order);
    // zakładamy, że po stronie kodu natywnego będzie sortowana przekazana tablica a
    // (order=true oznacza rosnąco, order=false oznacza malejąco)
    // metoda powinna zwrócić posortowaną tablicę
    public native Double[] sort02(Double[] a);
    // zakładamy, że drugi atrybut będzie pobrany z obiektu przekazanego do metody natywnej (czyli będzie brana wartość pole order)
    public native void sort03();
    // zakładamy, że po stronie natywnej utworzone zostanie okienko pozwalające zdefiniować zawartość tablicy do sortowania
    // oraz warunek określający sposób sortowania order.
    // wczytana tablica powinna zostać przekazana do obiektu Javy na pole a, zaś warunek sortowania powinien zostać przekazany
    // do pola orded
    // Wynik sortowania (tablica b w obiekcie Java) powinna wyliczać metoda Javy multi04
    // (korzystająca z parametrów a i order, wstawiająca wynik do b).

    public void sort04(){
        // sortuje a według order, a wynik wpisuje do b
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

    public static void main(String[] args){
        new NativeSortingMachine().sort03();
    }
}
