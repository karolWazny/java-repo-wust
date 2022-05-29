package pl.edu.pwr.java.lab13.jsfx.csv;


import java.util.Objects;
import java.util.stream.IntStream;

public class DataSet {
    private String[] header = {};
    private String[][] data = {{}};

    private <T> T[][] deepCopy(T[][] matrix) {
        return java.util.Arrays.stream(matrix).map(el -> el.clone()).toArray(i -> matrix.clone());
    }

    public String[] getHeader() {
        return header;
    }
    public void setHeader(String[] header) {
        this.header = header.clone();
    }
    public String[][] getData() {
        return data;
    }
    public void setData(String[][] data) {
        this.data = deepCopy(data);
    }

    public Cursor cursor(){
        return new Cursor(this);
    }

    public static class Cursor {
        private int index = -1;
        private final DataSet dataSet;

        private Cursor(DataSet dataSet) {
            this.dataSet = dataSet;
        }

        public boolean next() {
            index++;
            if(index > dataSet.data.length)
                index = dataSet.data.length;
            return index != -1 && index < dataSet.data.length;
        }

        public boolean previous() {
            index--;
            if(index < -1)
                index = -1;
            return index != -1;
        }

        private int indexOf(String val) {
            return IntStream.range(0, dataSet.header.length)
                    .filter(i -> Objects.equals(dataSet.header[i], val))
                    .findFirst()
                    .orElse(-1);
        }

        public String get(String key) {
            int keyPos = indexOf(key);
            return dataSet.data[index][keyPos];
        }
    }
}