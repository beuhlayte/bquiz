package com.belette.bquiz;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.content.Intent;
import android.view.View;
import android.widget.Button;

public class ActiviteMenu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activite_menu);

        final Button boutonOption = (Button) findViewById(R.id.bOptions);
        boutonOption.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ouvrirOption();
            }
        });

        final Button boutonJouer = (Button) findViewById(R.id.bJeu);
        boutonJouer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ouvrirJeu();
            }
        });

        final Button boutonSynchro = (Button) findViewById(R.id.bSynchro);
        boutonSynchro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SynchroBDD();
            }
        });
    }
    public void ouvrirOption() {
        Intent option = new Intent(ActiviteMenu.this, ActiviteOption.class);
        startActivity(option);
    }
    public void ouvrirJeu() {
        Intent jeu = new Intent(ActiviteMenu.this, ActiviteJouer.class);
        startActivity(jeu);
    }
    /**
     * Synchronisation de la base SQLite avec la base MySQL
     */
    public void SynchroBDD() {
        SynchroMySQL.Synchroniser(this);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_activite_menu, menu);
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

    public void ecrireQuestion() {

    }
}

