package com.belette.bquiz;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.view.View;


public class ActiviteJouer extends AppCompatActivity {

    String vue = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activite_jouer);

        final Button boutonSurvie = (Button) (findViewById(R.id.bSurvie));
        boutonSurvie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vue = "Survie";
                ouvrirThemes();
            }
        });
        final Button boutonDetente = (Button) (findViewById(R.id.bDetente));
        boutonDetente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vue = "Detente";
                ouvrirThemes();
            };
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_activite_jouer, menu);
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

    public void ouvrirThemes() {
        Intent themes = new Intent(ActiviteJouer.this,ActiviteThemes.class);
        themes.putExtra("Type", vue);
        startActivity(themes);
    }
}
