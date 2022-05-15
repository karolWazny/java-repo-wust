package pl.edu.pwr.java.lab11;

import pl.edu.pwr.java.lab11.inputservice.UserInputService;

public class UserInputServiceMockup extends UserInputService {
    public Data input;

    @Override
    public Data getInput() {
        return input;
    }
}
