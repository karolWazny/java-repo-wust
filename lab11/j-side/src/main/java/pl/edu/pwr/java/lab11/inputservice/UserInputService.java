package pl.edu.pwr.java.lab11.inputservice;

import pl.edu.pwr.java.lab11.Order;

public abstract class UserInputService {
    private static UserInputService instance;

    public static UserInputService getInstance(){
        if(instance == null){
            instance = defaultUserInputService();
        }
        return instance;
    }

    private static UserInputService defaultUserInputService(){
        return new DialogInputService();
    }

    public static void setService(UserInputService instance) {
        UserInputService.instance = instance;
    }

    public abstract Data getInput();
    public static class Data {
        public Double[] values;
        public Order order;
    }
}
