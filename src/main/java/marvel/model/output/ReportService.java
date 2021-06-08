package marvel.model.output;

import marvel.model.character.CharacterInfo;

/**
 * Helper class that creates report string from CharacterInfo object
 */
public class ReportService {

    /**
     * Takes in a CharacterInfo object, generates a string report based on it's attributes
     *
     * @param info CharacterInfo object that contains data
     * @return String - Report on character's information that is formatted for readability
     */
    public String generateReport(CharacterInfo info){
        if(info == null){
            return null;
        }
        String report = "Summary report for Marvel Character";
        report = report.concat("\nName: ").concat(info.getName());
        report = report.concat("\nID: ").concat(String.valueOf(info.getId()));
        report = report.concat("\nDescription: ").concat(info.getDescription());
        report = report.concat("\n\nThumbnail : ").concat(info.getThumbnail().getPath().concat("/standard_medium.").concat(info.getThumbnail().getExtension()));
        report = report.concat("\nResource last modified: ").concat(info.getModified());

        report = report.concat("\n\nURLs to public websites containing character information");
        for(int i = 0; i < info.getUrls().size(); i ++){
            report = report.concat("\n\tType: ").concat(info.getUrls().get(i).getType());
            report = report.concat(" URL: ").concat(info.getUrls().get(i).getUrl());
        }

        report = report.concat("\n\nNumber of Comics which feature this character: ").concat(String.valueOf(info.getNComics()));
        report = report.concat("\nNumber of Stories which this character appears: ").concat(String.valueOf(info.getNStories()));
        report = report.concat("\nNumber of Events which this character appears: ").concat(String.valueOf(info.getNEvents()));
        report = report.concat("\nNumber of Series which this character appears: ").concat(String.valueOf(info.getNSeries()));

        report = report.concat("\n\nComics List:");
        for(int i = 0 ; i < info.getComicList().size() ; i++){
            report = report.concat("\n\tName: ").concat(info.getComicList().get(i).getName());
            report = report.concat(" Resource URI: ").concat(info.getComicList().get(i).getResourcePath());
        }

        report = report.concat("\n\nStories List:");
        for(int i = 0 ; i < info.getStoryList().size() ; i++){
            report = report.concat("\n\tName: ").concat(info.getStoryList().get(i).getName());
            report = report.concat(" Type: ").concat(info.getStoryList().get(i).getType());
            report = report.concat(" Resource URI: ").concat(info.getStoryList().get(i).getResourcePath());
        }

        report = report.concat("\n\nEvents List:");
        for(int i = 0 ; i < info.getEventList().size() ; i++){
            report = report.concat("\n\tName: ").concat(info.getEventList().get(i).getName());
            report = report.concat(" Resource URI: ").concat(info.getEventList().get(i).getResourcePath());
        }

        report = report.concat("\n\nSeries List:");
        for(int i = 0 ; i < info.getSeriesList().size() ; i++){
            report = report.concat("\n\tName: ").concat(info.getSeriesList().get(i).getName());
            report = report.concat(" Resource URI: ").concat(info.getSeriesList().get(i).getResourcePath());
        }

        report = report.concat("\n\nData provided by Marvel. 2021 MARVEL");
        return report;
    }


}
