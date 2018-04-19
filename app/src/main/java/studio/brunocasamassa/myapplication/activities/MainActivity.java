package studio.brunocasamassa.myapplication.activities;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import studio.brunocasamassa.myapplication.R;
import studio.brunocasamassa.myapplication.adapters.MoviesTabAdapter;
import studio.brunocasamassa.myapplication.utils.SlidingTabLayout;

public class MainActivity extends AppCompatActivity {


    //TODO : GET LAYOUT MOVIE ACTIVITY
    //TODO : CREATE REQUEST MODELS
    //TODO : CREATE RETROFIT REQUEST AND LOGICS


    //TODO : IMPLEMENT ROBOELECTRIC AUTOMATION TEST


    @BindView(R.id.stl_tabs_palestras)
    SlidingTabLayout slidingTabLayout;

    @BindView(R.id.vp_palestras)
    ViewPager viewPager;

    private MoviesTabAdapter moviesTabs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);


        moviesTabs = new MoviesTabAdapter(getSupportFragmentManager());

        viewPager.setAdapter(moviesTabs);

        slidingTabLayout.setViewPager(viewPager);



    }
}