package deviation;

import ex.api.AbstractService;
import ex.api.AnalysisException;
import ex.api.DataSet;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

public class StandardDeviationService extends AbstractService {
    private DataSet output;
    private DataSet input;

    private final AtomicBoolean isProcessing = new AtomicBoolean(false);

    private int numberOfRows;

    private List<String> firstRow;
    private List<String> secondRow;
    private List<String> headers;

    @Override
    public void setOptions(String[] options) throws AnalysisException {

    }

    @Override
    public String getName() {
        return "Standard deviation";
    }

    @Override
    public void submit(DataSet ds) throws AnalysisException {
        synchronized (isProcessing){
            if(isProcessing.get())
                throw new AnalysisException("Cannot submit new task, while previous one is still being processed!");
            isProcessing.set(true);
        }
        input = ds;
        numberOfRows = input.getData().length;

        new Thread(this::process).start();
    }

    private void process(){
        firstRow = new LinkedList<>();
        firstRow.add("mean");
        secondRow = new LinkedList<>();
        secondRow.add("deviation");
        headers = new LinkedList<>();
        headers.add("statistic:");

        for(int i = 0; i < input.getHeader().length; i++){
            processColumnWithIndex(i);
        }

        output = buildResult();

        synchronized (isProcessing){
            isProcessing.set(false);
        }

        notifyListeners();
    }

    private DataSet buildResult(){
        DataSet output = new DataSet();
        output.setHeader(headers.toArray(new String[0]));
        String[][] data = new String[2][];
        data[0] = firstRow.toArray(new String[0]);
        data[1] = secondRow.toArray(new String[0]);
        output.setData(data);
        return output;
    }

    private void processColumnWithIndex(int columnIndex){
        double sum = 0.0;
        String[][] data = input.getData();
        List<Double> values = new LinkedList<>();
        for(int i = 0; i < input.getData().length; i++){
            double value = Double.parseDouble(data[i][columnIndex].trim());
            sum += value;
            values.add(value);
        }
        double mean = sum / (double) numberOfRows;
        sum = 0.0;
        for(Double value : values){
            sum += (mean - value) * (mean - value);
        }
        double deviation = Math.sqrt(sum / numberOfRows);
        firstRow.add("" + mean);
        secondRow.add("" + deviation);
        headers.add(input.getHeader()[columnIndex]);
    }

    @Override
    public DataSet retrieve(boolean clear) throws AnalysisException {
        synchronized (isProcessing){
            if(isProcessing.get())
                return null;
        }
        DataSet output = this.output;
        if(clear)
            this.output = null;
        return output;
    }
}
