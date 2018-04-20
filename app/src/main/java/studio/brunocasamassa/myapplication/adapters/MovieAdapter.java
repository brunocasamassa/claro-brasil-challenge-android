package studio.brunocasamassa.myapplication.adapters;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import studio.brunocasamassa.myapplication.R;
import studio.brunocasamassa.myapplication.activities.MovieActivity;
import studio.brunocasamassa.myapplication.holder.MovieHolder;
import studio.brunocasamassa.myapplication.models.Movie;
import studio.brunocasamassa.myapplication.utils.SessionManager;

/**
 * Created by bruno on 18/04/2018.
 */

public class MovieAdapter extends RecyclerView.Adapter<MovieHolder> {

    private ArrayList<Movie> movieList;
    private Activity context;
    private String cameFrom;

    public MovieAdapter(ArrayList<Movie> moviesList, Activity context, String cameFrom) {
        this.movieList = moviesList;
        this.context = context;
        this.cameFrom = cameFrom;
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

            holder.textDate.setText(movieList.get(position).getReleaseDate());

            Picasso.with(context).load(context.getResources().getString(R.string.image_base_url) + movieList.get(position).getPosterPath()).fit().into(holder.imageMovie);
            holder.ratingBar.setProgress(movieList.get(position).getVoteAverage().intValue());
            Drawable progress = holder.ratingBar.getProgressDrawable();
            DrawableCompat.setTint(progress, Color.YELLOW);

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, MovieActivity.class);
                    intent.putExtras(movieList.get(position).getBundle());
                    ((Activity) context).startActivity(intent);

                }
            });

            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    if (cameFrom.equals("ALLMOVIES")) {

                        String movieName = movieList.get(position).getTitle();
                        AlertDialog.Builder alert = new AlertDialog.Builder(context);
                        alert.setTitle("Favoritar Filme");
                        alert.setTitle("Deseja adicionar " + movieName + " a sua lista de favoritos?");
                        alert.setPositiveButton("SIM", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                SessionManager session = new SessionManager(context);
                                session.updateFavorites(movieList.get(position), context);

                            }
                        }).setNegativeButton("NÃO", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        }).show();

                    } else if (cameFrom.equals("FAVORITES")) {
                        Toast.makeText(context, "Filme já favoritado", Toast.LENGTH_SHORT).show();

                        String movieName = movieList.get(position).getTitle();
                        AlertDialog.Builder alert = new AlertDialog.Builder(context);
                        alert.setTitle("Excluir Filme");
                        alert.setTitle("Deseja excluir " + movieName + " da sua lista de favoritos?");
                        alert.setPositiveButton("SIM", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                SessionManager session = new SessionManager(context);
                                session.removeFavorites(movieList.get(position), context);

                            }
                        }).setNegativeButton("NÃO", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        }).show();
                    }
                    return false;
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
