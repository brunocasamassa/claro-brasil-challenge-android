package studio.brunocasamassa.myapplication.fragments;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import studio.brunocasamassa.myapplication.R;
import studio.brunocasamassa.myapplication.adapters.MovieAdapter;
import studio.brunocasamassa.myapplication.models.Movie;
import studio.brunocasamassa.myapplication.models.Results;
import studio.brunocasamassa.myapplication.service.HttpRequestCode;
import studio.brunocasamassa.myapplication.service.MoviedbResponse;
import studio.brunocasamassa.myapplication.utils.Tools;

/**
 * Created by bruno on 17/04/2018.
 */

public class FragmentAllMoviesList extends Fragment implements HttpRequestCode {

    @Override
    public void onResume() {
        super.onResume();

        //verify connection and request
        if (!Tools.verifyStatusConnection(getActivity())) {
            Toast.makeText(getActivity(), "Sem conexão com a internet", Toast.LENGTH_SHORT).show();
        } else {
            try {
                getRequest(getResources().getString(R.string.base_url));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }


    @BindView(R.id.list_movies_all)
    RecyclerView viewMovies;

    private Observable<Results> results;
    private ArrayList<Movie> moviesList;
    private MovieAdapter movieAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_movies_list, container, false);
        System.out.println(" STATUS CREATED");
        ButterKnife.bind(this, v);

        GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), 2);
        viewMovies.setLayoutManager(layoutManager);



        return v;
    }


    private void getRequest(String string) {

        MoviedbResponse moviedbResponse = initRetrofit(string);

        results = moviedbResponse.listMoviesOb();

        results.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(results -> {

                    if (results != null) {
                        moviesList = new ArrayList<>();

                        for (Movie movie : results.getResults()) {
                            Log.d("MOVIE IN ARRAY: ", movie.getTitle());

                            moviesList.add(movie);

                            setMoviesList(moviesList);

                        }
                    }
                    ;
                });

        results.doOnError(throwable -> {

                VerifyRequestStatus verifyRequestStatus = new VerifyRequestStatus(getActivity(), moviedbResponse);
                verifyRequestStatus.execute();

        });

        return;
    }


    private void setMoviesList(ArrayList<Movie> moviesList) {

        movieAdapter = new MovieAdapter(moviesList, getActivity(), "ALLMOVIES");
        movieAdapter.notifyItemInserted(movieAdapter.getItemCount());
        viewMovies.setAdapter(movieAdapter);

    }


    //USED IN UNIT TESTS
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
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();


        MoviedbResponse service = retrofit.create(MoviedbResponse.class);

        return service;

    }

    @Override
    public void onReceiveRequestCode(int httpRequestCode) {


    }
}

class VerifyRequestStatus extends AsyncTask<String, Integer, String> implements HttpRequestCode {

    private FragmentActivity context;
    private MoviedbResponse moviedbResponse;

    public VerifyRequestStatus(FragmentActivity activity, MoviedbResponse moviedbResponse) {
        this.context = activity;
        this.moviedbResponse = moviedbResponse;
    }


    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected String doInBackground(String... strings) {

        try {
            int httpStatus = moviedbResponse.listMovies().execute().code();
            this.onReceiveRequestCode(httpStatus);
        } catch (UnknownHostException u) {
            u.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception ee) {
            ee.printStackTrace();
        }

        return null;
    }

    @Override
    public void onReceiveRequestCode(int httpRequestCode) {
        Log.d("HTTP STATUS CODE: ", String.valueOf(httpRequestCode));

        int HTTP_OK = 200;

        if (HTTP_OK != httpRequestCode) {

            String error = "";

            switch (httpRequestCode) {
                case 404:
                    error = "Requisição não encontrada, favor comunicar o desenvolvedor";
                case 408:
                    error = "Requisição atingiu o tempo limite, verifique sua conexão e tente novamente";
                case 500:
                    error = "Erro de servidor, tente novamente mais tarde";
                case 503:
                    error = "Servidor em manutenção";
                default:

                    Toast.makeText(context, error, Toast.LENGTH_SHORT).show();
            }

        }

    }
}