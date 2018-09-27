package id.web.wfebriadi.submission5.provider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import id.web.wfebriadi.submission5.database.DatabaseContract;
import id.web.wfebriadi.submission5.database.FavoriteHelper;

import static id.web.wfebriadi.submission5.database.DatabaseContract.AUTHORITY;
import static id.web.wfebriadi.submission5.database.DatabaseContract.CONTENT_URI;

public class FilmProvider extends ContentProvider {

    private static final int FILM = 1;
    private static final int FILM_ID = 2;
    private FavoriteHelper favoriteHelper;

    private static final UriMatcher URI_MATCHER = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        URI_MATCHER.addURI(AUTHORITY, DatabaseContract.FILM_FAVORITE, FILM);
        URI_MATCHER.addURI(AUTHORITY, DatabaseContract.FILM_FAVORITE + "/#", FILM_ID);
    }

    @Override
    public boolean onCreate() {
        favoriteHelper = new FavoriteHelper(getContext());
        favoriteHelper.open();
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, String[] strings, String s, String[] strings1, String s1) {
        Cursor cursor;
        switch (URI_MATCHER.match(uri)){
            case FILM:
                cursor = favoriteHelper.queryProvider();
                break;
            case FILM_ID:
                cursor = favoriteHelper.queryByIdProvider(uri.getLastPathSegment());
                break;
            default:
                cursor = null;
                break;
        }
        if (cursor != null){
            cursor.setNotificationUri(getContext().getContentResolver(), uri);
        }
        return cursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        long insert;

        switch (URI_MATCHER.match(uri)){
            case FILM:
                insert = favoriteHelper.insertProvider(values);
                break;
            default:
                insert = 0;
                break;
        }
        if (insert > 0){
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return Uri.parse(CONTENT_URI + "/" + insert);
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        int delete;
        switch (URI_MATCHER.match(uri)){
            case FILM:
                delete = favoriteHelper.deleteProvider(uri.getLastPathSegment());
                break;
            default:
                delete = 0;
                break;
        }
        if (delete > 0){
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return delete;
    }

    @Override
    public int update(@NonNull Uri uri, ContentValues values, String s, String[] string) {
        int update;
        switch (URI_MATCHER.match(uri)){
            case FILM_ID:
                update = favoriteHelper.updateProvider(uri.getLastPathSegment(), values);
                break;
            default:
                update = 0;
                break;
        }
        if (update > 0){
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return update;
    }
}
