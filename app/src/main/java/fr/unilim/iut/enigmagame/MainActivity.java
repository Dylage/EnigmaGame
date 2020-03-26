package fr.unilim.iut.enigmagame;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private Button bouton;
    private TextView question;
    private EditText saisie;
    private Button boutonParametre;
    private Parametre param;
    private EnigmeBDD db;
    private Enigme enigmeActuelle;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bouton = (Button) findViewById(R.id.bouton);
        question = (TextView) findViewById(R.id.question);
        saisie = (EditText) findViewById(R.id.saisie);


        initDb();


        this.db.open();
        // this.enigmeActuelle = db.getEnigmeWithQuestion("Quelle est la couleur du cheval blanc d'Henry IV ?");
        //this.enigmeActuelle = db.getEnigmeWithId(1);
        this.enigmeActuelle = db.getRandomEnigme();

        this.question.setText(this.enigmeActuelle.getEnigme());
        this.saisie.setHint("Saisissez votre réponse");

        bouton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                if (enigmeActuelle.getReponse().equals(saisie.getText().toString())){
                    question.setText("BRAVO !" + "\n" +
                            "Essais " +  db.getNbEssaisEnigme(enigmeActuelle.getId()) + "...");
                    db.close();
                }else{
                    db.nouvelEssai(enigmeActuelle.getId());
                }
            }
        });

        boutonParametre = (Button) findViewById(R.id.boutonParametre);
        boutonParametre.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent intent = new Intent(MainActivity.this, Parametre.class);
                startActivity(intent);
            }
        });

    }

    /**
     * Insère les énigmes dans la DB
     */
    private void initDb(){
        this.db = new EnigmeBDD(this);
        this.db.destroy();
        this.db.init();

        ArrayList listEnigmes = new ArrayList();

        Enigme enigme = new Enigme("Quelle est la couleur du cheval blanc d'Henry IV ?", "blanc");
        Enigme enigme1 = new Enigme("Qu'est ce qui est jaune et qui attend ?", "Jonathan");

        listEnigmes.add(enigme);
        listEnigmes.add(enigme1);

        this.db.open();
        this.db.insertEnigme(listEnigmes);
        this.db.close();
    }

    @Override
    protected void onStop() {
        super.onStop();
        this.db.destroy();

    }
}
