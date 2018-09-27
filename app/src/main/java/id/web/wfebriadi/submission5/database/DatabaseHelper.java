package id.web.wfebriadi.submission5.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static android.provider.BaseColumns._ID;
import static id.web.wfebriadi.submission5.database.DatabaseContract.FILM_FAVORITE;
import static id.web.wfebriadi.submission5.database.DatabaseContract.FilmColumns.BACKDROP_POSTER;
import static id.web.wfebriadi.submission5.database.DatabaseContract.FilmColumns.OVERVIEW;
import static id.web.wfebriadi.submission5.database.DatabaseContract.FilmColumns.POSTER;
import static id.web.wfebriadi.submission5.database.DatabaseContract.FilmColumns.RELEASE_DATE;
import static id.web.wfebriadi.submission5.database.DatabaseContract.FilmColumns.TITLE;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static String DATABASE_NAME = "db_film_favorite";

    private static final int DATABASE_VERSION = 1;

    public static String CREATE_TABLE_FILM = "create table " + FILM_FAVORITE +
            " (" +_ID + " integer primary key autoincrement, " +
            TITLE + " text not null, " +
            OVERVIEW + " text not null, " +
            RELEASE_DATE + " text not null, " +
            POSTER + " text not null, " +
            BACKDROP_POSTER + " text not null);";


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_FILM);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + FILM_FAVORITE);
        onCreate(db);
    }
}