package marvel.model.input;

import javafx.scene.image.Image;
import marvel.model.character.*;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

public class OfflineMarvelModel implements InputModel{
    /**
     * Inherited from InputModel interface - no usage in offline model.
     * @param handler
     */
    @Override
    public void setApiHandler(MarvelApiHandler handler){ }

    @Override
    public CharacterInfo getInfoByName(String name) {
        List<ResourceUrl> urls = new ArrayList<>();
        urls.add(new ResourceUrl("wiki", "dummy-url.com"));
        urls.add(new ResourceUrl("blog", "another-dummy.com"));

        List<Event> events = new ArrayList<>();
        events.add(new Event("Very important event", "dummy-path"));
        events.add(new Event("Another very important event", "dummy-path"));

        List<Story> stories = new ArrayList<>();
        stories.add(new Story("Once upon a time", "Horror","dummy-path"));
        stories.add(new Story("Tale as old as time", "Sci-Fi","dummy-path"));

        List<Series> series = new ArrayList<>();
        series.add(new Series("Time series", "math-9999-edu-au"));

        Thumbnail thb = new Thumbnail("./src/main/resources/marvel/dummy.png", "png");
        CharacterInfo dummy = new CharacterInfo(2222, name, "A hero that is created for the sake of dummy version.", "1999-999-9999");
        dummy.setUrls(urls);
        dummy.setThumbnail(thb);
        dummy.setStoryList(stories);
        dummy.setEventList(events);
        dummy.setSeriesList(series);
        dummy.setNEvents(234);
        dummy.setNStories(144);
        dummy.setNComics(0);
        dummy.setNSeries(53);
        return dummy;
    }

    @Override
    public String getThumbnailFullPath(CharacterInfo info) {
       if(info == null) {
           return null;
       }
       return "./src/main/resources/marvel/dummy.png";
    }

}
