package id.web.wfebriadi.submission5.widget;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.Target;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import id.web.wfebriadi.submission5.FilmItem;
import id.web.wfebriadi.submission5.R;
import id.web.wfebriadi.submission5.database.FavoriteHelper;

public class StackRemoteViewsFactory implements
        RemoteViewsService.RemoteViewsFactory {

    public static final String URL = "https://image.tmdb.org/t/p/w500/";
    public static ArrayList<FilmItem> filmItem = new ArrayList<>();
    public Context context;
    public int widgetID;

    private void getDataMovie(Context context){
        FavoriteHelper favoriteHelper = new FavoriteHelper(context);
        favoriteHelper.open();
        filmItem = favoriteHelper.getAllData();
        favoriteHelper.close();
    }
    public StackRemoteViewsFactory(Context context1, Intent intent){
        context = context1;
        widgetID = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID);

    }

    @Override
    public void onCreate() {
        getDataMovie(context);
    }

    @Override
    public void onDataSetChanged() {
        getDataMovie(context);
    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        return filmItem.size();
    }

    @Override
    public RemoteViews getViewAt(int position) {
        RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.widget_image);
        Bitmap bitmap = null;
        try{
            bitmap = Glide.with(context)
                    .load(URL+filmItem.get(position).getPoster())
                    .asBitmap()
                    .error(new ColorDrawable(context.getResources().getColor(R.color.colorAccent)))
                    .into(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL).get();
        }catch (InterruptedException e){
            e.printStackTrace();
        } catch (ExecutionException e){
            e.printStackTrace();
        }

        remoteViews.setImageViewBitmap(R.id.imageViewWidget, bitmap);
        Bundle bundle = new Bundle();
        bundle.putInt(PosterWidget.EXTRA_ITEM, position);
        Intent intent1 = new Intent();
        intent1.putExtras(bundle);

        remoteViews.setOnClickFillInIntent(R.id.imageViewWidget, intent1);
        return remoteViews;

    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }
}
