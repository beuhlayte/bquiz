package com.belette.bquiz;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.view.View;


public class ActiviteThemes extends AppCompatActivity {

    String typeJeu = null;
    String theme = new String("");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activite_themes);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            typeJeu = extras.getString("Type");
        }
        final Button anglais = (Button) (findViewById(R.id.bAnglais));
        final Button art = (Button) (findViewById(R.id.bArt));
        final Button bdda = (Button) (findViewById(R.id.bBDDA));
        final Button cinema = (Button) (findViewById(R.id.bCinema));
        final Button geek = (Button) (findViewById(R.id.bGeek));
        final Button divers = (Button) (findViewById(R.id.bDivers));
        final Button geo = (Button) (findViewById(R.id.bGeo));
        final Button general = (Button) (findViewById(R.id.bGeneral));
        final Button histoire = (Button) (findViewById(R.id.bHistoire));
        final Button lettres = (Button) (findViewById(R.id.bLettres));
        final Button musique = (Button) (findViewById(R.id.bMusique));
        final Button nature = (Button) (findViewById(R.id.bNature));
        final Button mythes = (Button) (findViewById(R.id.bMythes));
        final Button sciences = (Button) (findViewById(R.id.bSciences));
        final Button sport = (Button) (findViewById(R.id.bSport));
        anglais.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                theme = "anglais";
                ouvrirQuiz();
            }
        });
        art.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                theme = "art";
                ouvrirQuiz();
            }
        });
        bdda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                theme = "bdda";
                ouvrirQuiz();
            }
        });
        cinema.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                theme = "cinema";
                ouvrirQuiz();
            }
        });
        geek.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                theme = "geek";
                ouvrirQuiz();
            }
        });
        divers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                theme = "divers";
                ouvrirQuiz();
            }
        });
        geo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                theme = "geo";
                ouvrirQuiz();
            }
        });
        general.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                theme = "general";
                ouvrirQuiz();
            }
        });
        histoire.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                theme = "histoire";
                ouvrirQuiz();
            }
        });
        lettres.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                theme = "lettres";
                ouvrirQuiz();
            }
        });
        musique.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                theme = "musique";
                ouvrirQuiz();
            }
        });
        nature.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                theme = "nature";
                ouvrirQuiz();
            }
        });
        mythes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                theme = "mythes";
                ouvrirQuiz();
            }
        });
        sciences.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                theme = "sciences";
                ouvrirQuiz();
            }
        });
        sport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                theme = "sport";
                ouvrirQuiz();
            }
        });
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_activite_themes, menu);
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

    public void ouvrirQuiz() {
        Intent quiz = new Intent(ActiviteThemes.this,ActiviteQuiz.class);
        quiz.putExtra("Type", typeJeu);
        quiz.putExtra("Theme", theme);
        startActivity(quiz);
    }
}
