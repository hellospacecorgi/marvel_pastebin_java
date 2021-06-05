package marvel.model.output;

import marvel.model.character.CharacterInfo;

public class OnlinePastebinModel implements OutputModel{
    private PastebinApiHandler handler;

    @Override
    public void setApiHandler(PastebinApiHandler handler) {
        this.handler = handler;
    }

    @Override
    public boolean sendReport(CharacterInfo info) {
        return false;
    }

    @Override
    public String getReportUrl() {
        return null;
    }
}
