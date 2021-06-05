package marvel.model.output;

import marvel.model.character.CharacterInfo;

public class OfflinePastebinModel implements OutputModel{
    @Override
    public void setApiHandler(PastebinApiHandler handler) { }

    @Override
    public boolean sendReport(CharacterInfo info) {
        if(info == null){
            return false;
        }
        return true;
    }

    @Override
    public String getReportUrl() {
        return "dummy-report-output-url";
    }


}
