package com.belette.bquiz;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.nio.charset.Charset;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

/**
 * Created by jordan on 10/10/2015.
 */
public class SynchroMySQL {

   private static final String URL_QUESTIONS = "http://bquiz.esy.es/verifQuestions.php";
   private static final String URL_REPONSES = "http://bquiz.esy.es/verifReponses.php";
   private static final String URL_THEMES = "http://bquiz.esy.es/verifThemes.php";

    /**
     * Synchroniser de façon asynchrone
     * @param context
     */
    public static void Synchroniser(final Context context) {
        // Lancement de la synchronisation dans un thread aynchrone pour ne pas bloquer l'utilisation
        // de l'appli pendant la synchronisation
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {

                Log.i("SynchroMySQL", "Synchronisation de la base de données");

                QuizBDD bdd = new QuizBDD(context);
                // Récupération de la date de synchro
                String date = bdd.GetDateSynchro();
                bdd.close();

                DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd+HH:mm:ss");
                dateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
                //get current date time with Date()
                Date newdate = new Date();

                Log.i("SynchroMySQL", "Date de dernière synchronisation : " + date);

                boolean synchroOK = false;

                Log.i("SynchroMySQL", "Parsage des thèmes");
                if (ParseThemes(context, date)){

                    Log.i("SynchroMySQL", "Parsage des questions");
                    if (ParseQuestions(context, date)) {

                        Log.i("SynchroMySQL", "Parsage des réponses");
                        if (ParseReponses(context, date)) {
                            Log.i("SynchroMySQL","Parsage des réponses OK");

                            QuizBDD bdd2 = new QuizBDD(context);
                            // MAJ de la date de synchronisation si toutes les MAJ se sont bien passées
                            bdd2.UpdateDateSynchro(dateFormat.format(newdate));
                            bdd2.close();

                            Log.i("SynchroMySQL","MAJ de la date de synchro OK");

                            synchroOK = true;
                        }
                        else {
                            Log.e("SynchroMySQL", "Erreur Parse Réponses");
                        }
                    }
                    else {
                        Log.e("SynchroMySQL", "Erreur Parse Questions");
                    }
                }
                else {
                    Log.e("SynchroMySQL", "Erreur Parse Thème");
                }

                // Message à afficher en fin de synchronisation
                CharSequence text = "" ;
                if (!synchroOK) {
                    text = "Erreur lors de la synchronisation. Vérifiez votre connexion internet.";
                }
                else {
                    text = "Synchronisation OK";
                }
                int duration = Toast.LENGTH_SHORT;

                final CharSequence ToastText = text;

                final Activity activity = (Activity)context;
                activity.runOnUiThread(new Runnable() {
                    public void run() {
                        Toast.makeText(activity, ToastText, Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
        thread.start();
    }

    /**
     * Ajouter la date de dernière synchro à l'URL
     * @param url
     * @param dateSynchro
     * @return
     */
    private static String FormatURLWithParamDate(String url, String dateSynchro) {
        if (!dateSynchro.isEmpty()) {
            url+="?date="+dateSynchro;
        }
        return url;
    }

    /**
     * Récupère la liste des thèmes en JSON
     * et les insère/update dans la base SQLite
     * @param context
     */
    private static boolean ParseThemes(Context context, String dateSynchro)
    {


        // Passage de la date dans l'URL
        String url = FormatURLWithParamDate(URL_THEMES,dateSynchro);

        QuizBDD bdd = new QuizBDD(context);

        // Récupération du JSON avec l'URL
        JSONArray json = readJsonFromUrl(url);
        if (json != null) {
            for (int i = 0; i < json.length(); i++) {
                try {

                    // Parsage du JSON
                    JSONObject row = json.getJSONObject(i);
                    int id = row.getInt(QuizBDD.COL_IDTHEMES);
                    String theme = row.getString(QuizBDD.COL_TTHEME);

                    // Création et insertion du thème
                    Themes t = new Themes();
                    t.setIdThemes(id);
                    t.setTheme(theme);
                    bdd.insertOrUpdateTheme(t);

                } catch (Exception e) {
                    Log.e("SynchroMySQL", e.toString());
                }
            }
        }

        bdd.close();


        // Si json est nul il y a un un problème à la récupération
        return json != null;

    }

    /***
     * Récupère les questions en JSON
     * et les insère/update dans la base SQLite
     * @param context contexte de la BDD
     */
    private static boolean ParseQuestions(Context context, String dateSynchro)
    {
        QuizBDD bdd = new QuizBDD(context);

        // Passage de la date dans l'URL
        String url = FormatURLWithParamDate(URL_QUESTIONS, dateSynchro);

        // Récupération du JSON avec l'URL
        JSONArray json = readJsonFromUrl(url);
        if (json != null) {
            // Parcours des Questions
            for (int i = 0; i < json.length(); i++) {
                try {
                    // Parsage du JSON
                    JSONObject row = json.getJSONObject(i);
                    int id = row.getInt(QuizBDD.COL_IDQUESTIONS);
                    String question = row.getString(QuizBDD.COL_QQUESTION);
                    String datemodif = row.getString(QuizBDD.COL_QDATEMODIF);
                    int theme = row.getInt(QuizBDD.COL_QTHEME);

                    // Création et insertion de la question
                    Questions q = new Questions();
                    q.setIdQuestion(id);
                    q.setQuestion(question);
                    q.setDateModif(datemodif);
                    q.setTheme(theme);

                    bdd.insertOrUpdateQuestion(q);
                }
                catch (Exception e) {
                    Log.e("SynchroMySQL", e.toString());
                }
            }

        }

        bdd.close();

        // Si json est nul il y a un un problème à la récupération
        return json != null;
    }

    /***
     * Récupère les réponses en JSON
     * Et les insère/update dans la base SQLite
     * @param context
     */
    private static boolean ParseReponses(Context context, String dateSynchro)
    {
        QuizBDD bdd = new QuizBDD(context);

        // Récupération de l'URL à partir de la date de synchronisation
        String url = FormatURLWithParamDate(URL_REPONSES,dateSynchro);

        // Récupération du JSON avec l'URL
        JSONArray json = readJsonFromUrl(url);
        if (json != null) {
            // Parcours du JSON
            for (int i = 0; i < json.length(); i++) {
                try {
                    // Parsage du JSON
                    JSONObject row = json.getJSONObject(i);
                    int id = row.getInt(QuizBDD.COL_IDREPONSES);
                    String reponse = row.getString(QuizBDD.COL_REPONSE);
                    String datemodif = row.getString(QuizBDD.COL_RDATEMODIF);
                    int idquestion = row.getInt(QuizBDD.COL_RQUESTION);


                    // Création et insertion des réponses
                    Reponses r = new Reponses();
                    r.setIdReponses(id);
                    r.setQuestions(idquestion);
                    r.setDateModif(datemodif);
                    r.setReponse(reponse);

                    bdd.insertOrUpdateReponse(r);
                }
                catch (Exception e) {
                    Log.e("SynchroMySQL", e.toString());

                }
            }

        }

        bdd.close();

        // Si json est nul il y a un un problème à la récupération
        return json != null;
    }

    /**
     * Récupération d'un JSONArray à partir d'une URL
     * @param url
     * @return
     */
    public static JSONArray readJsonFromUrl(String url) {
        JSONArray json = null;

        try {
           InputStream is = new URL(url).openStream();

           try {
               BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
               String jsonText = readAll(rd);

               if (jsonText.equals("null")) {
                   // Si on reçoit "null" alors il n'y a pas de modifs
                   // On renvoie un array vide
                   json = new JSONArray();
                   Log.i("SynchroMySQL", "Pas de mise à jour");
               }
               else {
                   // Sinon on parse le json
                   json = new JSONArray(jsonText);
               }

           } finally {
               is.close();
           }
       }
        catch(Exception e)
        {
            Log.i("SynchroMysql", e.toString());
        }
        return json;
    }

    /**
     * Lecture du contenu du lien
     * @param rd
     * @return
     * @throws IOException
     */
    private static String readAll(Reader rd) throws IOException {
        StringBuilder sb = new StringBuilder();
        int cp;
        while ((cp = rd.read()) != -1) {
            sb.append((char) cp);
        }
        return sb.toString();
    }

}
