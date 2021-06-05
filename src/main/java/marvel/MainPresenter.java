package marvel;

import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import marvel.model.ModelFacade;

import java.io.IOException;

/**
 * MainPresenter class observes the main view (Main.fxml),
 * triggers Model operations upon user events,
 * and ask corresponding view components to update accordingly via manipulating FXML attributes.
 *
 * @version 1.0.0
 * @see View
 * @see ViewSwitcher
 */
public class MainPresenter {
    /**
     *  Takes in user input to generate search enquiry for character information.
     *  Expects string input.
     */
    @FXML
    TextField characterName;

    /**
     *  Represents the lower left panel where response messages are printed.
     */
    @FXML
    TextArea message;

    @FXML
    TableView urlTable;

    @FXML
    TableView centerTable;

    ModelFacade model;

    public MainPresenter(ModelFacade model){
        this.model = model;
    }

//    @FXML
//    public void initialize(){
//
//    }

    /**
     * Triggered by 'Search Character' button.
     * Expects textfield to have string value input.
     * @throws IOException
     */
    @FXML
    public void onSearch() throws IOException {
        model.getCharacterInfo(characterName.getText());
    }
}
