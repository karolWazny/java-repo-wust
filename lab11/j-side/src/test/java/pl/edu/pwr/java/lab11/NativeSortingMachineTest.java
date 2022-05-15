package pl.edu.pwr.java.lab11;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.stream.Collectors;

public class NativeSortingMachineTest {
    private static Double[] values;
    private static Double[] ascending = new Double[0];
    private static Double[] descending = new Double[0];

    private NativeSortingMachine machine;

    @BeforeEach
    private void prepareMachine(){
        machine = new NativeSortingMachine();
    }

    @BeforeAll
    static void prepare(){
        values = new Double[]{3.14, 2.18, 5.12, 3.3333, 2.79};
        ascending = Arrays.stream(values)
                .sorted()
                .collect(Collectors.toList()).toArray(ascending);
        descending = Arrays.stream(values)
                .sorted((o1, o2) -> -o1.compareTo(o2))
                .collect(Collectors.toList()).toArray(descending);
    }

    @Test
    public void sort01TestAscending(){
        Double[] result = machine.sort(values, NativeSortingMachine.ASCENDING);
        Assertions.assertArrayEquals(ascending, result);
    }

    @Test
    public void sort01TestDescending(){
        Double[] result = machine.sort(values, NativeSortingMachine.DESCENDING);
        Assertions.assertArrayEquals(descending, result);
    }

    @Test
    public void sort02TestDefaultOrder(){
        Double[] result = machine.sort(values);
        Assertions.assertArrayEquals(ascending, result);
    }

    @Test
    public void sort02TestAscending(){
        machine.setOrder(NativeSortingMachine.ASCENDING);
        Double[] result = machine.sort(values);
        Assertions.assertArrayEquals(ascending, result);
    }

    @Test
    public void sort02TestDescending(){
        machine.setOrder(NativeSortingMachine.DESCENDING);
        Double[] result = machine.sort(values);
        Assertions.assertArrayEquals(descending, result);
    }

    @Test
    public void sort04TestAscending(){
        machine.setOrder(NativeSortingMachine.ASCENDING);
        machine.setInput(values);
        machine.sort();
        Double[] result = machine.getResult();
        Assertions.assertArrayEquals(ascending, result);
    }

    @Test
    public void sort04TestDescending(){
        machine.setOrder(NativeSortingMachine.DESCENDING);
        machine.setInput(values);
        machine.sort();
        Double[] result = machine.getResult();
        Assertions.assertArrayEquals(descending, result);
    }
}
