package id.web.wfebriadi.submission5.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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
public class UpcommingFragment extends Fragment {

    static final String TAG = UpcommingFragment.class.getSimpleName();
    public static final String EXTRAS = "extras";

    RecyclerView rvUpcommingFragment;
    NowPlayingUpcommingAdapter listAdapter;
    ArrayList<FilmItem> filmItemArrayList = new ArrayList<>();
    Bundle bundle;
    private JSONObject jsonObject;

    public UpcommingFragment() {
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
        return inflater.inflate(R.layout.fragment_upcomming, container, false);
    }
    public void onViewCreated(View v, Bundle savedInstanceState) {
        //super.onViewCreated(v, savedInstanceState);

        rvUpcommingFragment = (RecyclerView)v.findViewById(R.id.rv_upcoming);
        rvUpcommingFragment.setLayoutManager(new LinearLayoutManager(this.getActivity()));
        rvUpcommingFragment.setHasFixedSize(true);

        listAdapter = new NowPlayingUpcommingAdapter(this.getActivity());
        bundle = new Bundle();

        if (savedInstanceState == null){
            String url = "https://api.themoviedb.org/3/movie/upcoming?api_key=" + API_KEY + "&language=en-US";
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
                            Log.d(TAG, "list length: "+filmItemArrayList.size());
                        }
                        listAdapter.ListFilm(filmItemArrayList);
                        Log.d(TAG, "tes: "+listAdapter.getFilmItemArrayList());
                        rvUpcommingFragment.setAdapter(listAdapter);
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
            rvUpcommingFragment.setAdapter(listAdapter);
        }

    }
}
