package com.belette.bquiz;

/**
 * Created by jordan on 29/09/2015.
 */
public class Questions {
    private int idQuestions;
    private String question;
    private int theme;
    private String dateModif;

    public Questions() {  }
    public int getIdQuestion() { return idQuestions; }
    public void setIdQuestion(int id) { idQuestions = id; }
    public String getQuestion() { return question; }
    public void setQuestion(String q) { question = q; }
    public int getTheme() { return theme; }
    public void setTheme(int t) { theme = t; }
    public String getDateModif() {
        return dateModif;
    }
    public void setDateModif(String date) { dateModif = date; }

    public static void UpdateOrInsertQuestion(int id, String question, String datemodif)
    {

    }
}
