package id.web.wfebriadi.submission5.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
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
public class SearchFragment extends Fragment {

    public static String EXTRAS = "extras";
    String query = "";

    RecyclerView rvSearchFragment;
    NowPlayingUpcommingAdapter listAdapter;

    ArrayList<FilmItem> filmItemArrayList = new ArrayList<>();

    public SearchFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search, container, false);
    }
    public void onViewCreated(View v, Bundle savedInstanceState) {
        super.onViewCreated(v, savedInstanceState);

        filmItemArrayList = new ArrayList<>();
        query = getArguments().getString(EXTRAS);

        rvSearchFragment = (RecyclerView)v.findViewById(R.id.rv_search);
        rvSearchFragment.setHasFixedSize(true);
        rvSearchFragment.setLayoutManager(new LinearLayoutManager(this.getActivity()));

        listAdapter = new NowPlayingUpcommingAdapter(this.getActivity());

        if (savedInstanceState == null){
            String url = "https://api.themoviedb.org/3/search/movie?api_key=" + API_KEY + "&language=en-US&query=" + query;
            AsyncHttpClient client = new AsyncHttpClient();
            client.get(url, new AsyncHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                    try {
                        String result = new String(responseBody);
                        JSONObject responseObject = new JSONObject(result);
                        JSONArray getResult = responseObject.getJSONArray("results");

                        for (int i = 0; i < getResult.length(); i++) {
                            JSONObject json = getResult.getJSONObject(i);
                            FilmItem film = new FilmItem(json);
                            filmItemArrayList.add(film);
                        }

                        listAdapter.ListFilm(filmItemArrayList);
                        rvSearchFragment.setAdapter(listAdapter);

                    } catch (Exception e) {
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
            rvSearchFragment.setAdapter(listAdapter);
        }
        /*
        String url = "https://api.themoviedb.org/3/search/movie?api_key=" + API_KEY + "&language=en-US&query=" + query;
        AsyncHttpClient client = new AsyncHttpClient();
        client.get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try {
                    String result = new String(responseBody);
                    JSONObject responseObject = new JSONObject(result);
                    JSONArray getResult = responseObject.getJSONArray("results");

                    for (int i = 0; i < getResult.length(); i++) {
                        JSONObject json = getResult.getJSONObject(i);
                        FilmItem film = new FilmItem(json);
                        filmItemArrayList.add(film);
                    }

                    listAdapter.ListFilm(filmItemArrayList);
                    rvSearchFragment.setAdapter(listAdapter);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

            }
        });
        */
    }
}
