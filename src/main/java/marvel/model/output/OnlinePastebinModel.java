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
        if(info == null){
            return false;
        }
        String report = "Summary report for Marvel Character";
        report = report.concat("\nName: ").concat(info.getName());
        report = report.concat("\nID: ").concat(String.valueOf(info.getId()));
        report = report.concat("\nDescription: ").concat(info.getDescription());
        report = report.concat("\nThumbnail : ").concat(info.getThumbnail().getPath().concat("/standard_medium.").concat(info.getThumbnail().getExtension()));
        report = report.concat("\nNumber of Comics which feature this character: ").concat(String.valueOf(info.getNComics()));
        report = report.concat("\nNumber of Stories which this character appears: ").concat(String.valueOf(info.getNStories()));
        report = report.concat("\nNumber of Events which this character appears: ").concat(String.valueOf(info.getNEvents()));
        report = report.concat("\nNumber of Series which this character appears: ").concat(String.valueOf(info.getNSeries()));
        report = report.concat("\nData provided by Marvel. 2021 MARVEL");

        return handler.sendReport(info.getName(), report);
    }

    @Override
    public String getReportUrl() {
        return handler.getOutputUrl();
    }
}
