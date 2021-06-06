package marvel.model.character;

/**
 * Represents events data including name of event and path to resource URI.
 *
 * @see CharacterInfo
 */
public class Event {
    /**
     * Canonical name of the event
     */
    private String name;
    /**
     * Path to individual event resource
     */
    private String resourcePath;
    /**
     * Constructor for creating a Event.
     * @param name The name of the event
     * @param resourcePath Path to individual event resource
     */
    public Event(String name, String resourcePath) {
        this.resourcePath = resourcePath;
        this.name = name;
    }
    /**
     * Getter for name of Event
     * @return name - String, name of the event
     */
    public String getName() {
        return name;
    }

    /**
     * Getter for path to resource URI of Event
     * @return resourcePath - path to resource URI of Event
     */
    public String getResourcePath() {
        return resourcePath;
    }
}
