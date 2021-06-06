package marvel;

/**
 * Enum class for mapping views to a .fxml file.
 *
 * Simple getters and setters for retrieving value.
 *
 * @see App
 * @see ViewSwitcher
 */
public enum View {
    /**
     * Enum values that maps to .fxml file names
     */
    MAIN("Main.fxml");
    /**
     * The .fxml file name the current view maps to
     */
    private String fileName;

    /**
     * Constructor for View
     * @param fileName - A .fxml file name
     */
    View(String fileName){
        this.fileName = fileName;
    }

    /**
     * Getter for .fxml file name of View
     * @return String - .fxml file name of View
     */
    public String getFileName(){
        return fileName;
    }
}