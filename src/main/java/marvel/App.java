package marvel;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import marvel.model.*;
import marvel.model.input.InputModel;
import marvel.model.input.OfflineMarvelModel;
import marvel.model.input.OnlineMarvelModel;

/**
 * First class called when application starts.
 *
 */
public class App extends Application {
    static boolean offlineInput = false;
    static boolean offlineOutput = false;
    static String configFilePath = "./src/main/resources/marvel/KeyConfig.json";

    ModelFacade model;
    InputModel input;
    OutputModel output;

    public String getGreeting() {
        return "Hello world.";
    }

    public static void main(String[] args) {

        System.out.println(new App().getGreeting());
        if(args.length < 1){
            System.out.println("Give --args=\"offline\" to switch to offline mode. \nRunning online mode by default.");
        } else {
            if(args[0].equals("offline")) {
                offlineInput = true;
                System.out.println("[INPUT API] Running offline version.");
            } else if (args[0].equals("online")){
                offlineInput = false;
                System.out.println("[INPUT API] Running online version.");
            }

            if(args[1].equals("offline")) {
                offlineOutput = true;
                System.out.println("[OUTPUT API] Running offline version.");
            } else if (args[1].equals("online")){
                offlineOutput = false;
                System.out.println("[OUTPUT API] Running online version.");
            }
        }
        //calls start()
        launch(args);
    }


    @Override
    public void start(Stage stage) throws Exception {

        Scene scene = new Scene(new Pane());

        if(offlineInput){
            input = new OfflineMarvelModel();
        } else {
            input = new OnlineMarvelModel();
        }

        if(offlineOutput){
            output = new OfflinePastebinModel();
        } else {
            output = new OnlinePastebinModel();
        }


        model = new ModelImpl(input, output, configFilePath);

        ViewSwitcher.setModel(model);
        ViewSwitcher.setScene(scene);
        ViewSwitcher.switchTo(View.MAIN);

        stage.setScene(scene);
        stage.setTitle("Marvel Characters");
        stage.show();


    }
}
