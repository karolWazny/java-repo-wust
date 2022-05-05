import ex.api.AnalysisService;
import trendline.TrendlineService;

module trendline.implementation {
    requires statistics.api;
    exports trendline;
    provides AnalysisService
            with TrendlineService;
}