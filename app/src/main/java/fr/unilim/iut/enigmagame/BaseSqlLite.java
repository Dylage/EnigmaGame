package fr.unilim.iut.enigmagame;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class BaseSqlLite extends SQLiteOpenHelper {
    private static final String TABLE_ENIGMES = "table_enigmes";
    private static final String COL_ID = "ID";
    private static final String COL_ENIGME = "ENIGME";
    private static final String COL_REPONSE = "REPONSE";
    private static final String CREATE_BDD = "CREATE TABLE " + TABLE_ENIGMES + " ("
            + COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COL_ENIGME + " TEXT NOT NULL, "
            + COL_REPONSE + " TEXT NOT NULL);";

    public BaseSqlLite(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);

    }
    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }


    public void init(SQLiteDatabase db) {
        db.execSQL(CREATE_BDD);
    }

    public void destroy(SQLiteDatabase db){
        db.execSQL("DROP TABLE " + TABLE_ENIGMES + ";");

    }


}