package marvel.model.output;

import marvel.model.character.CharacterInfo;

import java.util.List;

/**
 * Online version implementation of OutputModel. Sends data to the live Pastebin API.
 *
 * @see PastebinApiHandler
 */
public class OnlinePastebinModel implements OutputModel{
    /**
     * Handler for processing POST requests and responses to the API
     */
    private PastebinApiHandler handler;

    /**
     * Service for generating report based on CharacterInfo given
     */
    private ReportService service;

    /**
     * Sets a handler for processing POST requests and responses to the API
     *
     * @param handler PastebinApiHandler instance
     */
    @Override
    public void setApiHandler(PastebinApiHandler handler) {
        this.handler = handler;
    }

    /**
     * Sets service for generating a report baesd on CharacterInfo object
     * @param service ReportService instance that creates report from CharacterInfo object
     */
    @Override
    public void setReportService(ReportService service) {
        this.service = service;
    }

    /**
     * Generate a report based on given CharacterInfo, sends report to pastebin
     *
     * <p>Delegates actual sending of HttpRequest to PastebinApiHandler</p>
     *
     * <p>Delegates formatting of report string to ReportService</p>
     *
     * @param info CharacterInfo object containing data to generate report for
     * @param unmatchedNames List of names that are not at the selected index in the list
     * @return boolean - return true if report successfully sent, otherwise null
     */
    @Override
    public boolean sendReport(CharacterInfo info, List<String> unmatchedNames) {
        if(this.handler == null || this.service == null){
            throw new IllegalStateException();
        }
        if(info == null){
            return false;
        }
        if(unmatchedNames == null){
            throw new IllegalArgumentException();
        }
        String report = service.generateReport(info, unmatchedNames);
        return handler.sendReport(info.getName(), report);
    }

    /**
     * Retrieves the URL of paste created for last report sent
     *
     * @return String - URL of paste created for last report sent
     */
    @Override
    public String getReportUrl() {
        if(handler.getOutputUrl() != null){
            return handler.getOutputUrl();
        }
        return null;
    }
}
