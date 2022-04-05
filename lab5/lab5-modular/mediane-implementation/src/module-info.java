import ex.api.AnalysisService;
import mediane.MedianeService;

module mediane.implementation {
    requires statistics.api;
    exports mediane;
    provides AnalysisService
            with MedianeService;
}