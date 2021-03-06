package fr.unilim.iut.enigmagame;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class BaseSqlLite extends SQLiteOpenHelper {
    private static final String TABLE_ENIGMES = "table_enigmes";
    private static final String COL_ID = "ID";
    private static final String COL_ENIGME = "ENIGME";
    private static final String COL_REPONSE = "REPONSE";
    private static final String COL_SCORE = "NB_ESSAIS";
    private static final String CREATE_BDD = "CREATE TABLE " + TABLE_ENIGMES + " ("
            + COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COL_ENIGME + " TEXT NOT NULL, "
            + COL_REPONSE + " TEXT NOT NULL, " + COL_SCORE + " INTEGER NOT NULL " + ");";

    public BaseSqlLite(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    /**
     * À l'installation, on crée la DB
     * @param db
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_BDD);
    }

    /**
     * À la mise à jour, on supprime l'ancienne DB puis on en recrée une
     * @param db
     * @param i
     * @param i1
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ENIGMES + ";");
        onCreate(db);
    }



}