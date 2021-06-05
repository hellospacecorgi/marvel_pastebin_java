package marvel.model;

public class Story {
    private String name;
    private String type;
    private String resourcePath;

    public Story( String name, String type, String resourcePath) {
        this.resourcePath = resourcePath;
        this.name = name;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public String getResourcePath() {
        return resourcePath;
    }
}
