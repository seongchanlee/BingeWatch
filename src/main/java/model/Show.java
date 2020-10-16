package model;

import java.util.ArrayList;
import java.util.List;

public class Show {
    private String showName;
    private int currEpisode;
    private List<Integer> fillerEpisodes;

    public Show(String showName) {
        this.showName = showName;
        this.currEpisode = 0;
        fillerEpisodes = new ArrayList<>();
    }

    public void addFillers(int start, int end) {
        for (int i = start; i <= end; i++) {
            fillerEpisodes.add(i);
        }
    }

    public void updateCurrEpisode(int currEpisode) {
        this.currEpisode = currEpisode;
    }

    public String getShowName() {
        return showName;
    }

    public int getNextEpisode() {
        int nextEpisode = currEpisode + 1;

        while (fillerEpisodes.contains(nextEpisode)) {
            nextEpisode++;
        }

        return nextEpisode;
    }

    public int getCurrEpisode() {
        return currEpisode;
    }
}
