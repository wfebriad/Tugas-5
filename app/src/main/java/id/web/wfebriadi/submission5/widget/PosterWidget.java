package id.web.wfebriadi.submission5.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.widget.RemoteViews;
import android.widget.Toast;

import java.util.Objects;

import id.web.wfebriadi.submission5.R;

/**
 * Implementation of App Widget functionality.
 */
public class PosterWidget extends AppWidgetProvider {

    public static final String TOAST = "id.web.wfebriadi.submission5.TOAST";
    public static final String EXTRA_ITEM = "id.wfebriadi.submission5.EXTRA_ITEM";
    public static final String UPDATE_WIDGET = "id.wfebriadi.submission5.UPDATE_WIDGET";

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {

        Intent intent = new Intent(context, ServiceFilmFavorite.class);
        intent.setAction(UPDATE_WIDGET);
        context.sendBroadcast(intent);

        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
        intent.setData(Uri.parse(intent.toUri(intent.URI_INTENT_SCHEME)));

        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.poster_widget);
        views.setRemoteAdapter(R.id.stack_view, intent);
        views.setEmptyView(R.id.stack_view, R.id.empty_view);

        Intent toastIntent = new Intent(context, PosterWidget.class);
        toastIntent.setAction(PosterWidget.TOAST);
        toastIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
        intent.setData(Uri.parse(intent.toUri(Intent.URI_INTENT_SCHEME)));
        PendingIntent toastPendingIntent = PendingIntent.getBroadcast(context, 0, toastIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        views.setPendingIntentTemplate(R.id.stack_view, toastPendingIntent);

        appWidgetManager.updateAppWidget(appWidgetId, views);

        /*
        CharSequence widgetText = context.getString(R.string.appwidget_text);
        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.poster_widget);
        views.setTextViewText(R.id.appwidget_text, widgetText);

        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
        */
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }
    public void onReceive(Context context, Intent intent){
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
        switch (Objects.requireNonNull(intent.getAction())){
            case TOAST :
                int appWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID);
                int view = intent.getIntExtra(EXTRA_ITEM, 0);
                Toast.makeText(context, "Touched View " + view, Toast.LENGTH_SHORT).show();
                break;
            case UPDATE_WIDGET :
                int widgetIds[] = appWidgetManager.getAppWidgetIds(new ComponentName(context, PosterWidget.class));
                onUpdate(context, appWidgetManager, widgetIds);
                appWidgetManager.notifyAppWidgetViewDataChanged(widgetIds, R.id.stack_view);
                break;
        }

        AppWidgetManager appWidgetManager1 = AppWidgetManager.getInstance(context);
        ComponentName widget = new ComponentName(context, PosterWidget.class);
        int[] appWidgetId = appWidgetManager1.getAppWidgetIds(widget);
        appWidgetManager1.notifyAppWidgetViewDataChanged(appWidgetId, R.id.stack_view);
        /*
        if (intent.getAction().equals(TOAST)){
            int view = intent.getIntExtra(EXTRA_ITEM, 0);
            Toast.makeText(context, "Touched View " + view, Toast.LENGTH_SHORT).show();
        }
        if (intent.getAction().equals(UPDATE_WIDGET)){
            int widget[] = AppWidgetManager.getInstance(context).getAppWidgetIds(new ComponentName(context,PosterWidget.class));

            for (int id:widget){
                AppWidgetManager.getInstance(context).notifyAppWidgetViewDataChanged(id, R.id.stack_view);
            }
        }
        */
        super.onReceive(context, intent);
    }
}

