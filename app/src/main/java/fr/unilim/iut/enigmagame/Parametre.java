package fr.unilim.iut.enigmagame;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class Parametre extends AppCompatActivity {
    ConstraintLayout layout;
    ArrayAdapter<String> adapter;
    ListView liste;
    String[] tab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parametre);
        liste = (ListView) findViewById(R.id.listeParametre);
        tab =  new String[4];
        tab[0] = "PARAMETRES";
        tab[1] = "Définir son nom";
        tab[2] = "Activer / Désactiver l'écran de verrouillage";
        tab[3] = "Scores";

        adapter = new ArrayAdapter<String>(this,android.R.layout.simple_selectable_list_item,android.R.id.text1,tab);

        liste.setAdapter(adapter);

        liste.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Position est la position dans le tableau

                if (id == 4){

                }

                System.out.println(position);
            }

        });
    }
}
