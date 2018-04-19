package studio.brunocasamassa.myapplication.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import studio.brunocasamassa.myapplication.R;

/**
 * Created by bruno on 18/04/2018.
 */



public class MovieHolder extends RecyclerView.ViewHolder{

    @BindView(R.id.squareImageView)
    public ImageView imageMovie;
    @BindView(R.id.txtTitle)
    public TextView textTitle;
    @BindView(R.id.txtDate)
    public TextView textDate;
    @BindView(R.id.movieRating)
    public RatingBar ratingBar;

    public MovieHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this,itemView);

    }
}
