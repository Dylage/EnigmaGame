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
    private Button renew;
    private TextView question;
    private TextView msg;
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
        msg = (TextView) findViewById(R.id.msg);
        saisie = (EditText) findViewById(R.id.saisie);
        boutonParametre = (Button) findViewById(R.id.boutonParametre);
        renew = (Button) findViewById(R.id.boutonRenew);


        initDb();

        int oldId = getIntent().getIntExtra("oldId", -1);


        this.db.open();
        // Pour changer d'énigme
        if (-1 != oldId){
            Enigme buffer = db.getRandomEnigme();

            while(buffer.getId() == oldId){
                buffer = db.getRandomEnigme();
            }
            this.enigmeActuelle = buffer;
        }else{
            this.enigmeActuelle = db.getRandomEnigme();
        }

        this.question.setText(this.enigmeActuelle.getEnigme());
        this.msg.setText("");
        this.saisie.setHint("Saisissez votre réponse");

        bouton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                db.open();
                if (enigmeActuelle.getReponse().equals(saisie.getText().toString())){
                    msg.setText("BRAVO !" + "\n" +
                            "Essais " +  db.getNbEssaisEnigme(enigmeActuelle.getId()) + "...");
                    db.close();
                }else{
                    msg.setText("C'est non...");
                    db.nouvelEssai(enigmeActuelle.getId());
                    db.close();
                }
            }
        });

        boutonParametre.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent intent = new Intent(MainActivity.this, Parametre.class);
                startActivity(intent);
            }
        });

        /**
         * Au clique, on revient ici et aléatoirement, on peut tomber sur une nouvelle énigme
         */
        renew.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent intent = new Intent(MainActivity.this, MainActivity.class);
                intent.putExtra("oldId", enigmeActuelle.getId());
                startActivity(intent);
            }
        });

    }

    /**
     * Insère les énigmes dans la DB
     */
    private void initDb(){
        this.db = new EnigmeBDD(this);


        ArrayList listEnigmes = new ArrayList();

        listEnigmes.add(new Enigme("Quelle est la couleur du cheval blanc d'Henry IV ?", "blanc"));
        listEnigmes.add(new Enigme("Qu'est ce qui est jaune et qui attend ?", "Jonathan"));
        listEnigmes.add(new Enigme("Qu'est ce que fait une fraise sur un cheval ?", "tagada"));
        listEnigmes.add(new Enigme("Avec quoi est ce qu'on ramasse les papayes ?", "foufourche"));
        listEnigmes.add(new Enigme("Comment appelle t-on un oiseau sur twitter ?", "#oiseau"));
        listEnigmes.add(new Enigme("Quel être, pourvu d'une seule voix, a d'abord quatre jambes le matin, puis deux jambes le midi, et trois jambes le soir ?", "humain"));

        this.db.open();
        this.db.insertEnigme(listEnigmes);
        this.db.close();
    }

}
