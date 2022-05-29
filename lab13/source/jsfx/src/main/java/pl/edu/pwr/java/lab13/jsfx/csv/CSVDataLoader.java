package pl.edu.pwr.java.lab13.jsfx.csv;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.stream.Collectors;

public class CSVDataLoader {
    private String delimiter = ",";
    public DataSet readFromFile(Path file) throws FileFormatException {
        try {
            String[][] outputData = Files.lines(file)
                    .skip(1)
                    .map(line-> Arrays.stream(line.split(delimiter))
                            .map(String::trim)
                            .collect(Collectors.toList())
                            .toArray(new String[0]))
                    .collect(Collectors.toList())
                    .toArray(new String[0][]);
            String[] header = Files.lines(file)
                    .findFirst()
                    .stream()
                    .map(line-> Arrays.stream(line.split(delimiter))
                            .map(String::trim)
                            .collect(Collectors.toList())
                            .toArray(new String[0]))
                    .collect(Collectors.toList())
                    .get(0);

            int index = 0;
            for(String[] line
                    : outputData){
                if(line.length != header.length)
                    throw new FileFormatException("This is not a proper CSV file.");
                outputData[index] = line;
                index++;
            }
            DataSet dataSet = new DataSet();
            dataSet.setData(outputData);
            dataSet.setHeader(header);
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
