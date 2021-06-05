package marvel.model;

public class Comic {
    private String name;
    private String resourcePath;

    public Comic( String name, String resourcePath) {
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
