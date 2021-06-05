package marvel.model.output;

import marvel.model.character.CharacterInfo;

public class OfflinePastebinModel implements OutputModel{
    @Override
    public boolean sendReport(CharacterInfo info) {
        return false;
    }

    @Override
    public String getReportUrl() {
        return null;
    }


}
