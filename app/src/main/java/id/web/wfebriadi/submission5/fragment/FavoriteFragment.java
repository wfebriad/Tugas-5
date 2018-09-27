package id.web.wfebriadi.submission5.fragment;


import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import id.web.wfebriadi.submission5.R;
import id.web.wfebriadi.submission5.adapter.FavoriteAdapter;

import static id.web.wfebriadi.submission5.database.DatabaseContract.CONTENT_URI;

/**
 * A simple {@link Fragment} subclass.
 */
public class FavoriteFragment extends Fragment {

    static final String TAG = FavoriteFragment.class.getSimpleName();
    public static final String EXTRAS = "extras";

    RecyclerView rvFavoriteFragment;
    private Cursor listCursor;
    private FavoriteAdapter favoriteAdapter;

    public FavoriteFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_favorite, container, false);
    }

    public void onViewCreated (View v, Bundle savedInstanceState) {
        rvFavoriteFragment = (RecyclerView)v.findViewById(R.id.rvListFavoriteFragment);
        rvFavoriteFragment.setLayoutManager(new LinearLayoutManager(this.getActivity()));
        rvFavoriteFragment.setHasFixedSize(true);

        favoriteAdapter = new FavoriteAdapter(this.getActivity());
        favoriteAdapter.setFilm(listCursor);
        rvFavoriteFragment.setAdapter(favoriteAdapter);
        new LoadData().execute();
    }
    class LoadData extends AsyncTask<Void, Void, Cursor> {

        @Override
        protected Cursor doInBackground(Void... voids) {
            return getActivity().getContentResolver()
                    .query(CONTENT_URI, null, null, null, null);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(Cursor cursor) {
            super.onPostExecute(cursor);
            listCursor = cursor;
            favoriteAdapter.setFilm(listCursor);
            favoriteAdapter.notifyDataSetChanged();

            if (listCursor.getCount()== 0){
                Toast.makeText(getActivity(), "Tidak ada data", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
