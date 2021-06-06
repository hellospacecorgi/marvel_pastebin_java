package marvel.model.character;

/**
 * Represents thumbnail image data including image path and extension.
 * @see CharacterInfo
 */
public class Thumbnail {
    /**
     * The directory path to the image
     */
    private String path;
    /**
     * The file extension for the image
     */
    private String extension;

    /**
     * Constructor for creating a Thumbnail
     *
     * @param path The directory path to the image
     * @param extension The file extension for the image
     */
    public Thumbnail(String path, String extension){
        this.path = path;
        this.extension = extension;
    }

    /**
     * Getter for directory path of Thumbnail
     * @return path - directory path to the image
     */
    public String getPath() {
        return path;
    }

    /**
     * Getter for file extension of Thumbnail
     * @return extension - file extension of image
     */
    public String getExtension() {
        return extension;
    }

}
