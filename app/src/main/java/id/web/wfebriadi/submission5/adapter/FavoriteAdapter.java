package id.web.wfebriadi.submission5.adapter;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import id.web.wfebriadi.submission5.FilmDetailActivity;
import id.web.wfebriadi.submission5.FilmItem;
import id.web.wfebriadi.submission5.R;

public class FavoriteAdapter extends RecyclerView.Adapter<FavoriteAdapter.ViewHolder> {

    private Context context;
    private Cursor cursor;
    //ArrayList<FilmItem> filmItem = new ArrayList<>();
    public void setFilm(Cursor listFilm){
        this.cursor = listFilm;
    }
    public FavoriteAdapter(Context context){
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_film_favorite, parent, false);
        return new ViewHolder(v);
    }
    public FilmItem getItem(int position){
        if (!cursor.moveToPosition(position)){
            throw new IllegalStateException("Position Invalid");
        }
        return new FilmItem(cursor);
    }

    @Override
    public void onBindViewHolder(@NonNull FavoriteAdapter.ViewHolder holder, final int position) {
        final FilmItem filmItem = getItem(position);

        holder.tvTitle.setText(filmItem.getTitle());
        holder.tvDeskripsi.setText(filmItem.getOverview());
        holder.tvRelease_date.setText(filmItem.getRelease());
        Picasso.with(context)
                .load("http://image.tmdb.org/t/p/w500/"+filmItem.getPoster())
                .into(holder.imgPoster);

        holder.btnDetail.setOnClickListener(new View.OnClickListener() {
            public void onClick (View v){
                //FilmItem filmItem = listFilm.get(position);
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
        if (cursor == null ) return 0;
        return cursor.getCount();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imgPoster;
        TextView tvTitle;
        TextView tvRelease_date;
        TextView tvDeskripsi;
        Button btnDetail;

        public ViewHolder(View v){
            super(v);
            imgPoster = (ImageView)v.findViewById(R.id.img_posterFilm);
            tvTitle = (TextView)v.findViewById(R.id.tv_judulFilm);
            tvRelease_date = (TextView)v.findViewById(R.id.tv_tanggalRilis);
            tvDeskripsi = (TextView)v.findViewById(R.id.tv_deskripsiFilm);
            btnDetail = (Button)v.findViewById(R.id.btn_detail);
        }
    }
}