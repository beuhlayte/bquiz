package com.belette.bquiz;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by jordan on 03/10/2015.
 */
public class MaBaseSQLite extends SQLiteOpenHelper {

    private static final String CREER_TABLE_THEMES = "CREATE TABLE " + QuizBDD.TABLE_THEMES + " (" + QuizBDD.COL_IDTHEMES + " INTEGER PRIMARY KEY , " + QuizBDD.COL_TTHEME + " VARCHAR(255) ) ;";
    private static final String CREER_TABLE_QUESTIONS = "CREATE TABLE " + QuizBDD.TABLE_QUESTIONS + " (" + QuizBDD.COL_IDQUESTIONS + " INTEGER PRIMARY KEY , " + QuizBDD.COL_QQUESTION + " VARCHAR(255) , " + QuizBDD.COL_QTHEME + " INT , " + QuizBDD.COL_QDATEMODIF + " VARCHAR(255) ) ;";
    private static final String CREER_TABLE_REPONSES = "CREATE TABLE " + QuizBDD.TABLE_REPONSES + " (" + QuizBDD.COL_IDREPONSES + " INTEGER PRIMARY KEY , " + QuizBDD.COL_REPONSE + " VARCHAR(255) , " + QuizBDD.COL_RQUESTION + " INT , " + QuizBDD.COL_RDATEMODIF + " VARCHAR(255) ) ;";
    private static final String CREER_TABLE_SYNCHRO = "CREATE TABLE " + QuizBDD.TABLE_SYNCHRO + " (" + QuizBDD.COL_IDSYNCHRO + " INTEGER PRIMARY KEY , " + QuizBDD.COL_DATESYNCHRO + " VARCHAR(50) ); " ;

    public MaBaseSQLite(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREER_TABLE_THEMES);
        db.execSQL(CREER_TABLE_QUESTIONS);
        db.execSQL(CREER_TABLE_REPONSES);
        db.execSQL(CREER_TABLE_SYNCHRO);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int version, int newVersion) {
        db.execSQL("DROP TABLE if exists " + QuizBDD.TABLE_REPONSES);
        db.execSQL("DROP TABLE if exists " + QuizBDD.TABLE_QUESTIONS);
        db.execSQL("DROP TABLE if exists " + QuizBDD.TABLE_THEMES);
        db.execSQL("DROP TABLE if exists " + QuizBDD.TABLE_SYNCHRO);
        onCreate(db);
    }
}
