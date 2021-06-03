package marvel.pastebin.model;

import java.util.List;

public class OfflineModel implements ModelFacade{
    @Override
    public List<String> createAccount(String username) {
        return null;
    }

    @Override
    public boolean authenticate(String username, String token) {
        return false;
    }

    @Override
    public void setInfo(String username, String token) {

    }

    @Override
    public String getInfo() {
        return null;
    }
}
