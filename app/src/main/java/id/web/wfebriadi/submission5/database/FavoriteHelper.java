package id.web.wfebriadi.submission5.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import java.util.ArrayList;

import id.web.wfebriadi.submission5.FilmItem;

import static android.provider.BaseColumns._ID;
import static id.web.wfebriadi.submission5.database.DatabaseContract.FILM_FAVORITE;
import static id.web.wfebriadi.submission5.database.DatabaseContract.FilmColumns.BACKDROP_POSTER;
import static id.web.wfebriadi.submission5.database.DatabaseContract.FilmColumns.OVERVIEW;
import static id.web.wfebriadi.submission5.database.DatabaseContract.FilmColumns.POSTER;
import static id.web.wfebriadi.submission5.database.DatabaseContract.FilmColumns.RELEASE_DATE;
import static id.web.wfebriadi.submission5.database.DatabaseContract.FilmColumns.TITLE;

public class FavoriteHelper {

    private Context context;
    private DatabaseHelper databaseHelper;
    private SQLiteDatabase database;

    public FavoriteHelper (Context context){
        this.context = context;
    }
    public FavoriteHelper open() throws SQLException {
        databaseHelper = new DatabaseHelper(context);
        database = databaseHelper.getWritableDatabase();
        return this;
    }
    public void close() {
        databaseHelper.close();
    }
    public void insertTrasaction (FilmItem filmItem){
        String SQL = "INSERT INTO " + FILM_FAVORITE + " ("
                + TITLE + ","
                + OVERVIEW + ", "
                + RELEASE_DATE + ", "
                + POSTER + ", "
                + BACKDROP_POSTER + ") VALUES (?,?,?,?,?)";

        SQLiteStatement stmt = database.compileStatement(SQL);
        stmt.bindString(1, filmItem.getTitle());
        stmt.bindString(2, filmItem.getOverview());
        stmt.bindString(3, filmItem.getRelease());
        stmt.bindString(4, filmItem.getPoster());
        stmt.bindString(5, filmItem.getBackdropPoster());
        stmt.execute();
        stmt.clearBindings();

    }
    public boolean checkData(String title){
        Cursor cursor = database.query(FILM_FAVORITE, null,
                TITLE + " LIKE ?", new String[]{title},
                null, null, _ID + " ASC", null);
        cursor.moveToFirst();
        if (cursor.getCount() > 0){
            return true;
        } else {
            return false;
        }
    }
    public Cursor queryByIdProvider(String id){
        return database.query(FILM_FAVORITE, null,
                _ID + " = ?",
                null,
                null,
                null,
                null);
    }
    public Cursor queryProvider(){
        return database.query(FILM_FAVORITE,
                null,
                null,
                null,
                null,
                null,
                _ID + " DESC");
    }
    public long insertProvider(ContentValues values){
        return database.insert(FILM_FAVORITE,null, values);
    }

    public int updateProvider(String id, ContentValues values){
        return database.update(FILM_FAVORITE, values, _ID+" =?", new String[]{id});
    }
    public int DeleteProvider(FilmItem filmItem){
        return database.delete(FILM_FAVORITE, _ID+" = ?", new String[]{});
    }
    public int deleteProvider(String id){
        return database.delete(FILM_FAVORITE, _ID+" = ?", new String[]{id});
    }
    public void beginTransaction(){
        database.beginTransaction();
    }

    public void setTransactionSuccess(){
        database.setTransactionSuccessful();
    }

    public void endTransaction(){
        database.endTransaction();
    }


    public ArrayList<FilmItem> getAllData() {
        Cursor cursor = database.query(FILM_FAVORITE, null, null, null, null, null, _ID + " ASC", null);
        cursor.moveToFirst();
        ArrayList<FilmItem> arrayList = new ArrayList<>();
        FilmItem filmItem1;
        if (cursor.getCount() > 0) {
            do {
                filmItem1 = new FilmItem(cursor);
                filmItem1.setId(cursor.getInt(cursor.getColumnIndexOrThrow(_ID)));
                filmItem1.setTitle(cursor.getString(cursor.getColumnIndexOrThrow(TITLE)));
                filmItem1.setOverview(cursor.getString(cursor.getColumnIndexOrThrow(OVERVIEW)));
                filmItem1.setRelease(cursor.getString(cursor.getColumnIndexOrThrow(RELEASE_DATE)));
                filmItem1.setPoster(cursor.getString(cursor.getColumnIndexOrThrow(POSTER)));
                filmItem1.setBackdrop_poster(cursor.getString(cursor.getColumnIndexOrThrow(BACKDROP_POSTER)));

                arrayList.add(filmItem1);
                cursor.moveToNext();
            } while ((!cursor.isAfterLast()));
        }
        cursor.close();
        return arrayList;
    }


}
