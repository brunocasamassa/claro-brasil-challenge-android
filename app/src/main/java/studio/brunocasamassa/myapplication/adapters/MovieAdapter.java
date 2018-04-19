package studio.brunocasamassa.myapplication.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import studio.brunocasamassa.myapplication.R;
import studio.brunocasamassa.myapplication.activities.MovieActivity;
import studio.brunocasamassa.myapplication.holder.MovieHolder;
import studio.brunocasamassa.myapplication.models.Movie;

/**
 * Created by bruno on 18/04/2018.
 */

public class MovieAdapter extends RecyclerView.Adapter<MovieHolder> {

    private ArrayList<Movie> movieList;
    private Context context;

    public MovieAdapter(ArrayList<Movie> moviesList, Context context) {
        this.movieList = moviesList;
        this.context = context;
    }

    @Override
    public MovieHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MovieHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.models_movie, parent, false));
    }

    @Override
    public void onBindViewHolder(MovieHolder holder, final int position) {
        try {
            holder.textTitle.setText(movieList.get(position).getTitle());
            System.out.println("NAAAAAA"+ movieList.get(position).getPosterPath());
            holder.textDate.setText(movieList.get(position).getReleaseDate());

            Picasso.with(context).load(context.getResources().getString(R.string.image_base_url) + movieList.get(position).getPosterPath()).fit().into(holder.imageMovie);
            holder.ratingBar.setProgress(movieList.get(position).getVoteAverage().intValue());
            System.out.println("NAAAAAA2");
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, MovieActivity.class);
                    intent.putExtras(movieList.get(position).getBundle());
                    ((Activity) context).startActivity(intent);

                }
            });

        } catch (NullPointerException n) {
            n.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return movieList.size();
    }
}
