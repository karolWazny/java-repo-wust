package resources;

import java.util.ListResourceBundle;

public class QuestionPanelBundle extends ListResourceBundle {
    @Override
    protected Object[][] getContents() {
        return new Object[][]{
                {"confirmButton", "Confirm"},
                {"answerLabel", "Answer: "}
        };
    }
}