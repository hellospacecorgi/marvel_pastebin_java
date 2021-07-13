package marvel;

/**
 * Interface for ViewObserver methods to listen for UI events.
 */
public interface ViewObserver {
    /**
     * Performs operation knowing the Search Character button was clicked
     * @param name Text inside text field when event happened
     */
    public void onSearch(String name);

    /**
     * Performs operation upon knowing the Comics button was clicked
     */
    public void onComics();

    /**
     * Performs operation knowing the Stories button was clicked
     */
    public void onStories();
    /**
     * Performs operation knowing the Events button was clicked
     */
    public void onEvents();
    /**
     * Performs operation knowing the Series button was clicked
     */
    public void onSeries();
    /**
     * Performs operation knowing the URLs button was clicked
     */
    public void onUrls();
    /**
     * Performs operation knowing the Send Report button was clicked
     */
    public void onSendReport();
    /**
     * Performs operation knowing the Load from cache button was clicked
     */
    public void onLoadFromCache(String name);
    /**
     * Performs operation knowing the index chosen for matching is selected
     *
     * @param index Integer chosen as index in list
     */
    public void onIndexSelected(int index);
}
