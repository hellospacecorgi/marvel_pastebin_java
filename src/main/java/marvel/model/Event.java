package marvel.model;

public class Event {
    private String name;
    private String resourcePath;

    public Event(String name, String resourcePath) {
        this.resourcePath = resourcePath;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public String getResourcePath() {
        return resourcePath;
    }
}
