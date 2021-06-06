package marvel.model.output;

import marvel.model.character.CharacterInfo;

/**
 * Interface for methods relating to interacting with the output Pastebin API.
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

    public boolean sendReport(CharacterInfo info);
    public String getReportUrl();

}
