package studio.brunocasamassa.myapplication.utils;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import studio.brunocasamassa.myapplication.models.Movie;


/**
 * Created by bruno on 19/04/2018.
 *
 *
 */

public class SessionManager {

    SharedPreferences pref;

    // Editor for Shared preferences
    SharedPreferences.Editor editor;

    // Context
    Context _context;

    // Sharedpref  key name
    private static final String PREF_NAME = "movieDB";

    private static final String _MOVIELIST = "MovieList";


    public SessionManager(Context _context) {
        this._context = _context;
        pref = _context.getSharedPreferences(PREF_NAME, 0);
        editor = pref.edit();
    }


    public void updateFavorites(Movie movie,Activity activity) {

        try {

            Gson gson = new Gson();
            String jsonMovie = gson.toJson(movie);

            System.out.println("JSONMOVIE "+ jsonMovie);

            Set<String> stringSet = new HashSet<String>();

            stringSet.addAll(pref.getStringSet(_MOVIELIST,stringSet));

            stringSet.add(jsonMovie);

            editor.putStringSet(_MOVIELIST, stringSet);
            editor.commit();

            Toast.makeText(_context, "Filme favoritado com sucesso", Toast.LENGTH_SHORT).show();
            Tools.refreshAll(activity);

        } catch (NullPointerException n) {
            n.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(_context, "Erro ao favoritar filme", Toast.LENGTH_SHORT).show();
            Log.e("ERROR SHARED ", e.getMessage());
        }

    }

    public ArrayList<Movie> getMoviesDetails() {

        ArrayList<Movie> favoriteMovies = new ArrayList<>();
        ArrayList<String> favoriteMoviesString = new ArrayList<>();

        Gson gson = new Gson();

        try {

            favoriteMoviesString.addAll(convertToArray(pref.getStringSet(_MOVIELIST, null)));

            for (int i = 0; i < favoriteMoviesString.size(); i++) {

                favoriteMovies.add(gson.fromJson(favoriteMoviesString.get(i), Movie.class));
            }
        } catch (NullPointerException n) {
            n.printStackTrace();

        }

        return favoriteMovies;

    }


    private ArrayList<String> convertToArray(Set<String> stringSet) {
        ArrayList<String> arrayList = new ArrayList<>();

        try {

            for (String string : stringSet) {
                arrayList.add(string);
                System.out.println("convertToArray" + string);
            }

        } catch (NullPointerException e) {
            e.printStackTrace();
            Log.e("ERROR SET TO ARRAY", e.toString());
            if (!arrayList.isEmpty()) {
                return arrayList;
            }
        } catch (Exception x) {
            Log.e("ERROR SET TO ARRAY", x.toString());
        }

        return arrayList;

    }

    public void clearSession() {
        editor.clear();
        editor.commit();

    }


}
