package id.web.wfebriadi.submission5;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Date;

import id.web.wfebriadi.submission5.database.DatabaseContract;
import id.web.wfebriadi.submission5.database.FavoriteHelper;

public class FilmDetailActivity extends AppCompatActivity {
    public static String TITLE = "title";
    public static String OVERVIEW = "overview";
    public static String RELEASE_DATE = "release_date";
    public static String POSTER = "poster";
    public static String BACKDROP_POSTER = "backdrop_poster";
    public static String MOVIE_ITEM = "movie_item";

    private TextView tvTitle, tvOverview, tvReleaseDate;
    private ImageView imgPoster, imgBackdropPoster;
    private Context context;
    private FilmItem filmItem;
    private FavoriteHelper favoriteHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_film_detail);

        Intent intent = this.getIntent();
        final int tmdbId = intent.getIntExtra("tmdbid", 0);

        tvTitle = (TextView) findViewById(R.id.tv_judulFilm);
        tvOverview = (TextView) findViewById(R.id.tv_deskripsiFilm);
        tvReleaseDate = (TextView) findViewById(R.id.tv_tanggalRilis);
        imgPoster = (ImageView) findViewById(R.id.img_posterFilm);
        imgBackdropPoster = (ImageView) findViewById(R.id.poster_backdrop);

        final String title = getIntent().getStringExtra(TITLE);
        final String overview = getIntent().getStringExtra(OVERVIEW);
        final String release_date = getIntent().getStringExtra(RELEASE_DATE);
        final String poster = getIntent().getStringExtra(POSTER);
        final String backdrop_poster = getIntent().getStringExtra(BACKDROP_POSTER);



        this.filmItem = new FilmItem(tmdbId, title, overview, release_date, poster, backdrop_poster);
        this.favoriteHelper = new FavoriteHelper(this);
        //Cursor cursor = this.getContentResolver().query(DatabaseContract.contentUriWithID(this.filmItem.tmdbid), null, null, null, null);

        SimpleDateFormat date_format = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date date = date_format.parse(release_date);
            SimpleDateFormat new_format_tanggal = new SimpleDateFormat("EEEE, dd/MM/yyyy");
            String tanggal_rilis = new_format_tanggal.format(date);
            tvReleaseDate.setText(tanggal_rilis);
        } catch (Exception e) {
            e.printStackTrace();
        }
        tvTitle.setText(title);
        tvOverview.setText(overview);
        Picasso.with(this).load("http://image.tmdb.org/t/p/w500/" + poster).into(imgPoster);
        Picasso.with(this).load("http://image.tmdb.org/t/p/w500/" + poster).into(imgPoster);
        Picasso.with(this).load("http://image.tmdb.org/t/p/w780/" + backdrop_poster).into(imgBackdropPoster);

        CollapsingToolbarLayout collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        collapsingToolbarLayout.setTitle(title);


        collapsingToolbarLayout.setExpandedTitleTextAppearance(R.style.ExpandedAppBar);
        collapsingToolbarLayout.setCollapsedTitleTextAppearance(R.style.CollapsedAppBar);
    }
}
