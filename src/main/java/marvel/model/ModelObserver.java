package marvel.model;

/**
 * Interface for ModelObserver methods to perform actions based on ModelFacade notifications.
 */
public interface ModelObserver {
    /**
     * Performs action upon knowing CharacterInfo is built in model
     */
    public void updateCharacterInfo();

    /**
     * Performs action upon knowing paste report URL is obtained in model
     */
    public void updateReportUrl();

    /**
     * Performs action upon knowing the searched name list is updated in model
     */
    public void updateSearchedList();
}
