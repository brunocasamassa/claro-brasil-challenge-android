
package studio.brunocasamassa.myapplication.models;

import android.os.Bundle;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by bruno on 18/04/2018.
 *
 *
 *
 */


public class Movie implements Serializable {

    @SerializedName("vote_count")
    @Expose
    private Integer voteCount;
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("video")
    @Expose
    private Boolean video;
    @SerializedName("vote_average")
    @Expose
    private Double voteAverage;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("popularity")
    @Expose
    private Double popularity;
    @SerializedName("poster_path")
    @Expose
    private String posterPath;
    @SerializedName("original_language")
    @Expose
    private String originalLanguage;
    @SerializedName("original_title")
    @Expose
    private String originalTitle;
    @SerializedName("genre_ids")
    @Expose
    private List<Integer> genreIds = null;
    @SerializedName("backdrop_path")
    @Expose
    private String backdropPath;
    @SerializedName("adult")
    @Expose
    private Boolean adult;
    @SerializedName("overview")
    @Expose
    private String overview;
    @SerializedName("release_date")
    @Expose
    private String releaseDate;
    private final static long serialVersionUID = 1273349101429077108L;

    /**
     * No args constructor for use in serialization
     */
    public Movie() {
    }

    /**
     * @param genreIds
     * @param id
     * @param title
     * @param releaseDate
     * @param overview
     * @param posterPath
     * @param originalTitle
     * @param voteAverage
     * @param originalLanguage
     * @param adult
     * @param backdropPath
     * @param voteCount
     * @param video
     * @param popularity
     */
    public Movie(Integer voteCount, Integer id, Boolean video, Double voteAverage, String title, Double popularity, String posterPath, String originalLanguage, String originalTitle, List<Integer> genreIds, String backdropPath, Boolean adult, String overview, String releaseDate) {
        super();
        this.voteCount = voteCount;
        this.id = id;
        this.video = video;
        this.voteAverage = voteAverage;
        this.title = title;
        this.popularity = popularity;
        this.posterPath = posterPath;
        this.originalLanguage = originalLanguage;
        this.originalTitle = originalTitle;
        this.genreIds = genreIds;
        this.backdropPath = backdropPath;
        this.adult = adult;
        this.overview = overview;
        this.releaseDate = releaseDate;
    }


    public Movie(Bundle extras) {

        this.voteCount = extras.getInt("voteCount");
        this.id = extras.getInt("id");
        this.video = extras.getBoolean("video");
        this.voteAverage = extras.getDouble("voteAverage");
        this.title = extras.getString("title");
        this.popularity = extras.getDouble("popularity");
        this.posterPath = extras.getString("posterPath");
        this.originalLanguage = extras.getString("originaLanguage");
        this.originalTitle = extras.getString("originalTitle");
        this.genreIds = extras.getIntegerArrayList("genreIds");
        this.backdropPath = extras.getString("backdropPath");
        this.adult = extras.getBoolean("adult");
        this.overview = extras.getString("overview");
        this.releaseDate = extras.getString("releaseDate");
    }


    public Integer getVoteCount() {
        return voteCount;
    }

    public void setVoteCount(Integer voteCount) {
        this.voteCount = voteCount;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Boolean getVideo() {
        return video;
    }

    public void setVideo(Boolean video) {
        this.video = video;
    }

    public Double getVoteAverage() {
        return voteAverage;
    }

    public void setVoteAverage(Double voteAverage) {
        this.voteAverage = voteAverage;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Double getPopularity() {
        return popularity;
    }

    public void setPopularity(Double popularity) {
        this.popularity = popularity;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public String getOriginalLanguage() {
        return originalLanguage;
    }

    public void setOriginalLanguage(String originalLanguage) {
        this.originalLanguage = originalLanguage;
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public void setOriginalTitle(String originalTitle) {
        this.originalTitle = originalTitle;
    }

    public List<Integer> getGenreIds() {
        return genreIds;
    }

    public void setGenreIds(List<Integer> genreIds) {
        this.genreIds = genreIds;
    }

    public String getBackdropPath() {
        return backdropPath;
    }

    public void setBackdropPath(String backdropPath) {
        this.backdropPath = backdropPath;
    }

    public Boolean getAdult() {
        return adult;
    }

    public void setAdult(Boolean adult) {
        this.adult = adult;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }


    public Bundle getBundle() {

        Bundle bundle = new Bundle();

        bundle.putInt("voteCount", this.getVoteCount());
        bundle.putInt("id", this.getId());
        bundle.putBoolean("video", this.getVideo());
        bundle.putDouble("voteAverage", this.getVoteAverage());
        bundle.putString("title", this.getTitle());
        bundle.putDouble("popularity", this.getPopularity());
        bundle.putString("posterPath", this.getPosterPath());
        bundle.putString("originaLanguage", this.getOriginalLanguage());
        bundle.putString("originalTitle", this.getOriginalTitle());
        bundle.putIntegerArrayList("genreIds", (ArrayList<Integer>) this.getGenreIds());
        bundle.putString("backdropPath", this.getBackdropPath());
        bundle.putBoolean("adult", this.getAdult());
        bundle.putString("overview", this.getOverview());
        bundle.putString("releaseDate", this.getReleaseDate());


        return bundle;
    }
}
