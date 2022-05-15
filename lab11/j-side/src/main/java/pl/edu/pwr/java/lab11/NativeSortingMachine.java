package pl.edu.pwr.java.lab11;

import java.util.Arrays;

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
    public native Double[] sort(Double[] a);
    public native void getUserInputAndSort();

    public void sort(){
        result = sort(input);
    }

    public Boolean getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = booleanFromOrder(order);
    }

    private Boolean booleanFromOrder(Order order){
        switch (order){
            case ASCENDING:
                return ASCENDING;
            case DESCENDING:
                return DESCENDING;
            default:
                throw new RuntimeException("Incorrect order!");
        }
    }

    public void setOrder(Boolean order) {
        this.order = order;
    }

    public Double[] getResult() {
        return result.clone();
    }

    public void setInput(Double[] input) {
        this.input = input;
    }

    public static void main(String[] args){
        NativeSortingMachine machine = new NativeSortingMachine();
        machine.getUserInputAndSort();
        Arrays.stream(machine.result)
                .forEach(System.out::println);
    }
}
