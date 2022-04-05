package mediane;

import ex.api.AnalysisException;
import ex.api.AnalysisService;
import ex.api.DataSet;

public class MedianeService implements AnalysisService {
    @Override
    public void setOptions(String[] options) throws AnalysisException {

    }

    @Override
    public String getName() {
        return "Median";
    }

    @Override
    public void submit(DataSet ds) throws AnalysisException {

    }

    @Override
    public DataSet retrieve(boolean clear) throws AnalysisException {
        return null;
    }
}
