import deviation.StandardDeviationService;
import ex.api.AnalysisService;

module standard.deviation.implementation {
    requires statistics.api;
    exports deviation;
    provides AnalysisService
            with StandardDeviationService;
}