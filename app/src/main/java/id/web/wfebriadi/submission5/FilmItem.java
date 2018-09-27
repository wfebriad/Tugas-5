package id.web.wfebriadi.submission5;

import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONObject;

import id.web.wfebriadi.submission5.database.DatabaseContract;

import static android.provider.BaseColumns._ID;
import static id.web.wfebriadi.submission5.database.DatabaseContract.getColumnInt;
import static id.web.wfebriadi.submission5.database.DatabaseContract.getColumnString;

public class FilmItem implements Parcelable{

    private Integer id;
    public int tmdbID;
    public String title, overview, release_date, poster, backdrop_poster, posterFilmWidget;

    public FilmItem(JSONObject object){
        try {
            int id = object.getInt("id");
            String title = object.getString("title");
            String overview = object.getString("overview");
            String release = object.getString("release_date");
            String poster = object.getString("poster_path");
            String backdrop_poster = object.getString("backdrop_path");

            this.tmdbID = id;
            this.title = title;
            this.overview = overview;
            this.release_date = release;
            this.poster = poster;
            this.backdrop_poster = backdrop_poster;
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public FilmItem(Cursor cursor){
        this.id = getColumnInt(cursor, _ID);
        //this.tmdbID = getColumnInt(cursor, DatabaseContract.FilmColumns.TMDBID);
        this.title =  getColumnString(cursor, DatabaseContract.FilmColumns.TITLE);
        this.overview = getColumnString(cursor, DatabaseContract.FilmColumns.OVERVIEW);
        this.release_date = getColumnString(cursor, DatabaseContract.FilmColumns.RELEASE_DATE);
        this.poster = getColumnString(cursor, DatabaseContract.FilmColumns.POSTER);
        this.backdrop_poster = getColumnString(cursor, DatabaseContract.FilmColumns.BACKDROP_POSTER);
    }
    public FilmItem(int tmdbID, String title, String overview,
                    String release_date, String poster, String backdrop_poster){
        this.title = title;
        this.overview = overview;
        this.release_date = release_date;
        this.poster = poster;
        this.backdrop_poster = backdrop_poster;
        this.tmdbID = tmdbID;
    }
    public FilmItem(int id, int tmdbID, String title, String overview,
                    String release_date, String poster, String backdrop_poster){
        this(tmdbID, title, overview, release_date, poster, backdrop_poster);
        this.id = id;
    }



    public static final Creator<FilmItem> CREATOR = new Creator<FilmItem>() {
        @Override
        public FilmItem createFromParcel(Parcel in) {
            return new FilmItem(in);
        }

        @Override
        public FilmItem[] newArray(int size) {
            return new FilmItem[size];
        }
    };

    public int getId(){
        return id;
    }
    public void setId(int id){
        this.id = id;
    }
    public int getTmdbID(){
        return tmdbID;
    }
    public void setTmdbID(int tmdbID){
        this.tmdbID = tmdbID;
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

    public String getPosterFilmWidget(){
        return posterFilmWidget;
    }
    public void setPosterFilmWidget(String poster){
        this.posterFilmWidget = poster;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.title);
        dest.writeString(this.release_date);
        dest.writeString(this.overview);
        dest.writeString(this.poster);
        dest.writeString(this.backdrop_poster);
        //dest.writeInt(this.id);
    }
    protected FilmItem(Parcel in) {
        if (in.readByte() == 0) {
            id = null;
        } else {
            id = in.readInt();
        }
        tmdbID = in.readInt();
        title = in.readString();
        overview = in.readString();
        release_date = in.readString();
        poster = in.readString();
        backdrop_poster = in.readString();
        posterFilmWidget = in.readString();
    }
}
