package marvel;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import marvel.model.ModelFacade;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Used for switching between views upon navigation buttons clicked.
 *
 * @see App
 * @see View
 */
public class ViewSwitcher {
    private static Scene scene;
    private static ModelFacade model;
    private static Map<View, Parent> cache = new HashMap<>();
    public static void setScene(Scene scene){
        ViewSwitcher.scene = scene;
    }
    public static void switchTo(View view) throws IOException {
        if (scene == null ){
            System.out.println("No scene or model set");
            return;
        }

        try{
            Parent root = null;
            if(cache.containsKey(view)){
                //view already loaded no need to reload
                System.out.println("Loading from cache");
                root = cache.get(view);
            } else {
                System.out.println("Loading from FXML");

                FXMLLoader viewLoader = new FXMLLoader(ViewSwitcher.class.getResource(view.getFileName()));
                String path = view.getFileName();
                if(path.equals("Main.fxml")) {
                    viewLoader.setController(new MainPresenter(model));
                }
                root = viewLoader.load();

                //cache view to the cache map
                cache.put(view, root);
            }
            scene.setRoot(root);

        } catch (IOException e){
            e.printStackTrace();
        }
    }

    /**
     * Passes model to presenter to coordinate updates.
     * @param model
     */
    public static void setModel(ModelFacade model) {
        ViewSwitcher.model = model;
    }
}
