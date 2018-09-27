package id.web.wfebriadi.submission5.fragment;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;
import id.web.wfebriadi.submission5.FilmItem;
import id.web.wfebriadi.submission5.R;
import id.web.wfebriadi.submission5.adapter.NowPlayingUpcommingAdapter;

import static id.web.wfebriadi.submission5.BuildConfig.API_KEY;

/**
 * A simple {@link Fragment} subclass.
 */
public class NowPlayingFragment extends Fragment {

    public static final String MOVIE = "EXTRAS_MOVIE";
    RecyclerView rvNowPlayingFragment;
    NowPlayingUpcommingAdapter listAdapter;
    ArrayList<FilmItem> filmItemArrayList = new ArrayList<>();
    Bundle bundle;
    private JSONObject jsonObject;

    public NowPlayingFragment() {
        // Required empty public constructor
    }

    public void onSaveInstanceState(Bundle bundle){
        bundle.putParcelableArrayList("listfilm", filmItemArrayList);
        super.onSaveInstanceState(bundle);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_now_playing, container, false);
    }
    public void onViewCreated (View v, Bundle savedInstanceState) {
        //super.onViewCreated(v, savedInstanceState);

        rvNowPlayingFragment = (RecyclerView)v.findViewById(R.id.rvNowPlaying);
        rvNowPlayingFragment.setLayoutManager(new LinearLayoutManager(this.getActivity()));
        rvNowPlayingFragment.setHasFixedSize(true);

        listAdapter = new NowPlayingUpcommingAdapter(this.getActivity());
        bundle = new Bundle();

        if (savedInstanceState == null){
            String url = "https://api.themoviedb.org/3/movie/now_playing?api_key="+API_KEY+"&language=en_US";

            AsyncHttpClient client = new AsyncHttpClient();
            client.get(url, new AsyncHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                    try {
                        String result = new String(responseBody);
                        JSONObject responseObject = new JSONObject(result);
                        JSONArray getResult = responseObject.getJSONArray("results");
                        jsonObject = responseObject;

                        for (int i = 0; i < getResult.length(); i++) {
                            JSONObject json = getResult.getJSONObject(i);
                            FilmItem film = new FilmItem(json);
                            filmItemArrayList.add(film);
                        }
                        listAdapter.ListFilm(filmItemArrayList);
                        rvNowPlayingFragment.setAdapter(listAdapter);
                    } catch (Exception e){
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

                }
            });
        } else {
            filmItemArrayList = savedInstanceState.getParcelableArrayList("listfilm");
            listAdapter.ListFilm(filmItemArrayList);
            rvNowPlayingFragment.setAdapter(listAdapter);
        }

    }

    /*
    @NonNull
    @Override
    public Loader<ArrayList<FilmItem>> onCreateLoader(int id, @Nullable Bundle args) {
        String filmList = "";
        if (bundle != null){
            filmList = bundle.getString(MOVIE);
        }
        return new NowPlayingUpcommingAdapter(getActivity(), filmList, "nowcoming");
    }

    @Override
    public void onLoadFinished(@NonNull Loader<ArrayList<FilmItem>> loader, ArrayList<FilmItem> data) {
        listAdapter.setData(data);
    }

    @Override
    public void onLoaderReset(@NonNull Loader<ArrayList<FilmItem>> loader) {
        listAdapter.setData(null);
    }
    */
}
