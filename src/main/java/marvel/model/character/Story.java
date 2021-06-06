package marvel.model.character;
/**
 * Represents story data including name of story and path to resource URI.
 *
 * @see CharacterInfo
 */
public class Story {
    /**
     * Canonical name of the series
     */
    private String name;
    /**
     * The type of the story (interior or cover)
     */
    private String type;
    /**
     * Path to individual series resource
     */
    private String resourcePath;
    /**
     * Constructor for creating a Story.
     * @param name The name of the story
     * @param type The type of the story (interior or cover)
     * @param resourcePath Path to individual story resource
     */
    public Story( String name, String type, String resourcePath) {
        this.resourcePath = resourcePath;
        this.name = name;
        this.type = type;
    }
    /**
     * Getter for name of Story
     * @return name - String, name of the Story
     */
    public String getName() {
        return name;
    }

    /**
     * Getter for type of Story
     * @return type - Type of the story (interior or cover)
     */
    public String getType() {
        return type;
    }

    /**
     * Getter for path to resource URI of Story
     * @return resourcePath - path to resource URI of Story
     */
    public String getResourcePath() {
        return resourcePath;
    }
}
