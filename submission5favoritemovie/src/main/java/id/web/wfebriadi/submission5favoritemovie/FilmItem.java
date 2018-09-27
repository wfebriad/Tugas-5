package id.web.wfebriadi.submission5favoritemovie;

import android.database.Cursor;

import org.json.JSONObject;

import id.web.wfebriadi.submission5favoritemovie.database.DatabaseContract;

import static android.provider.BaseColumns._ID;
import static id.web.wfebriadi.submission5favoritemovie.database.DatabaseContract.getColumnInt;
import static id.web.wfebriadi.submission5favoritemovie.database.DatabaseContract.getColumnString;

public class FilmItem {

    private Integer id;
    private String title, overview, release_date, poster, backdrop_poster;

    public FilmItem(){

    }

    public FilmItem(Cursor cursor){
        this.id = getColumnInt(cursor, _ID);
        this.title =  getColumnString(cursor, DatabaseContract.FilmColumns.TITLE);
        this.overview = getColumnString(cursor, DatabaseContract.FilmColumns.OVERVIEW);
        this.release_date = getColumnString(cursor, DatabaseContract.FilmColumns.RELEASE_DATE);
        this.poster = getColumnString(cursor, DatabaseContract.FilmColumns.POSTER);
        this.backdrop_poster = getColumnString(cursor, DatabaseContract.FilmColumns.BACKDROP_POSTER);
    }
    public FilmItem(JSONObject object){
        try {
            int id = object.getInt("id");
            String title = object.getString("title");
            String overview = object.getString("overview");
            String release = object.getString("release_date");
            String poster = object.getString("poster_path");
            String backdrop_poster = object.getString("backdrop_path");

            this.id = id;
            this.title = title;
            this.overview = overview;
            this.release_date = release;
            this.poster = poster;
            this.backdrop_poster = backdrop_poster;
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public int getId(){
        return id;
    }
    public void setId(int id){
        this.id = id;
    }

    public String getTitle(){
        return title;
    }
    public void setTitle(String title){
        this.title = title;
    }

    public String getOverview(){
        return overview;
    }
    public void setOverview(String overview){
        this.overview = overview;
    }

    public String getRelease(){
        return release_date;
    }
    public void setRelease(String release){
        this.release_date = release_date;
    }

    public String getPoster(){
        return poster;
    }
    public void setPoster(String poster){
        this.poster = poster;
    }

    public String getBackdropPoster(){
        return backdrop_poster;
    }
    public void setBackdrop_poster(String backdrop_poster){
        this.backdrop_poster = backdrop_poster;
    }
}
