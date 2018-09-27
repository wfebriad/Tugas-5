package id.web.wfebriadi.submission5.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import id.web.wfebriadi.submission5.FilmDetailActivity;
import id.web.wfebriadi.submission5.FilmItem;
import id.web.wfebriadi.submission5.R;
import id.web.wfebriadi.submission5.database.FavoriteHelper;
import id.web.wfebriadi.submission5.widget.PosterWidget;

public class NowPlayingUpcommingAdapter extends RecyclerView.Adapter<NowPlayingUpcommingAdapter.ViewHolder> {

    private Context context;
    FavoriteHelper favoriteHelper;
    ArrayList<FilmItem> filmItemArrayList = new ArrayList<>();

    public ArrayList<FilmItem> getFilmItemArrayList() {
        return filmItemArrayList;
    }
    public void ListFilm(ArrayList<FilmItem> filmItemArrayList){
        this.filmItemArrayList = filmItemArrayList;
    }
    public NowPlayingUpcommingAdapter(Context context){
        this.context = context;
    }
    public void setData(ArrayList<FilmItem> filmItems){
        filmItemArrayList = filmItems;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public NowPlayingUpcommingAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemFilm = LayoutInflater.from(parent.getContext()).inflate(R.layout.detail_film, parent, false);
        return new ViewHolder(itemFilm);
    }

    @Override
    public void onBindViewHolder(@NonNull NowPlayingUpcommingAdapter.ViewHolder holder, final int position) {
        Picasso.with(this.context)
                .load("http://image.tmdb.org/t/p/w500" + getFilmItemArrayList().get(position).getPoster())
                .into(holder.poster);

        holder.title.setText(getFilmItemArrayList().get(position).getTitle());
        holder.release_date.setText(getFilmItemArrayList().get(position).getRelease());

        String tanggal = getFilmItemArrayList().get(position).getRelease();
        SimpleDateFormat date_format = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date date = date_format.parse(tanggal);
            SimpleDateFormat new_format_tanggal = new SimpleDateFormat("EEEE, dd/MM/yyyy");
            String tanggal_rilis = new_format_tanggal.format(date);
            holder.release_date.setText(tanggal_rilis);
        } catch (Exception e){
            e.printStackTrace();
        }

        holder.overview.setText(getFilmItemArrayList().get(position).getOverview());
        holder.btnDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FilmItem filmItem = filmItemArrayList.get(position);
                Intent intent = new Intent(context, FilmDetailActivity.class);
                intent.putExtra(FilmDetailActivity.TITLE, filmItem.getTitle());
                intent.putExtra(FilmDetailActivity.RELEASE_DATE, filmItem.getRelease());
                intent.putExtra(FilmDetailActivity.OVERVIEW, filmItem.getOverview());
                intent.putExtra(FilmDetailActivity.POSTER, filmItem.getPoster());
                intent.putExtra(FilmDetailActivity.BACKDROP_POSTER, filmItem.getBackdropPoster());
                context.startActivity(intent);
            }
        });
        holder.btnFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                favoriteHelper = new FavoriteHelper(context);
                FilmItem filmItem = getFilmItemArrayList().get(position);
                favoriteHelper.open();

                favoriteHelper.beginTransaction();
                if (!favoriteHelper.checkData(filmItem.getTitle())){
                    try {
                        favoriteHelper.insertTrasaction(filmItem);
                        favoriteHelper.setTransactionSuccess();
                        Toast.makeText(context, "This Film Added to Favoritte", Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(context, PosterWidget.class);
                        i.setAction(PosterWidget.UPDATE_WIDGET);
                        context.sendBroadcast(i);
                    } catch (Exception e){

                    }
                } else {
                    //favoriteHelper.deleteProvider();
                    //favoriteHelper.setTransactionSuccess();
                    Toast.makeText(context, "This Film Already Added to Database", Toast.LENGTH_SHORT).show();
                }
                favoriteHelper.endTransaction();
                favoriteHelper.close();

            }
        });
        holder.cardView.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                FilmItem filmItem = filmItemArrayList. get(position);
                Intent intent = new Intent(context, FilmDetailActivity.class);
                intent.putExtra(FilmDetailActivity.TITLE, filmItem.getTitle());
                intent.putExtra(FilmDetailActivity.OVERVIEW, filmItem.getOverview());
                intent.putExtra(FilmDetailActivity.RELEASE_DATE, filmItem.getRelease());
                intent.putExtra(FilmDetailActivity.POSTER, filmItem.getPoster());
                intent.putExtra(FilmDetailActivity.BACKDROP_POSTER, filmItem.getBackdropPoster());
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return filmItemArrayList == null ? 0: getFilmItemArrayList().size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView poster;
        TextView title;
        TextView release_date;
        TextView overview;
        Button btnDetail;
        Button btnFavorite;
        CardView cardView;

        public ViewHolder(View itemFilm){
            super(itemFilm);
            poster = (ImageView)itemFilm.findViewById(R.id.img_posterFilm);
            title = (TextView)itemFilm.findViewById(R.id.tv_judulFilm);
            release_date = (TextView)itemFilm.findViewById(R.id.tv_tanggalRilis);
            overview = (TextView)itemFilm.findViewById(R.id.tv_deskripsiFilm);
            btnDetail = (Button)itemFilm.findViewById(R.id.btn_detail);
            btnFavorite = (Button)itemFilm.findViewById(R.id.btn_favorite);
            cardView = (CardView)itemFilm.findViewById(R.id.card_view1);
        }
    }
    private void deleteFavorite(String id){

    }
}