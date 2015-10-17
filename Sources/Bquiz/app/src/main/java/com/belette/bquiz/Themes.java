package com.belette.bquiz;

/**
 * Created by jordan on 29/09/2015.
 */
public class Themes {
    private int idThemes;
    private String theme;

    public Themes() {}
    public Themes(int id, String t) {
        idThemes = id;
        theme = t;
    }
    public int getIdThemes() {
        return idThemes;
    }
    public void setIdThemes(int id) {
        idThemes = id;
    }
    public String getTheme() {
        return theme;
    }
    public void setTheme(String t) {
        theme = t;
    }
}
