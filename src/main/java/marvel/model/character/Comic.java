package marvel.model.character;

/**
 * Represents comic issue data including name of issue and path to resource URI.
 *
 * @see CharacterInfo
 */
public class Comic {
    /**
     * Canonical name of the comic
     */
    private String name;
    /**
     * Path to inidividual comic resource
     */
    private String resourcePath;

    /**
     * Constructor for creating a Comic.
     *
     * @param name Canonical name of the comic
     * @param resourcePath Path to individual comic resource
     */
    public Comic(String name, String resourcePath) {
        this.resourcePath = resourcePath;
        this.name = name;
    }

    /**
     * Getter for name of Comic
     *
     * @return name - String, name of the comic
     */
    public String getName() {
        return name;
    }

    /**
     * Getter for path to resource URI of Comic
     *
     * @return resourcePath - path to resource URI of Comic
     */
    public String getResourcePath() {
        return resourcePath;
    }
}
