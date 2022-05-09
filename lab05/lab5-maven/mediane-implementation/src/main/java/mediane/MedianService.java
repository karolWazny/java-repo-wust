package mediane;

import ex.api.AbstractService;
import ex.api.AnalysisException;
import ex.api.DataSet;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

public class MedianService extends AbstractService {
    private DataSet output;
    private DataSet input;

    private final AtomicBoolean isProcessing = new AtomicBoolean(false);

    private int numberOfRows;

    private List<String> outputValues;
    private List<String> headers;

    @Override
    public void setOptions(String[] options) throws AnalysisException {

    }

    @Override
    public String getName() {
        return "Median";
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
        outputValues = new LinkedList<>();
        outputValues.add("median");
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
        String[][] data = new String[1][];
        data[0] = outputValues.toArray(new String[0]);
        output.setData(data);
        return output;
    }

    private void processColumnWithIndex(int columnIndex){
        String[][] data = input.getData();
        List<Double> values = new ArrayList<>(numberOfRows);
        for(int i = 0; i < input.getData().length; i++){
            double value = Double.parseDouble(data[i][columnIndex].trim());
            values.add(i, value);
        }
        values.sort(Double::compareTo);
        double medianValue;
        if(values.size() % 2 == 1)
            medianValue = values.get(values.size()/2);
        else
            medianValue = (values.get(values.size() / 2) + values.get(values.size() / 2 - 1)) / 2.0;
        outputValues.add("" + medianValue);
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
