package marvel;

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