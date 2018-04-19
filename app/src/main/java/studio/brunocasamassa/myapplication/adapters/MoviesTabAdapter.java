package studio.brunocasamassa.myapplication.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import studio.brunocasamassa.myapplication.fragments.FragmentAllMoviesList;
import studio.brunocasamassa.myapplication.fragments.FragmentFavsMoviesList;

/**
 * Created by bruno on 17/04/2018.
 */

public class MoviesTabAdapter  extends FragmentStatePagerAdapter {

    private String[] tituloAbas = {" A L L  M O V I E S ", " F A V O R I T E S "};

    public MoviesTabAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {

        Fragment fragment = null;

        switch (position) {
            case 0:
                fragment = new FragmentAllMoviesList();
                break;
            case 1:
                fragment = new FragmentFavsMoviesList();
                //notifyDataSetChanged();
                break;
        }

        return fragment;

    }


    @Override
    public int getCount() {
        return tituloAbas.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tituloAbas[position];
    }


}
