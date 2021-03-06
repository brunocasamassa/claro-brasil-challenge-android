package studio.brunocasamassa.myapplication.service;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;
import studio.brunocasamassa.myapplication.models.Movie;
import studio.brunocasamassa.myapplication.models.Results;
import studio.brunocasamassa.myapplication.models.Trailer;

/**
 * Created by bruno on 17/04/2018.
 */

public interface MoviedbResponse {

    @GET("movie/{type}")
    Call<List<Movie>> listMoviesByType(@Path("type") String type);

    @GET("discover/movie?api_key=5846f6b05e769c07777bbde7693357c4&language=en-US&sort_by=popularity.desc&include_adult=false&include_video=true")
    Observable<Results> listMoviesOb();

    @GET("discover/movie?api_key=5846f6b05e769c07777bbde7693357c4&language=en-US&sort_by=popularity.desc&include_adult=false&include_video=true")
    Call<Results> listMovies();


    @GET("movie/{id}/videos?api_key=5846f6b05e769c07777bbde7693357c4")
    Observable<Trailer> getMovieTrailers(@Path("id") String id);




}
