import ex.api.AnalysisService;
import mediane.MedianService;

module mediane.implementation {
    requires statistics.api;
    exports mediane;
    provides AnalysisService
            with MedianService;
}