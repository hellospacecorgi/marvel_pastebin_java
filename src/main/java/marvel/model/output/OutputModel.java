package marvel.model.output;

import marvel.model.character.CharacterInfo;

public interface OutputModel {

    public boolean sendReport(CharacterInfo info);
    public String getReportUrl();

}
