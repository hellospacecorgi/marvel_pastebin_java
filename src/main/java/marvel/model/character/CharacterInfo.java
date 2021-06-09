package marvel.model.character;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents data retrieved on a specific Marvel character from a previous InputModel search.
 *
 * <p>Provides getters and setters for all fields.</p>
 *
 * <p>Fields description reference Marvel API's interactive documentation.
 * See <a href="https://developer.marvel.com/docs#!/public/getCreatorCollection_get_0">GET /v1/public/characters</a>
 * </p>
 * @see Comic
 * @see Event
 * @see ResourceUrl
 * @see Series
 * @see Story
 * @see Thumbnail
 */
public class CharacterInfo {
    /**
     * Unique ID of the character resource
     */
    private int id;
    /**
     * Name of the character
     */
    private String name;
    /**
     * A short bio or description of the character
     */
    private String description;
    /**
     * The date the resource was most recently modified
     */
    private String modified;
    /**
     * A set of public web sets for resource about character
     */
    private List<ResourceUrl> urls;
    /**
     * Object that contains path and extension for getting representative image for character
     */
    private Thumbnail thumbnail;
    /**
     * Number of total available issues featuring this character
     */
    private int nComics = 0;
    /**
     * Number of total available stories which this character appears.
     */
    private int nStories = 0;
    /**
     * Number of total available events which this character appears.
     */
    private int nEvents = 0;
    /**
     * Number of total available series which this character appears.
     */
    private int nSeries = 0;
    /**
     * Resource list of comics which feature this character (API has return limit of up to 20 items)
     */
    private List<Comic> comicList = new ArrayList<>();
    /**
     * Resource list of stories which this character appears (API has return limit of up to 20 items)
     */
    private List<Story> storyList = new ArrayList<>();
    /**
     * Resource list of events which this character appears (API has return limit of up to 20 items)
     */
    private List<Event> eventList = new ArrayList<>();
    /**
     * Resource list of series which this character appears (API has return limit of up to 20 items)
     */
    private List<Series> seriesList = new ArrayList<>();

    /**
     * Initialises a CharacterInfo object.
     *
     * <p>All fields are optional as listed on Marvel API documentation response class model for Character.</p>
     *
     * <p>Parameters description reference Marvel API's interactive documentation.
     * See <a href="https://developer.marvel.com/docs#!/public/getCreatorCollection_get_0">GET /v1/public/characters</a></p>
     *
     * @param id Unique ID of the character resource
     * @param name Name of the character
     * @param description A short bio or description of the character
     * @param modified The date the resource was most recently modified
     */
    public CharacterInfo(int id, String name, String description, String modified) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.modified = modified;
    }

    /**
     * Sets a Thumbnail that represents representative image of the character.
     *
     * @param thumbnail Represents representative image for a character
     */
    public void setThumbnail(Thumbnail thumbnail){
        this.thumbnail = thumbnail;
    }

    /**
     * Sets list of Comic which features the character
     *
     * @param comicList Represents list of comics which features the character
     */
    public void setComicList(List<Comic> comicList) {
        this.comicList = comicList;
    }

    /**
     * Sets list of Story which features the character
     *
     * @param storyList Represents list of stories which features the character
     */
    public void setStoryList(List<Story> storyList) {
        this.storyList = storyList;
    }

    /**
     * Sets list of Event which features the character
     *
     * @param eventList Represents list of events which features the character
     */
    public void setEventList(List<Event> eventList) {
        this.eventList = eventList;
    }

    /**
     * Sets list of Series which features the character
     *
     * @param seriesList Represents list of series which features the character
     */
    public void setSeriesList(List<Series> seriesList) {
        this.seriesList = seriesList;
    }

    /**
     * Sets list of ResourceUrls which has information about the character
     *
     * @param urlList Represents list of URLs that has information about the character
     */
    public void setUrls(List<ResourceUrl> urlList){
        this.urls = urlList;
    }

    /**
     * Returns the unique Marvel character ID for this character
     *
     * @return id - unique Marvel character ID for this character
     */
    public int getId() {
        return id;
    }

    /**
     * Returns the canonical name for this character
     *
     * @return String - the official Marvel character name for this character
     */
    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getModified() {
        return modified;
    }

    public List<ResourceUrl> getUrls() {
        return urls;
    }

    public Thumbnail getThumbnail() {
        return thumbnail;
    }

    public List<Comic> getComicList() {
        return comicList;
    }

    public List<Story> getStoryList() {
        return storyList;
    }

    public List<Event> getEventList() {
        return eventList;
    }

    public List<Series> getSeriesList() {
        return seriesList;
    }

    public int getNComics() {
        return nComics;
    }

    public void setNComics(int nComics) {
        this.nComics = nComics;
    }

    public int getNStories() {
        return nStories;
    }

    public void setNStories(int nStories) {
        this.nStories = nStories;
    }

    public int getNEvents() {
        return nEvents;
    }

    public void setNEvents(int nEvents) {
        this.nEvents = nEvents;
    }

    public int getNSeries() {
        return nSeries;
    }

    public void setNSeries(int nSeries) {
        this.nSeries = nSeries;
    }
}
