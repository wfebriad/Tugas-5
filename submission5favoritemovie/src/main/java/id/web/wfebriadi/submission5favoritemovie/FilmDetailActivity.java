package id.web.wfebriadi.submission5favoritemovie;

import android.database.Cursor;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class FilmDetailActivity extends AppCompatActivity {

    public static String TITLE = "title";
    public static String OVERVIEW = "overview";
    public static String RELEASE_DATE = "release_date";
    public static String POSTER = "poster";
    public static String BACKDROP_POSTER = "backdrop_poster";

    TextView title, overview, release_date;
    ImageView poster, backdrop_poster;

    FilmItem filmItem = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_film_detail);

        title = (TextView)findViewById(R.id.tv_judulFilm);
        overview = (TextView)findViewById(R.id.tv_deskripsiFilm);
        release_date = (TextView)findViewById(R.id.tv_tanggalRilis);
        poster = (ImageView)findViewById(R.id.img_posterFilm);
        backdrop_poster = (ImageView)findViewById(R.id.poster_backdrop);

        Uri uri = getIntent().getData();

        if (uri != null) {
            Cursor cursor = getContentResolver().query(uri, null, null, null, null );
            if (cursor != null){
                if (cursor.moveToFirst()) filmItem = new FilmItem(cursor);
                cursor.close();
            }
        }
        if (filmItem != null){
            title.setText(filmItem.getTitle());
            overview.setText(filmItem.getOverview());
            release_date.setText(filmItem.getRelease());

            Picasso.with(this)
                    .load("https://image.tmdb.org/t/p/w780/"+filmItem.getPoster())
                    .into(poster);

            Picasso.with(this)
                    .load("https://image.tmdb.org/t/p/w780/"+filmItem.getBackdropPoster())
                    .into(backdrop_poster);
        }

    }
}
