package marvel.pastebin;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import marvel.pastebin.model.ModelFacade;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

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
                if(path.equals("login.fxml")) {
                    viewLoader.setController(new LoginController(model));
                }
                root = viewLoader.load();

                //root = FXMLLoader.load(ViewSwitcher.class.getResource(view.getFileName()));

                //cache view to the cache map
                cache.put(view, root);
            }
            scene.setRoot(root);

        } catch (IOException e){
            e.printStackTrace();
        }
    }

    public static void setModel(ModelFacade model) {
        ViewSwitcher.model = model;
    }
}
