package resources;

import java.util.ListResourceBundle;

public class CountriesOnContinentBundle_pl_PL extends ListResourceBundle {
    @Override
    protected Object[][] getContents() {
        return new Object[][]{
                {"question", "Ile jest pa\u0144stw na kontynencie "},
                {"correct", "To jest prawid\u0142owa odpowied\u017a."},
                {"incorrect", "To nie jest prawid\u0142owa odpowied\u017a, prawid\u0142owa odpowied\u017a to {0}."}
        };
    }
}
