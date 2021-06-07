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
     * @param handler PastebinApiHandler instance that handles live API requests - ignored
     */
    @Override
    public void setApiHandler(PastebinApiHandler handler) { }

    /**
     * Simulate send report request is processed by returning boolean value.
     *
     * @param info CharacterInfo object containing data to generate report on
     * @return boolean - Return true if info is not null, otherwise return false
     */
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
     * <p>Dummy version always return same dummy URL string to accessor behavior.</p>
     *
     * @return String - URL of paste created for last report sent
     */
    @Override
    public String getReportUrl() {
        return "dummy-report-output-url";
    }


}
