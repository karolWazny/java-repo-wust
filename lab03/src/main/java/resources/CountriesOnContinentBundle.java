package resources;

import java.util.ListResourceBundle;

public class CountriesOnContinentBundle extends ListResourceBundle {
    @Override
    protected Object[][] getContents() {
        return new Object[][]{
                {"question", "How many countries are there in "},
                {"correct", "This is a correct answer."},
                {"incorrect", "This is not a correct answer, the correct answer is {0}."}
        };
    }
}
