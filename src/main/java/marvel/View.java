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
    MAIN("Main.fxml");

    private String fileName;

    View(String fileName){
        this.fileName = fileName;
    }

    public String getFileName(){
        return fileName;
    }
}