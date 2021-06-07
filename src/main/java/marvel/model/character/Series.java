package marvel.model.character;
/**
 * Represents series data including name of series and path to resource URI.
 *
 * @see CharacterInfo
 */
public class Series {
    /**
     * Canonical name of the series
     */
    private String name;
    /**
     * Path to individual series resource
     */
    private String resourcePath;

    /**
     * Constructor for creating a Series.
     *
     * @param name The name of the series
     * @param resourcePath Path to individual series resource
     */
    public Series( String name, String resourcePath) {
        this.resourcePath = resourcePath;
        this.name = name;
    }
    /**
     * Getter for name of Series
     *
     * @return name - String, name of the Series
     */
    public String getName() {
        return name;
    }

    /**
     * Getter for path to resource URI of Series
     *
     * @return resourcePath - path to resource URI of Series
     */
    public String getResourcePath() {
        return resourcePath;
    }
}
