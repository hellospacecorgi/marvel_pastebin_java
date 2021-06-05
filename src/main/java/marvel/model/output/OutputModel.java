package marvel.model.output;

import marvel.model.character.CharacterInfo;
import marvel.model.input.MarvelApiHandler;

public interface OutputModel {

    public void setApiHandler(PastebinApiHandler handler);

    public boolean sendReport(CharacterInfo info);
    public String getReportUrl();

}
