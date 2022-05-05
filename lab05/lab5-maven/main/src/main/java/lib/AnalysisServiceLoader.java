package lib;

import ex.api.AnalysisService;

import java.util.ArrayList;
import java.util.List;
import java.util.ServiceLoader;

public class AnalysisServiceLoader {
    public static List<AnalysisService> loadAvailableServices(){
        List<AnalysisService> output = new ArrayList<>();
        ServiceLoader<AnalysisService> loader = ServiceLoader.load(AnalysisService.class);
        for (AnalysisService service:
             loader) {
            output.add(service);
        }
        return output;
    }
}
