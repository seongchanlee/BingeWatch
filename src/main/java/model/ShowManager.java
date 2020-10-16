package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public enum ShowManager {
    INSTANCE;

    private Map<String, Show> showMap;

    private ShowManager() {
        this.showMap = new HashMap<>();
    }

    public ShowManager getInstance() {
        return INSTANCE;
    }

    public void addShow(String newShowName) {
        if (!showMap.containsKey(newShowName)) {
            Show newShow = new Show(newShowName);
            showMap.put(newShowName, newShow);
        }
    }

    public void addFillersForGivenShow(String showName, int start, int end) {
        Show showObj = showMap.get(showName);
        showObj.addFillers(start, end);
    }

    public boolean containsShow(String showName) {
        return showMap.containsKey(showName);
    }

    public void removeShow(String showName) {
        showMap.remove(showName);
    }

    public void updateGivenEpisode(String showName, int currEpisode) {
        Show showObj = showMap.get(showName);
        if (showObj != null) {
            showObj.updateCurrEpisode(currEpisode);
        }
    }

    public int getNextEpisodeForGivenShow(String showName) {
        Show showObj = showMap.get(showName);
        if (showObj != null) {
            return showObj.getNextEpisode();
        } else {
            return -1;
        }
    }

    public List<Show> getShows() {
        return new ArrayList<>(showMap.values());
    }
}
