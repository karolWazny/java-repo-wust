package resources;

import java.util.ListResourceBundle;

public class AdminDivisionsBundle_en_EN extends ListResourceBundle {
    @Override
    protected Object[][] getContents() {
        return new Object[][]{
                {"question", "How first level administrative divisions are there in "},
                {"correct", "This is a correct answer."},
                {"incorrect", "This is not a correct answer, the correct answer is {0}."}
        };
    }
}