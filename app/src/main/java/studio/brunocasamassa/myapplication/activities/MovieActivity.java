package studio.brunocasamassa.myapplication.activities;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import studio.brunocasamassa.myapplication.R;
import studio.brunocasamassa.myapplication.models.Movie;
import studio.brunocasamassa.myapplication.models.Result;
import studio.brunocasamassa.myapplication.models.Trailer;
import studio.brunocasamassa.myapplication.service.MoviedbResponse;

public class MovieActivity extends AppCompatActivity {


    @BindView(R.id.videoView)
    VideoView videoVIew;
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


    private Call<Trailer> responseTrailers;
    private ArrayList<Movie> moviesList;
    private Movie movie;
    private boolean hasTrailer;
    private String uri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie);
        ButterKnife.bind(this);

        Bundle extras = getIntent().getExtras();

        movie = new Movie(extras);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(movie.getTitle());
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        txtOriginalTitle.setText(movie.getTitle());
        movieRating.setProgress(movie.getVoteAverage().intValue());
        Drawable progress = movieRating.getProgressDrawable();
        DrawableCompat.setTint(progress, Color.YELLOW);

        txtDate.setText(movie.getReleaseDate());
        txtDescription.setText(movie.getOverview());

        Picasso.with(getApplicationContext()).load(getResources().getString(R.string.image_base_url) + movie.getPosterPath()).fit().into(squareImageView);


        try {
            getRequest(getResources().getString(R.string.base_url));
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    private void getRequest(String string) {

        responseTrailers = initRetrofit(string).getMovieTrailers(String.valueOf(movie.getId()));

        responseTrailers.enqueue(new Callback<Trailer>() {

            @Override
            public void onResponse(Call<Trailer> call, Response<Trailer> response) {


                try {

                    Log.d("RESPON", response.headers().toMultimap().toString());

                    if (response.isSuccessful()) {
                        Trailer trailers = response.body();
                        //Log.d("RESPONSE BODY: ", response.body().getResults().listIterator().toString());

                        for (final Result trailer : trailers.getResults()) {
                            // Log.d("TRAILER KEY IN ARRAY: ", trailer.getKey());

                            if (trailer.getKey() != null) {
                                playButton.setVisibility(View.VISIBLE);
                                playButton.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        playTrailer(videoVIew, getResources().getString(R.string.video_base_url), MovieActivity.this);

                                    }
                                });

                            }


                        }
                    }
                } catch (NullPointerException n) {
                    n.printStackTrace();
                    Toast.makeText(getApplicationContext(), "Sem Trailer disponível", Toast.LENGTH_SHORT).show();
                }

            }


            @Override
            public void onFailure(Call<Trailer> call, Throwable t) {

                String error = t.getMessage();

                Toast.makeText(MovieActivity.this, error, Toast.LENGTH_SHORT).show();

            }

        });

    }


    private MoviedbResponse initRetrofit(String base_url) {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(base_url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        MoviedbResponse service = retrofit.create(MoviedbResponse.class);
        return service;

        // System.out.println("RSPON "+service.getMovieTrailers(base_url).execute().headers().);
    }

    public void playTrailer(VideoView videoVIew, String uri, Activity activity) {
        //VIDEO VIEW
        try {
            /*final ProgressBar progressBar = (ProgressBar) findViewById(R.id.progress_video);
            Drawable draw = getResources().getDrawable(R.drawable.custom_progreesbar);
            progressBar.setProgressDrawable(draw);
*/
            if (uri.equals("")) {

                // progressBar.setVisibility(View.GONE);

            } else {
                final MediaController mediaControls;
                mediaControls = new MediaController(activity);
                mediaControls.setAnchorView(videoVIew);

                videoVIew.setMediaController(mediaControls);
                Uri path1 = Uri.parse(uri);
                System.out.println("VIDEO LINK " + uri);

                videoVIew.setVideoURI(path1);
                videoVIew.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                    @Override
                    public void onPrepared(MediaPlayer mp) {
                        //mediaControls.setEnabled(true);
                        //progressBar.setVisibility(View.GONE);
                    }
                });


                DisplayMetrics metrics = new DisplayMetrics();
                getWindowManager().getDefaultDisplay().getMetrics(metrics);
                android.widget.LinearLayout.LayoutParams params = (android.widget.LinearLayout.LayoutParams) videoVIew.getLayoutParams();
                params.width = metrics.widthPixels;
                params.height = metrics.heightPixels;
                params.leftMargin = 0;
                videoVIew.setLayoutParams(params);

                videoVIew.start();
                videoVIew.pause();
            }
        } catch (Exception e) {
            System.out.println("ERROR VIDEO " + e.getMessage());
            e.printStackTrace();
        }


    }

}
