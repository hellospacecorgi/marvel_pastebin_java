package marvel;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
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

import java.io.IOException;

/**
 * Entry point for JavaFX application. Set up configurations on application set up.
 *
 * <p>Set up model with sub model versions as specified by command line arguments, set up application stage</p>
 *
 * @see Application
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
     * The application main method.
     *
     * <p>Allow 2 command line arguments in sequence to specify which version of input and output API to use.</p>
     *
     * <p>For example, "online offline" will run application with online input API and offline output API.</p>
     *
     * <p>If no arguments are specified, will run application using online models by default.</p>
     *
     * @param args command line arguments
     */
    public static void main(String[] args) {

        if(args.length < 1){
            System.out.println("Give --args=\"offline offline\" to switch to offline mode.\nRunning online mode by default.");
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

        launch(args);
    }

    /**
     *  Starts the JavaFX application.
     *
     *  <p>Sets up key components of MVP pattern for data-presentation separation of the application.</p>
     *
     *  <p>Initialise a ModelImpl model object as a ModelFacade with sub models based on command line arguments given and path to keys configuration file.</p>
     *
     *  <p>Sets scene from Main.fxml and sets a MainView object as its JavaFX Controller class</p>
     *
     *  <p>Initialise a MainPresenter presenter object with ModelFacade and MainView objects</p>
     *
     *  <p>Sets the scene to stage</p>
     *
     * @param stage primary stage for the application
     * @throws IOException if file to Main view cannot be loaded by FXMLLoader
     */
    @Override
    public void start(Stage stage) throws Exception {

        Scene scene = new Scene(new Pane());

        OutputModel output;
        InputModel input;
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

        ConfigHandler config = new ConfigHandler(configFilePath);
        ModelFacade model = new ModelImpl(input, output, config);
        MainView view = new MainView();
        MainPresenter presenter = new MainPresenter(model, view);

        Parent root = null;
        try{
            FXMLLoader viewLoader = new FXMLLoader(App.class.getResource("Main.fxml"));
            viewLoader.setController(view);
            root = viewLoader.load();
            scene.setRoot(root);

        } catch (IOException e){
            e.printStackTrace();
        }

        stage.setScene(scene);
        stage.setTitle("Marvel Characters");
        stage.show();

    }
}
