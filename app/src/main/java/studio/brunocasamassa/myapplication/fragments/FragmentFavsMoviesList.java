package studio.brunocasamassa.myapplication.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import studio.brunocasamassa.myapplication.R;
import studio.brunocasamassa.myapplication.adapters.MovieAdapter;
import studio.brunocasamassa.myapplication.models.Movie;
import studio.brunocasamassa.myapplication.utils.SessionManager;

/**
 * Created by bruno on 17/04/2018.
 */

public class FragmentFavsMoviesList extends Fragment{
    @Override
    public void onInflate(Context context, AttributeSet attrs, Bundle savedInstanceState) {
        super.onInflate(context, attrs, savedInstanceState);

    }

    @Override
    public void onResume() {
        super.onResume();

    }

    @BindView(R.id.list_movies_favorites)
    RecyclerView viewMoviesFavs;

    private ArrayList<Movie> moviesList;
    private MovieAdapter movieAdapter;

    private SessionManager sessionManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_movies_favorites_list, container, false);
        ButterKnife.bind(this, v);

        sessionManager = new SessionManager(getActivity());

        moviesList = new ArrayList<>();
        moviesList.addAll(sessionManager.getMoviesDetails());

        Log.d("DEBUG FAVSmOVIES" , String.valueOf(moviesList.size()));

        GridLayoutManager layoutManager = new GridLayoutManager(getActivity(),2);
        viewMoviesFavs.setLayoutManager(layoutManager);

        setMoviesList(moviesList);

        return v;
    }



    private void setMoviesList(ArrayList<Movie> moviesList) {

        movieAdapter = new MovieAdapter(moviesList, getActivity(), "FAVORITES");
        movieAdapter.notifyItemInserted(movieAdapter.getItemCount());
        viewMoviesFavs.setAdapter(movieAdapter);

    }
}
