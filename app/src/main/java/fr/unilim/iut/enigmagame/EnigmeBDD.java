package fr.unilim.iut.enigmagame;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class EnigmeBDD {
    private static final int VERSION_BDD = 1;
    private static final String NOM_BDD = "enigmes.db";
    private static final String TABLE_ENIGMES = "table_enigmes";
    private static final String COL_ID = "ID";
    private static final int NUM_COL_ID = 0;
    private static final String COL_ENIGME = "ENIGME";
    private static final int NUM_COL_ENIGME = 1;
    private static final String COL_SOLUTION = "REPONSE";
    private static final int NUM_COL_SOLUTION = 2;
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

    public long insertLivre(Enigme enigme) {

        ContentValues values = new ContentValues();

        values.put(COL_ENIGME, enigme.getEnigme());
        values.put(COL_SOLUTION, enigme.getReponse());

        return bdd.insert(TABLE_ENIGMES, null, values);
    }

    public int updateEnigme(int id, Enigme enigme) {

        ContentValues values = new ContentValues();
        values.put(COL_ENIGME, enigme.getEnigme());
        values.put(COL_SOLUTION, enigme.getReponse());
        return bdd.update(TABLE_ENIGMES, values, COL_ID + " = " + id, null);
    }

    public int removeLivreWithID(int id) {

        return bdd.delete(TABLE_ENIGMES, COL_ID + " = " + id, null);
    }

    public Enigme getEnigmeWithQuestion(String question) {

        Cursor c = bdd.query(TABLE_ENIGMES, new String[]{COL_ID, COL_ENIGME, COL_SOLUTION},
                COL_SOLUTION + " LIKE \"" + question + "\"", null, null, null, null);
        return cursorToEnigme(c);
    }

    public Enigme getEnigmeWithId(int id) {

        Cursor c = bdd.query(TABLE_ENIGMES, new String[]{COL_ID, COL_ENIGME, COL_SOLUTION},
                COL_ID + " LIKE \"" + id + "\"", null, null, null, null);
        return cursorToEnigme(c);
    }

    private Enigme cursorToEnigme(Cursor c) {
        if (c.getCount() == 0)
            return null;
        c.moveToFirst();
        Enigme enigme = new Enigme();
//on lui affecte toutes les infos grâce aux infos contenues dans le Cursor
        enigme.setId(c.getInt(NUM_COL_ID));
        enigme.setEnigme(c.getString(NUM_COL_ENIGME));
        enigme.setReponse(c.getString(NUM_COL_SOLUTION));
//On ferme le cursor
        c.close();
//On retourne le livre
        return enigme;
    }
}