package fr.unilim.iut.enigmagame;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.os.Bundle;
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
        tab[1] = "PREMIER PARAMETRE";
        tab[2] = "DEUXIEME PARAMETRE";
        tab[3] = "TROISIEME PARAMETRE";

        adapter = new ArrayAdapter<String>(this,android.R.layout.simple_selectable_list_item,android.R.id.text1,tab);

        liste.setAdapter(adapter);
    }
}
