package trendline;

import ex.api.AnalysisException;
import ex.api.AnalysisService;
import ex.api.DataSet;

public class TrendlineService implements AnalysisService {
    @Override
    public void setOptions(String[] options) throws AnalysisException {

    }

    @Override
    public String getName() {
        return "Trend line";
    }

    @Override
    public void submit(DataSet ds) throws AnalysisException {

    }

    @Override
    public DataSet retrieve(boolean clear) throws AnalysisException {
        return null;
    }
}
