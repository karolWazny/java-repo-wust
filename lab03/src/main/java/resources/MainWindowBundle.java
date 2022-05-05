package resources;

import java.util.ListResourceBundle;

public class MainWindowBundle  extends ListResourceBundle {
    @Override
    protected Object[][] getContents() {
        return new Object[][]{
                {"title", "GeoQuiz"},
                {"languageButton", "Switch language"},
                {"questionButton", "Next question"}
        };
    }
}