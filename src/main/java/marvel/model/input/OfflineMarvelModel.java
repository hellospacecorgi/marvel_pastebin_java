package marvel.model.input;

import javafx.scene.image.Image;
import marvel.model.character.*;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

/**
 * Dummy version implementation of InputModel. Returns dummy data on mutable and accessor calls.
 *
 * @see InputModel
 */
public class OfflineMarvelModel implements InputModel{
    /**
     * Inherited from InputModel interface - no usage in offline model.
     * @param handler MarvelApiHandler that handles live API requests - ignored
     */
    @Override
    public void setApiHandler(MarvelApiHandler handler){ }

    /**
     * Simulates a response from successful character name search.
     *
     * @param name Specified character name to search API with
     * @return CharacterInfo - dummy CharacterInfo object with dummy data
     */
    @Override
    public CharacterInfo getInfoByName(String name) {
        List<ResourceUrl> urls = new ArrayList<>();
        urls.add(new ResourceUrl("wiki", "dummy-url.com"));
        urls.add(new ResourceUrl("blog", "another-dummy.com"));

        List<Comic> comics = new ArrayList<>();
        comics.add(new Comic("One time hero", "comic-not-published.com"));

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
        dummy.setComicList(comics);
        dummy.setStoryList(stories);
        dummy.setEventList(events);
        dummy.setSeriesList(series);
        dummy.setNEvents(234);
        dummy.setNStories(144);
        dummy.setNComics(1);
        dummy.setNSeries(53);

        return dummy;

    }

    /**
     * Simulates accessor call for retrieving full path to thumbnail image from CharacterInfo
     * Since this is a dummy version, return null will let Presenter load default dummy image to display as thumbnail.
     *
     * @param info CharacterInfo object that contains
     * @return String - always return null in dummy version
     */
    @Override
    public String getThumbnailFullPath(CharacterInfo info) {
        return null;
    }

}
