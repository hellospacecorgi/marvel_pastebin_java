package marvel.model.output;

import marvel.model.character.CharacterInfo;

import java.util.List;

/**
 * Submodel interface for methods relating to interacting with the output Pastebin API.
 *
 * @see OnlinePastebinModel
 * @see OfflinePastebinModel
 * @see marvel.model.ModelImpl
 */
public interface OutputModel {
    /**
     * Sets a handler for processing POST requests and responses to the API
     *
     * @param handler PastebinApiHandler instance
     */
    public void setApiHandler(PastebinApiHandler handler);

    /**
     * Sets a helper class to generate the report string for sending to pastebin
     *
     * @param service ReportService instance that creates report from CharacterInfo object
     */
    public void setReportService(ReportService service);

    /**
     * Generate a report based on given CharacterInfo, sends report to output API
     *
     * @param info CharacterInfo object containing data to generate report on
     * @param unmatchedNames List of names that are not at the selected index in the list
     * @return boolean - return true if report successfully sent, otherwise null
     */
    public boolean sendReport(CharacterInfo info,  List<String> unmatchedNames);

    /**
     * Retrieves the URL of paste created for last report sent
     *
     * @return String - URL of paste created for last report sent
     */
    public String getReportUrl();

}
