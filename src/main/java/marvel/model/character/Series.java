package marvel.model.character;

public class Series {
    private String name;
    private String resourcePath;

    public Series( String name, String resourcePath) {
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
