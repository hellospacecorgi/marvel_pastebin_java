package marvel;

public interface ViewObserver {
    public void onSearch(String name);
    public void onComics();
    public void onStories();
    public void onEvents();
    public void onSeries();
    public void onUrls();
    public void onSendReport();
}
