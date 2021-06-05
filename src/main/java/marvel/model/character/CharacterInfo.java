package marvel.model.character;

import java.util.ArrayList;
import java.util.List;

public class CharacterInfo {
    private int id;
    private String name;
    private String description;
    private String modified;
    private List<String> urls;
    private String thumbnail;
    private List<Comic> comicList = new ArrayList<>();
    private List<Story> storyList = new ArrayList<>();
    private List<Series> seriesList = new ArrayList<>();

    public CharacterInfo(int id, String name, String description, String modified, List<String> urls, String thumbnail) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.modified = modified;
        this.urls = urls;
        this.thumbnail = thumbnail;
    }

    public void setComicList(List<Comic> comicList) {
        this.comicList = comicList;
    }

    public void setStoryList(List<Story> storyList) {
        this.storyList = storyList;
    }

    public void setSeriesList(List<Series> seriesList) {
        this.seriesList = seriesList;
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

    public List<String> getUrls() {
        return urls;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public List<Comic> getComicList() {
        return comicList;
    }

    public List<Story> getStoryList() {
        return storyList;
    }

    public List<Series> getSeriesList() {
        return seriesList;
    }
}
