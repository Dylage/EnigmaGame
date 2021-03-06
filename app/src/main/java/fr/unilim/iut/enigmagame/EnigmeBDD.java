package fr.unilim.iut.enigmagame;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.List;

public class EnigmeBDD {
    private static final int VERSION_BDD = 1;
    private static final String NOM_BDD = "enigmes.db";
    public static final String TABLE_ENIGMES = "table_enigmes";
    private static final String COL_ID = "ID";
    private static final int NUM_COL_ID = 0;
    private static final String COL_ENIGME = "ENIGME";
    private static final int NUM_COL_ENIGME = 1;
    private static final String COL_SOLUTION = "REPONSE";
    private static final int NUM_COL_SOLUTION = 2;
    private static final String COL_SCORE = "NB_ESSAIS";
    private static final int NUM_COL_SCORE = 3;
    private SQLiteDatabase bdd;
    private BaseSqlLite maBaseSQLite;

    public EnigmeBDD(Context context) {

        maBaseSQLite = new BaseSqlLite(context, NOM_BDD, null, VERSION_BDD);
    }

    public void open() {

        bdd = maBaseSQLite.getWritableDatabase();
    }

    public void close() {

        bdd.close();
    }

    public SQLiteDatabase getBDD() {
        return bdd;
    }

    /**
     * Méthode pour insérer une énigme dans la DB
     * @param enigme
     * @return
     */
    public long insertEnigme(Enigme enigme) {

        ContentValues values = new ContentValues();

        values.put(COL_ENIGME, enigme.getEnigme());
        values.put(COL_SOLUTION, enigme.getReponse());
        values.put(COL_SCORE, 0);

        return bdd.insert(TABLE_ENIGMES, null, values);
    }

    /**
     * Méthode pour insérer une liste d'énigme dans la DB
     * @param enigmes
     */
    public void insertEnigme(List<Enigme> enigmes) {

        for (Enigme enigme: enigmes) {
            this.insertEnigme(enigme);
        }

    }

    /**
     * Méthode pour modifier une énigme dans la DB
     * @param id : id de l'énigme dans la DB
     * @param enigme : on prend sa question, sa solution ET son score
     * @return
     */
    public int updateEnigme(int id, Enigme enigme) {

        ContentValues values = new ContentValues();
        values.put(COL_ENIGME, enigme.getEnigme());
        values.put(COL_SOLUTION, enigme.getScore());
        values.put(COL_SCORE, enigme.getReponse());
        return bdd.update(TABLE_ENIGMES, values, COL_ID + " = " + id, null);
    }

    /**
     * Incrémente de 1 le nombre d'essais sur l'énigme
     * @param id : id de l'énigme dans la DB
     * @return
     */
    public int nouvelEssai(int id) {

        ContentValues values = new ContentValues();
        values.put(COL_SCORE, this.getNbEssaisEnigme(id) + 1);

        return bdd.update(TABLE_ENIGMES, values, COL_ID + " = " + id, null);
    }

    /**
     * Méthode pour obtenir le nombre d'essais d'une énigme
     * @param id : identifiant d'une énigme
     * @return son nombre d'essais
     */
    public int getNbEssaisEnigme(int id){
        Cursor c = bdd.query(TABLE_ENIGMES, new String[]{COL_ID, COL_ENIGME, COL_SOLUTION, COL_SCORE},
                COL_ID + " LIKE \"" + id + "\"", null, null, null, null);
        return cursorToEnigme(c).getScore();
    }

    /**
     * Méthode pour supprimer une énigme de la DB
     * @param id
     * @return
     */
    public int removeEnigmeWithID(int id) {

        return bdd.delete(TABLE_ENIGMES, COL_ID + " = " + id, null);
    }

    /**
     * Méthode pour obtenir une énigme selon sa question
     * @param question : chaine de caractère représentant la question posée
     * @return : l'énigme complète
     */
    public Enigme getEnigmeWithQuestion(String question) {

        Cursor c = bdd.query(TABLE_ENIGMES, new String[]{COL_ID, COL_ENIGME, COL_SOLUTION, COL_SCORE},
                COL_SOLUTION + " LIKE \"" + question + "\"", null, null, null, null);
        return cursorToEnigme(c);
    }

    /**
     * Méthode pour obtenir une énigme selon son id dans la DB
     * @param id : identifiant de l'énigme
     * @return : l'énigme complète
     */
    public Enigme getEnigmeWithId(int id) {
        Cursor c = bdd.query(TABLE_ENIGMES, new String[]{COL_ID, COL_ENIGME, COL_SOLUTION, COL_SCORE},
                COL_ID + " LIKE \"" + id + "\"", null, null, null, null);
        return cursorToEnigme(c);
    }

    /**
     * Méthode pour obtenir une énigme aléatoirement
     * @return
     */
    public Enigme getRandomEnigme(){
        // On récupère le nombre d'énigmes dans la table
        Cursor mCount= bdd.rawQuery("select count(*) from TABLE_ENIGMES", null);
        mCount.moveToFirst();
        int count = mCount.getInt(0);
        mCount.close();

        // On génère un aléatoire entre 1 et ce nombre (les auto increment commencent à 1)
        int random = 1 + (int)(Math.random() * ((count - 1) + 1));

        // On récupère l'énigme dont l'id est ce nombre aléatoire
        Cursor c = bdd.query(TABLE_ENIGMES, new String[]{COL_ID, COL_ENIGME, COL_SOLUTION, COL_SCORE},
                COL_ID + " LIKE \"" + random + "\"", null, null, null, null);
        return cursorToEnigme(c);
    }

    /**
     * Méthode pour créer une énigme selon le curseur dans la DB (et les informations contenues)
     * @param c : curseur sur une ligne de la DB
     * @return : l'énigme complète
     */
    private Enigme cursorToEnigme(Cursor c) {
        if (c.getCount() == 0)
            return null;
        c.moveToFirst();
        Enigme enigme = new Enigme();
        //on lui affecte toutes les infos grâce aux infos contenues dans le Cursor
        enigme.setId(c.getInt(NUM_COL_ID));
        enigme.setEnigme(c.getString(NUM_COL_ENIGME));
        enigme.setReponse(c.getString(NUM_COL_SOLUTION));
        enigme.setScore(c.getInt(NUM_COL_SCORE));
        //On ferme le cursor
        c.close();
        //On retourne le livre
        return enigme;
    }
}