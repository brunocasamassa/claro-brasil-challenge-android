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

import java.io.IOException;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import studio.brunocasamassa.myapplication.R;
import studio.brunocasamassa.myapplication.adapters.MovieAdapter;
import studio.brunocasamassa.myapplication.models.Movie;
import studio.brunocasamassa.myapplication.models.Results;
import studio.brunocasamassa.myapplication.service.HttpRequestCode;
import studio.brunocasamassa.myapplication.service.MoviedbResponse;

/**
 * Created by bruno on 17/04/2018.
 */

public class FragmentAllMoviesList extends Fragment implements HttpRequestCode {

    @Override
    public void onInflate(Context context, AttributeSet attrs, Bundle savedInstanceState) {
        super.onInflate(context, attrs, savedInstanceState);

        System.out.println(" STATUS INFLATED");

    }

    @Override
    public void onResume() {
        super.onResume();

    }


    @BindView(R.id.list_movies_all)
    RecyclerView viewMovies;

    private Call<Results> results;
    private ArrayList<Movie> moviesList;
    private MovieAdapter movieAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_movies_list, container, false);
        System.out.println(" STATUS CREATED");
        ButterKnife.bind(this, v);

        GridLayoutManager layoutManager = new GridLayoutManager(getActivity(),2);
        viewMovies.setLayoutManager(layoutManager);

        try {
            getRequest(getResources().getString(R.string.base_url),this);
        } catch (Exception e) {
            e.printStackTrace();
        }



        return v;
}

    private void getRequest(String string , final HttpRequestCode httpRequestCode) {
        results = initRetrofit(string).listMovies();

        results.enqueue(new Callback<Results>() {

            @Override
            public void onResponse(Call<Results> call, Response<Results> response) {

                httpRequestCode.onReceiveRequestCode(response.code());

                if (response.isSuccessful()) {
                    Results results = response.body();
                    Log.d("RESPONSE BODY: ",response.body().getResults().toArray().toString());
                    moviesList = new ArrayList<>();

                    for (final Movie movie : results.getResults()) {
                        Log.d("MOVIE IN ARARY: ",movie.getTitle());

                        moviesList.add(movie);

                        setMoviesList(moviesList);

                    }


                }


            }


            @Override
            public void onFailure(Call<Results> call, Throwable t) {

            }

        });

    }

    private void setMoviesList(ArrayList<Movie> moviesList) {

            movieAdapter = new MovieAdapter(moviesList, getContext());
            movieAdapter.notifyItemInserted(movieAdapter.getItemCount());
            viewMovies.setAdapter(movieAdapter);

    }


    public int getHttpCodeStatus() {
        try {
            int status = initRetrofit(getResources().getString(R.string.base_url)).listMovies().execute().code();
            return status;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return 0;
    }
    private MoviedbResponse initRetrofit(String base_url) {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(base_url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        MoviedbResponse service = retrofit.create(MoviedbResponse.class);
        return service;
    }

    @Override
    public void onReceiveRequestCode(int httpCode) {

        Log.d("HTTP STATUS CODE: ",String.valueOf(httpCode));
    }
}
