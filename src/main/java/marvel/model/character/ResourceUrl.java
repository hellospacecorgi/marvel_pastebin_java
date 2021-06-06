package marvel.model.character;
/**
 * Represents events data including type and URL for public website resource.
 *
 * @see CharacterInfo
 */
public class ResourceUrl {
    /**
     * Text identifier for URL
     */
    private String type;
    /**
     * A full URL (including scheme, domain, and path)
     */
    private String url;

    /**
     * Constructor for creating a ResourceUrl
     *
     * @param type Text identifier for URL
     * @param url A full URL (including scheme, domain, and path)
     */
    public ResourceUrl(String type, String url) {
        this.type = type;
        this.url = url;
    }

    /**
     * Getter for getting type of ResourceUrl
     * @return type - Text identifier for URL
     */
    public String getType() {
        return type;
    }

    /**
     * Getter for getting full URL for ResrouceUrl
     * @return url - A full URL (including scheme, domain, and path)
     */
    public String getUrl() {
        return url;
    }



}
