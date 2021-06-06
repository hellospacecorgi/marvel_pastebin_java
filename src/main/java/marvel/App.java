package marvel;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import marvel.model.*;
import marvel.model.input.InputModel;
import marvel.model.input.OfflineMarvelModel;
import marvel.model.input.OnlineMarvelModel;
import marvel.model.output.OfflinePastebinModel;
import marvel.model.output.OnlinePastebinModel;
import marvel.model.output.OutputModel;

/**
 * First class called when application starts.
 *
 */
public class App extends Application {
    /**
     * Whether offline version is requested for input API
     */
    static boolean offlineInput = false;
    /**
     * Whether offline version is requested for output API
     */
    static boolean offlineOutput = false;
    /**
     * Path to configuration file containing user's API developer keys
     */
    static String configFilePath = "./src/main/resources/marvel/KeyConfig.json";
    /**
     * Model to be used by Presenter to retrieve and post data to APIs
     */
    ModelFacade model;
    /**
     * A version of InputModel that sends requests to input API
     */
    InputModel input;
    /**
     * A version of OutputModel that sends requests to output API
     */
    OutputModel output;

    /**
     * The application main method.
     *
     * <p>Allow 2 command line arguments in sequence to specifiy which version of input and output API to use</p>
     *
     * <p>For example, "online offline" will run application with online input API and offline output API.</p>
     *
     * @param args - command line arguments
     */
    public static void main(String[] args) {

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

    /**
     *  Starts the JavaFX application
     *
     *  <p>Initialises sub model based on command line arguments given.</p>
     *
     *  <p>Initialise model with sub models and path to configuration file.</p>
     *
     *  <p>Sets the scene to Main</p>
     *
     * @param stage
     * @throws Exception
     */
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
