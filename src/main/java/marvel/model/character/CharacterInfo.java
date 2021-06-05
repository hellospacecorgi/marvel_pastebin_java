package marvel.model.character;

import java.util.ArrayList;
import java.util.List;

public class CharacterInfo {
    private int id;
    private String name;
    private String description;
    private String modified;
    private List<ResourceUrl> urls;
    private Thumbnail thumbnail;
    private int nComics = 0;
    private int nStories = 0;
    private int nEvents = 0;
    private int nSeries = 0;
    private List<Comic> comicList = new ArrayList<>();
    private List<Story> storyList = new ArrayList<>();
    private List<Event> eventList = new ArrayList<>();
    private List<Series> seriesList = new ArrayList<>();

    public CharacterInfo(int id, String name, String description, String modified) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.modified = modified;
    }

    public void setThumbnail(Thumbnail thumbnail){
        this.thumbnail = thumbnail;
    }

    public void setComicList(List<Comic> comicList) {
        this.comicList = comicList;
    }

    public void setStoryList(List<Story> storyList) {
        this.storyList = storyList;
    }

    public void setEventList(List<Event> eventList) {
        this.eventList = eventList;
    }

    public void setSeriesList(List<Series> seriesList) {
        this.seriesList = seriesList;
    }

    public void setUrls(List<ResourceUrl> urlList){
        this.urls = urlList;
    }

    public int getId() {
        return id;
    }

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
