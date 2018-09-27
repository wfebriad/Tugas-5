package id.web.wfebriadi.submission5.notification;

import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import cz.msebera.android.httpclient.Header;
import id.web.wfebriadi.submission5.FilmDetailActivity;
import id.web.wfebriadi.submission5.FilmItem;
import id.web.wfebriadi.submission5.MainActivity;
import id.web.wfebriadi.submission5.R;

import static id.web.wfebriadi.submission5.BuildConfig.API_KEY;

public class ReleaseNotification extends BroadcastReceiver {

    public static final int NOTIFICATION_ID = 100;
    public List<FilmItem> listFilm = new ArrayList<>();
    @Override
    public void onReceive(final Context context, Intent intent) {
        //final String date = new SimpleDateFormat("E, MMM dd, yyyy", Locale.getDefault()).format(new Date());
        //final String title = "";

        String URL = "https://api.themoviedb.org/3/movie/upcoming?api_key=" + API_KEY + "&language=en-US";
        AsyncHttpClient asyncHttpClient = new AsyncHttpClient();
        asyncHttpClient.get(URL, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                String result = new String (responseBody);
                try{
                    JSONObject responseObject = new JSONObject( result );
                    JSONArray results = responseObject.getJSONArray( "results" );
                    Gson gson = new Gson();
                    listFilm = gson.fromJson( String.valueOf( results ), new TypeToken<List<FilmItem>>() {
                    }.getType() );
                    Log.d( "MovieResult", String.valueOf( gson.toJson( listFilm ) ) );


                    SimpleDateFormat sdf = new SimpleDateFormat( "yyyy-MM-dd" );
                    String today = sdf.format( new Date() );

                    Log.d( "onNotif" , "Date " + today );

                    List<FilmItem> items = new ArrayList<>();

                    for (int i = 0; i < listFilm.size(); i++) {
                        String movieDate = listFilm.get( i ).getRelease();
                        Log.d( "onNotif" , "Date " + movieDate + " title " + listFilm.get( i ).title);
                        if (today.equals( movieDate )){
                            items.add( listFilm.get( i ) );
                        }
                    }

                    Log.d( "onNotif" , "movieSize " + items.size());


                    int notifId = 200;

                    notification( context, notifId, items );
                } catch (Exception e){
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show();
            }
        });
    }
    public void notification(Context context, int notifID, List<FilmItem> items) {
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        //Intent intent = new Intent(context, MainActivity.class);
        // PendingIntent pendingIntent = PendingIntent.getActivity(context, notifID, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        Uri uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        for (int i = 0; i < items.size(); i++) {
            Intent intent = new Intent(context, MainActivity.class);
            intent.putExtra(FilmDetailActivity.MOVIE_ITEM, new Gson().toJson(items.get(i)));
            PendingIntent pendingIntent = PendingIntent.getActivity(context, notifID, intent, PendingIntent.FLAG_UPDATE_CURRENT);

            NotificationCompat.Builder builder = new NotificationCompat.Builder(context, items.get(i).getTitle())
                    .setContentTitle(context.getResources().getString(R.string.app_name_release))
                    .setContentText(items.get(i).title)
                    .setContentIntent(pendingIntent)
                    .setSmallIcon(R.drawable.ic_notifications_active_black_24dp)
                    .setVibrate(new long[]{1000, 1000, 1000, 1000, 1000})
                    .setSound(uri)
                    .setAutoCancel(true);

            notificationManager.notify(i, builder.build());
        }
    }
    public void notificationRelease(Context context){
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 12);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.AM_PM, Calendar.PM);

        Intent intent = new Intent(context, ReleaseNotification.class);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, NOTIFICATION_ID, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
    }
}
