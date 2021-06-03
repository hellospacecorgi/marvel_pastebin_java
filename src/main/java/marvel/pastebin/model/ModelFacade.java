package marvel.pastebin.model;

import javafx.collections.ObservableList;

import java.util.List;
import java.util.Map;

public interface ModelFacade {
    //Login
    public List<String> createAccount(String username);
    public boolean authenticate(String username, String token);
    public void setInfo(String username, String token);
    public String getInfo();

}
