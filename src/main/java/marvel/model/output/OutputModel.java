package marvel.model.output;

import marvel.model.character.CharacterInfo;

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
     * @param handler - PastebinApiHandler instance
     */
    public void setApiHandler(PastebinApiHandler handler);

    /**
     * Generate a report based on given CharacterInfo, sends report to pastebin
     *
     * @param info - CharacterInfo object containing data to generate report on
     * @return boolean - return true if report successfully sent, otherwise null
     */
    public boolean sendReport(CharacterInfo info);

    /**
     * Retrieves the URL of paste created for last report sent
     *
     * @return String - URL of paste created for last report sent
     */
    public String getReportUrl();

}
