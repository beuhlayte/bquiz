package com.belette.bquiz;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by jordan on 04/10/2015.
 */
public class QuizBDD {
    private static final int VERSIONBDD = 1;
    private static final String NOMBDD = "bquiz.db";

    public static final String TABLE_THEMES = "Themes";
    public static final String COL_IDTHEMES = "idThemes";
    public static final int NUMCOLIDTHEMES = 0;
    public static final String COL_TTHEME = "theme";
    public static final int NUMCOLTHEME = 1;

    public static final String TABLE_QUESTIONS = "Questions";
    public static final String COL_IDQUESTIONS = "idQuestions";
    public static final int NUMCOLIDQUESTIONS = 0;
    public static final String COL_QQUESTION = "question";
    public static final int NUMCOLQQUESTION = 1;
    public static final String COL_QTHEME = "theme";
    public static final int NUMCOLQTHEME = 2;
    public static final String COL_QDATEMODIF = "dateModif";
    public static final int NUMCOLQDATEMODIF = 3;

    public static final String TABLE_REPONSES = "Reponses";
    public static final String COL_IDREPONSES = "idReponses";
    public static final int NUMCOLIDREPONSES = 0;
    public static final String COL_REPONSE = "reponse";
    public static final int NUMCOLREPONSE = 1;
    public static final String COL_RQUESTION = "question";
    public static final int NUMCOLRQUESTION = 2;
    public static final String COL_RDATEMODIF = "dateModif";
    public static final int NUMCOLRDATEMODIF = 3;

    public static final String TABLE_SYNCHRO = "Synchronisations";
    public static final String COL_IDSYNCHRO = "idSynchronisation";
    public static final String COL_DATESYNCHRO = "dateSynchronisation";

    private SQLiteDatabase bdd;

    private MaBaseSQLite maBaseSQLite;
    private int nbQuestions;

    public QuizBDD(Context context) {
        maBaseSQLite = new MaBaseSQLite(context, NOMBDD, null, VERSIONBDD);
        open();
    }

    public void open() {
        try {
            bdd = maBaseSQLite.getWritableDatabase();
        }
        catch (Exception e)
        {
            Log.e("QuizBDD", e.toString());
        }
    }

    public void close() {
        bdd.close();
    }

    /**
     * Fonction qui insère ou met à jour une question si l'ID existe déjà
     * @param questions
     * @return
     */
    public long insertOrUpdateQuestion(Questions questions) {
        ContentValues values = new ContentValues();
        values.put(COL_IDQUESTIONS, questions.getIdQuestion());
        values.put(COL_QQUESTION, questions.getQuestion());
        values.put(COL_QTHEME, questions.getTheme());
        values.put(COL_QDATEMODIF, questions.getDateModif());
        return bdd.replace(TABLE_QUESTIONS, null, values);
    }

    /**
     * Fonction qui insère ou met à jour une réponse si l'ID existe déjà
     * @param reponses
     * @return
     */
    public long insertOrUpdateReponse(Reponses reponses) {
        ContentValues values = new ContentValues();
        values.put(COL_IDREPONSES, reponses.getIdReponses());
        values.put(COL_REPONSE, reponses.getReponse());
        values.put(COL_RQUESTION, reponses.getQuestions());
        values.put(COL_RDATEMODIF, reponses.getDateModif());
        return bdd.replace(TABLE_REPONSES, null, values);
    }

    /**
     * Fonction qui insère ou met à jour un thème si l'ID existe déjà
     * @param themes
     * @return
     */
    public long insertOrUpdateTheme(Themes themes) {
        ContentValues values = new ContentValues();
        values.put(COL_IDTHEMES, themes.getIdThemes());
        values.put(COL_TTHEME, themes.getTheme());
        return bdd.replace(TABLE_THEMES, null, values);
    }

    public long UpdateDateSynchro(String date) {
        // On ne garde qu'une ligne dans la table synchro.
        // ID : 1
        // Date : Date courante

        ContentValues values = new ContentValues();
        values.put(COL_DATESYNCHRO, date);
        values.put(COL_IDSYNCHRO, 1);
        return bdd.replace(TABLE_SYNCHRO, null, values);
    }

    /**
     * Sélection de tous les thèmes
     * @return
     */
    public List<Themes> GetThemes() {
        List<Themes> themeList = new ArrayList<Themes>();
        // Select All Query
        String selectQuery = "SELECT  idThemes, theme FROM Themes";

        Cursor cursor = bdd.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Themes theme = new Themes();
                theme.setIdThemes(Integer.parseInt(cursor.getString(0)));
                theme.setTheme(cursor.getString(1));
                // Adding contact to list
                themeList.add(theme);
            } while (cursor.moveToNext());
        }
        cursor.close();
        // return contact list
        return themeList;
    }

    /**
     * Récupération de la date de la dernière synchronisation dans la table qui ne contient que ça
     * @return
     */
    public String GetDateSynchro() {
        String date = "";
        // Select All Query
        String selectQuery = "SELECT dateSynchronisation from " + TABLE_SYNCHRO;

        Cursor cursor = bdd.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            date = cursor.getString(0);
            cursor.close();
        }
        // return contact list
        return date;
    }
    public Questions questionAleatoire(int alea, int th) {
        String requete = "SELECT idQuestions, question, theme, dateModif FROM Questions WHERE theme = '" + th + "' ORDER BY random() LIMIT 1 ;";
        Cursor c = bdd.rawQuery(requete,null);
        //Cursor c = bdd.query(TABLE_QUESTIONS, new String[] { COL_IDQUESTIONS, COL_QQUESTION, COL_QTHEME, COL_QDATEMODIF }, COL_IDQUESTIONS + " LIKE " + alea, null, null, null, null,null);
        return cursorToQuestions(c);
    }

    public Questions cursorToQuestions(Cursor cursor) {
        if (cursor.getCount() == 0) {
            return null;
        }
        cursor.moveToFirst();
        Questions questions = new Questions();
        questions.setIdQuestion(cursor.getInt(NUMCOLIDQUESTIONS));
        questions.setQuestion(cursor.getString((NUMCOLQQUESTION)));
        questions.setTheme(cursor.getInt(NUMCOLQTHEME));
        questions.setDateModif(cursor.getString(NUMCOLQDATEMODIF));
        return questions;
    }

    public int idThemes(String th) {
        String requete = "SELECT idThemes FROM Themes WHERE theme ='"+ th +"';";
        Cursor c = bdd.rawQuery(requete,null);
        if (c.getCount() == 0) {
            c.close();
            return -1;
        }
        c.moveToFirst();
        return c.getInt(0);
    }

    public int nbQuestion(int th) {
        String requete = "SELECT count(*) FROM Questions WHERE theme = "+ th +";";
        Cursor c = bdd.rawQuery(requete,null);
        if (c.getCount() == 0) {
            c.close();
            return -1;
        }
        c.moveToFirst();
        return c.getInt(0);
    }

    public List<String> reponsesQalea(int idq) {
        List<String> reponses = new ArrayList();
        String requete = "SELECT reponse FROM Reponses WHERE question = " + idq;
        Cursor c = bdd.rawQuery(requete, null);
        if (c.getCount() > 0) {
            c.moveToFirst();
            do {
                reponses.add(c.getString(0).toLowerCase());
            } while (c.moveToNext());
            c.close();
        }
        return reponses;
    }


    private Map<String, String> mapAccents;
    public void initMapAccents() {
        mapAccents = new HashMap<String, String>();
        mapAccents.put("é", "e");
        mapAccents.put("è", "e");
        mapAccents.put("ë", "e");
        mapAccents.put("œ", "oe");
        mapAccents.put("à", "a");
        mapAccents.put("â", "a");
        mapAccents.put("ô", "o");
        mapAccents.put("ò", "o");
        mapAccents.put("í", "i");
    }

    public Boolean IsReponseQuestion(int idquestion, String reponse) {
        List<String> reponses = new ArrayList();
        reponse = "'" + reponse + "'";
        String requete = "SELECT reponse FROM Reponses WHERE question = " + idquestion + " and " + replaceAccents("reponse") + " like " + replaceAccents(reponse);
        Cursor c = bdd.rawQuery(requete, null);
        return  (c.getCount() > 0);
    }

    public String replaceAccents(String word)
    {
        if (mapAccents == null) {
            initMapAccents();
        }
        String ret = "LOWER(" + word + ")";
        for(Map.Entry<String, String> entry : mapAccents.entrySet() )
        {
            ret = "replace(" + ret + ",'" + entry.getKey() + "','" + entry.getValue() + "')";
        }
        return ret;
    }
}