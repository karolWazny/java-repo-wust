package trendline;

import ex.api.AbstractService;
import ex.api.AnalysisException;
import ex.api.DataSet;

import java.util.concurrent.atomic.AtomicBoolean;

public class TrendlineService extends AbstractService {
    private DataSet output;
    private DataSet input;

    private Double a;
    private Double b;

    private final AtomicBoolean isProcessing = new AtomicBoolean(false);

    private int numberOfRows;

    @Override
    public void setOptions(String[] options) throws AnalysisException {

    }

    @Override
    public String getName() {
        return "Trend line";
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
        a = null;
        b = null;

        double n = numberOfRows;
        String[][] data = input.getData();

        double sxx = 0.0;
        double sx = 0.0;
        double sxy = 0.0;
        double sy = 0.0;

        for(int i = 0; i < numberOfRows; i++){
            double x = Double.parseDouble(data[i][0].trim());
            double y = Double.parseDouble(data[i][1].trim());

            sxx += x * x;
            sxy += x * y;
            sx += x;
            sy += y;
        }

        a = (sx * sy - sxy * n) / ( sx * sx - sxx * n);
        b = (sx * sxy - sxx * sy) / ( sx * sx - sxx * n);

        output = buildResult();

        synchronized (isProcessing){
            isProcessing.set(false);
        }

        notifyListeners();
    }

    private DataSet buildResult(){
        DataSet output = new DataSet();
        output.setHeader(new String[]{"a", "b"});
        String[][] data = new String[1][];
        data[0] = new String[]{"" + a, "" + b};
        output.setData(data);
        return output;
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
