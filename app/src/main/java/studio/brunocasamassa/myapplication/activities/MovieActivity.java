package studio.brunocasamassa.myapplication.activities;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import studio.brunocasamassa.myapplication.R;
import studio.brunocasamassa.myapplication.models.Movie;

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
    @OnClick(R.id.play_button)
    public void playTrailer() {


    }

    private Movie movie;
    private boolean hasTrailer;

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

        /*
        if(!hasTrailer){
            playButton.setVisibility(View.GONE);
        } */

        txtDate.setText(movie.getReleaseDate());
        txtDescription.setText(movie.getOverview());

        Picasso.with(getApplicationContext()).load(getResources().getString(R.string.image_base_url) + movie.getPosterPath()).fit().into(squareImageView);

    }
}
