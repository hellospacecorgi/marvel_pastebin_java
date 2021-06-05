package marvel.model;

public class OfflinePastebinModel implements OutputModel{
    @Override
    public boolean sendReport(CharacterInfo info) {
        return false;
    }
}
