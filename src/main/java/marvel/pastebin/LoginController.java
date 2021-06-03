package marvel.pastebin;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import marvel.pastebin.model.ModelFacade;

import java.io.IOException;
import java.util.List;

public class LoginController {
    @FXML
    TextField username;
    @FXML
    TextField token;
    @FXML
    Label message;
    @FXML
    Text message1;

    @FXML
    ChoiceBox saved_accounts;

    ModelFacade model;

    public LoginController(ModelFacade model){
        this.model = model;
    }

    @FXML
    public void initialize(){
        saved_accounts.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observableValue, Object o, Object t1) {
                username.setText(String.valueOf(t1));
                //token.setText(model.getSavedAccounts().get(String.valueOf(t1)));
                System.out.println(String.valueOf(t1).concat(" selected"));
            }
        });
        /*
        message.setText(model.getServerStatus());
        model.saveAccount("randomcorgi", "61cbb008-c490-4595-a644-b39ff609347d");
        model.saveAccount("username", "token");
        saved_accounts.getItems().add("randomcorgi");
        saved_accounts.getItems().add("username");
        */

    }

    public void onLogin() throws IOException {
        if(model.authenticate(username.getText(), token.getText())){
            model.setInfo(username.getText(), token.getText());
            System.out.println(model.getInfo());
            //ViewSwitcher.switchTo(View.MAIN);
            return;
        }
        /*
        if(!model.isOnline() && model.authenticate(username.getText(), token.getText())){
            message.setText("Offline mode only accepts the default account as valid login");
            return;
        }

         */

        message.setText("Login failed, check if entered correctly?");
    }

    public void onSignUp(){
        List<String> account = model.createAccount(username.getText());
        if(account.size() < 2){
            message.setText("Failed to create account, try another username?");
            return;
        }

        message.setText("Account created - username: ".concat(account.get(0)));
        token.setText(account.get(1));

    }

    public void onSaveDetails(){
        /*
        if(model.saveAccount(username.getText(), token.getText())){
            saved_accounts.getItems().add(username.getText());
            message.setText("Credentials saved but does not guarantee login!!");
        } else {
            message.setText("Cannot create new save - duplicate username");
        }

         */
    }
}
