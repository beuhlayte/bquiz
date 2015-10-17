package com.belette.bquiz;

/**
 * Created by jordan on 30/09/2015.
 */
public class Reponses {
    private int idReponses;
    private String reponse;
    private int question;
    private String dateModif;

    public Reponses() {}

    public Reponses(int idq,String rep) {
        question = idq;
        reponse = rep;
    }
    public int getIdReponses() { return idReponses; }
    public void setIdReponses(int id) { idReponses = id; }
    public String getReponse() { return reponse; }
    public void setReponse(String rep) {
        reponse = rep;
    }
    public int getQuestions() {
        return question;
    }
    public void setQuestions(int id) {
        question = id;
    }
    public String getDateModif() {
        return dateModif;
    }
    public void setDateModif(String date) { dateModif = date; }
}
