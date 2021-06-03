package marvel.pastebin;

public enum View {
    LOGIN("login.fxml");

    private String fileName;

    View(String fileName){
        this.fileName = fileName;
    }

    public String getFileName(){
        return fileName;
    }
}