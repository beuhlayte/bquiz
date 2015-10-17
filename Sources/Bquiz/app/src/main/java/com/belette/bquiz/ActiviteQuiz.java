package com.belette.bquiz;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.List;


public class ActiviteQuiz extends AppCompatActivity {

    String typeJeu;
    String theme;
    int idTheme, nbQuestions;
    Questions question;
    //String reponses[];
    //QuizBDD bdd = new QuizBDD(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        QuizBDD bdd = new QuizBDD(this);
        bdd.open();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activite_quiz);

        //Initialisation
        TextView tTheme = (TextView) (findViewById(R.id.tTheme));
        TextView tNbLignes = (TextView) (findViewById(R.id.tNbLignes));
        Button bOk = (Button)(findViewById(R.id.bOk));
        Button bQsuivante = (Button) (findViewById(R.id.bQSuivante));
        bQsuivante.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nouvelleQuestion();
            }
        });
        bOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                verifReponse();
            }
        });

        int numeroDeLaLigne = 0;
        //On récupère les données de l'activité des thèmes
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            typeJeu = extras.getString("Type");
            theme = extras.getString("Theme");
        }
        idTheme = bdd.idThemes(theme);
        nbQuestions = bdd.nbQuestion(idTheme);

        tTheme.setText(theme);
        tNbLignes.setText(String.valueOf(nbQuestions));
        ecrireQuestions();
        bdd.close();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_activite_quiz, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void ecrireQuestions() {
        int nbAlea = (int)(Math.random()*100000) % nbQuestions;
        QuizBDD bdd = new QuizBDD(this);
        TextView tQuestion = (TextView) (findViewById(R.id.tQuestion));
        Questions nouvelleQuestion = bdd.questionAleatoire(nbAlea, idTheme);
        tQuestion.setText(nouvelleQuestion.getQuestion());
        question = nouvelleQuestion;
        bdd.close();
    }


    public void verifReponse() {
        QuizBDD bdd = new QuizBDD(this);
        List <String> reponses = bdd.reponsesQalea(question.getIdQuestion());
        EditText eReponse = (EditText) (findViewById(R.id.eReponse));
        TextView tVerif = (TextView) (findViewById(R.id.tVerif));
        String reponse = eReponse.getText().toString().toLowerCase();
        int i = 0;
        if (reponses.contains(reponse)) {
            tVerif.setText("Bonne réponse !");
        }
        else {
            tVerif.setText("Dommage ! La réponse était " + reponses.get(0));
        }
        bdd.close();
    }

    public void nouvelleQuestion() {
        ecrireQuestions();
        EditText eReponse = (EditText) (findViewById(R.id.eReponse));
        TextView tVerif = (TextView) (findViewById(R.id.tVerif));
        eReponse.setText("");
        tVerif.setCursorVisible(false);
    }
}