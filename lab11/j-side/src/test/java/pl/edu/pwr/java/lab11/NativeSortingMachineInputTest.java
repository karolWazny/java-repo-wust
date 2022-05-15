package pl.edu.pwr.java.lab11;

import org.junit.jupiter.api.*;
import pl.edu.pwr.java.lab11.inputservice.UserInputService;

import java.util.Arrays;
import java.util.stream.Collectors;

@Tag("native-sorting")
public class NativeSortingMachineInputTest {
    private static Double[] values;
    private static Double[] ascending = new Double[0];
    private static Double[] descending = new Double[0];

    private static UserInputService.Data data;

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
        UserInputServiceMockup mockup = new UserInputServiceMockup();
        data = new UserInputService.Data();
        data.values = values;
        mockup.input = data;
        UserInputService.setService(mockup);
    }

    @Test
    public void sort01TestAscending(){
        data.order = Order.ASCENDING;
        machine.getUserInputAndSort();
        Assertions.assertArrayEquals(ascending, machine.getResult());
    }

    @Test
    public void sort01TestDescending(){
        data.order = Order.DESCENDING;
        machine.getUserInputAndSort();
        Assertions.assertArrayEquals(descending, machine.getResult());
    }
}
