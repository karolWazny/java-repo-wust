package resources;

import java.util.ListResourceBundle;

public class CountriesOnContinentBundle extends ListResourceBundle {
    @Override
    protected Object[][] getContents() {
        return new Object[][]{
                {"question", "How many countries are there in {0}?"}
        };
    }
}
