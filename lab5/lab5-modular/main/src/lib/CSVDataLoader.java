package lib;

import ex.api.DataSet;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;

public class CSVDataLoader {
    private String delimiter = ",";
    public DataSet readFromFile(Path file) throws FileFormatException {
        try {
            String[][] lines = (String[][]) Files.lines(file)
                    .map(line-> Arrays.stream(line.split(delimiter))
                            .map(String::trim))
                    .toArray();
            String[][] outputData = new String[lines.length - 1][];

            int index = 0;
            for(String[] line : lines){
                if(line.length != lines[0].length)
                    throw new FileFormatException("This is not a proper CSV file.");
                outputData[index] = line;
                index++;
            }
            DataSet dataSet = new DataSet();
            dataSet.setData(outputData);
            dataSet.setHeader(lines[0]);
            return dataSet;
        } catch (IOException e) {
            e.printStackTrace();
        }
        throw new RuntimeException("Something went wrong...");
    }

    public String getDelimiter() {
        return delimiter;
    }

    public void setDelimiter(String delimiter) {
        this.delimiter = delimiter;
    }
}
