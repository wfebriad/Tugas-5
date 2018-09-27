package id.web.wfebriadi.submission5favoritemovie.adapter;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import id.web.wfebriadi.submission5favoritemovie.R;

import static id.web.wfebriadi.submission5favoritemovie.database.DatabaseContract.FilmColumns.OVERVIEW;
import static id.web.wfebriadi.submission5favoritemovie.database.DatabaseContract.FilmColumns.POSTER;
import static id.web.wfebriadi.submission5favoritemovie.database.DatabaseContract.FilmColumns.RELEASE_DATE;
import static id.web.wfebriadi.submission5favoritemovie.database.DatabaseContract.FilmColumns.TITLE;
import static id.web.wfebriadi.submission5favoritemovie.database.DatabaseContract.getColumnString;

public class FavoriteAdapter extends CursorAdapter {

    public FavoriteAdapter(Context context, Cursor c, boolean autoRequery) {
        super(context, c, autoRequery);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        View v = LayoutInflater.from(context).inflate(R.layout.card_film_favorite, parent, false);
        return v;
    }

    @Override
    public Cursor getCursor() {
        return super.getCursor();
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        if (cursor!=null){
            TextView title = (TextView)view.findViewById(R.id.tv_judulFilm);
            TextView overview = (TextView)view.findViewById(R.id.tv_deskripsiFilm);
            TextView release_date = (TextView)view.findViewById(R.id.tv_tanggalRilis);
            ImageView poster = (ImageView)view.findViewById(R.id.img_posterFilm);
            final Button btnDetail = (Button)view.findViewById(R.id.btn_detail);

            title.setText(getColumnString(cursor, TITLE));
            overview.setText(getColumnString(cursor, OVERVIEW));
            release_date.setText(getColumnString(cursor, RELEASE_DATE));
            String url = getColumnString(cursor, POSTER);
            Glide.with(context)
                    .load("https://image.tmdb.org/t/p/w300/" + url)
                    .into(poster);

        }
    }
}
