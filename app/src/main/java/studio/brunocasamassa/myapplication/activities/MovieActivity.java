package studio.brunocasamassa.myapplication.activities;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import studio.brunocasamassa.myapplication.R;
import studio.brunocasamassa.myapplication.models.Movie;
import studio.brunocasamassa.myapplication.models.Result;
import studio.brunocasamassa.myapplication.models.Trailer;
import studio.brunocasamassa.myapplication.service.MoviedbResponse;
import studio.brunocasamassa.myapplication.utils.Tools;

public class MovieActivity extends AppCompatActivity {


    @BindView(R.id.txtOriginalTitle)
    TextView txtOriginalTitle;
    @BindView(R.id.movieRating)
    RatingBar movieRating;
    @BindView(R.id.txtDate)
    TextView txtDate;
    @BindView(R.id.txtDescription)
    TextView txtDescription;
    @BindView(R.id.squareImageView)
    ImageView squareImageView;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.play_button)
    ImageView playButton;


    private Observable<Trailer> responseTrailers;
    private Movie movie;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie);
        ButterKnife.bind(this);

        Bundle extras = getIntent().getExtras();

        movie = new Movie(extras);

        setToolbar();

        //SET VIEW
        txtOriginalTitle.setText(movie.getTitle());
        movieRating.setProgress(movie.getVoteAverage().intValue());
        Drawable progress = movieRating.getProgressDrawable();
        DrawableCompat.setTint(progress, Color.YELLOW);
        txtDate.setText(movie.getReleaseDate());
        txtDescription.setText(movie.getOverview());

        Picasso.with(getApplicationContext()).load(getResources().getString(R.string.image_base_url) + movie.getPosterPath()).fit().into(squareImageView);

        if (!Tools.verifyStatusConnection(getApplicationContext())) {
            Toast.makeText(MovieActivity.this, "Sem conexão com a internet", Toast.LENGTH_SHORT).show();
        } else {

        try {
            getRequest(getResources().getString(R.string.base_url));
        } catch (Exception e) {
            e.printStackTrace();
        }}


    }

    private void setToolbar() {

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(movie.getTitle());
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(v -> finish());

    }

    private void getRequest(String string) {

        responseTrailers = initRetrofit(string).getMovieTrailers(String.valueOf(movie.getId()));

        responseTrailers.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(responseTrailers -> {
                    if (responseTrailers != null) {
                        for (final Result trailer : responseTrailers.getResults()) {
                             Log.d("TRAILER KEY IN ARRAY: ", trailer.getKey());
                            if (trailer.getKey() != null) {
                                playButton.setVisibility(View.VISIBLE);
                                playButton.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        playTrailer(getResources().getString(R.string.video_base_url) + trailer.getKey());

                                    }
                                });

                            }


                        }
                    }
                });


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

    public void playTrailer(String uri) {

        if (!uri.equals("")) {

            uri = uri + "?autoplay=1&vq=small";
            Uri uri2 = Uri.parse(uri);
            Intent intent = new Intent(Intent.ACTION_VIEW, uri2);
            startActivity(intent);
        }

    }
}




