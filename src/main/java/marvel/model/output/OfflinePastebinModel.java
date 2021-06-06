package marvel.model.output;

import marvel.model.character.CharacterInfo;

/**
 * Dummy version implementation of OutputModel. Returns dummy data on mutable and accessor calls.
 *
 * @see OutputModel
 */
public class OfflinePastebinModel implements OutputModel{

    /**
     * Inherited from OutputModel interface - no usage in offline model.
     *
     * @param handler - PastebinApiHandler instance that handles live API requests - ignored
     */
    @Override
    public void setApiHandler(PastebinApiHandler handler) { }

    @Override
    public boolean sendReport(CharacterInfo info) {
        if(info == null){
            return false;
        }
        return true;
    }

    /**
     * Retrieves the URL of paste created for last report sent
     *
     * Dummy version always return same dummy URL string to simulate behavior.
     *
     * @return String - URL of paste created for last report sent
     */
    @Override
    public String getReportUrl() {
        return "dummy-report-output-url";
    }


}
