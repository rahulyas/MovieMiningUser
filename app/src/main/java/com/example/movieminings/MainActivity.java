package com.example.movieminings;

import android.app.ActivityOptions;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.example.movieminings.Adapter.movieshowadapter;
import com.example.movieminings.Adapter.sliderpageradapter;
import com.example.movieminings.Model.getvideodetails;
import com.example.movieminings.Model.movieitemClickListern;
import com.example.movieminings.Model.sliderside;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity implements movieitemClickListern {

    movieshowadapter moviesShowAdapter;
    DatabaseReference mDatabaserefence ;
    private List<getvideodetails> uploads, uploadsListlatest,uploadsListpopular;
    private List<getvideodetails> actionmovies, sportmovies,comedymovies,romanticmovies,advanturemovies;

    private ViewPager sliderpager;
    private List<sliderside> uploadsslider ;

    private TabLayout indicator,tabActionMovies;
    private RecyclerView MoviesRV ,moviesRvWeek ,tab;

    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.actionbar);

        progressDialog = new ProgressDialog(this);
        iniViews();
        addAllMovies();
        iniPopularMovies();
        // iniSlider();
        iniWeekMovies();
        movieviewtab();
        // getActionMovies();
        askPermission();


    }

    private void addAllMovies(){
        uploads = new ArrayList<>();
        uploadsListlatest = new ArrayList<>();
        uploadsListpopular = new ArrayList<>();
        actionmovies = new ArrayList<>();
        sportmovies = new ArrayList<>();
        uploadsslider = new ArrayList<>();
        advanturemovies = new ArrayList<>();
        comedymovies = new ArrayList<>();
        romanticmovies = new ArrayList<>();


        mDatabaserefence = FirebaseDatabase.getInstance().getReference("videos");
        progressDialog.setMessage("loading....");
        progressDialog.show();

        mDatabaserefence.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    getvideodetails upload = postSnapshot.getValue(getvideodetails.class);
                    sliderside slide = postSnapshot.getValue(sliderside.class);
                    if(upload.getVideo_type().equals("latest movies")){
                        uploadsListlatest.add(upload);

                    }else if(upload.getVideo_type().equals("Best popular movies"))
                    {
                        uploadsListpopular.add(upload);
                    }
                    if(upload.getVideo_category().equals("Action")){
                        actionmovies.add(upload);
                    }else if(upload.getVideo_category().equals("Sports")){
                        sportmovies.add(upload);
                    }if(upload.getVideo_category().equals("Adventure")){
                        advanturemovies.add(upload);
                    } else if(upload.getVideo_category().equals("Comedy")){
                        comedymovies.add(upload);
                    }
                    if(upload.getVideo_category().equals("Romantic")){
                        romanticmovies.add(upload);
                    }


                    if(upload.getVideo_slide().equals("Slide movies")){
                        uploadsslider.add(slide);
                    }
                    uploads.add(upload);

                }
                iniSlider();
                progressDialog.dismiss();

            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

                progressDialog.dismiss();
            }
        });

    }

    private void iniWeekMovies() {

        moviesShowAdapter = new movieshowadapter(this, uploadsListlatest,this);
        moviesRvWeek.setAdapter(moviesShowAdapter);
        moviesRvWeek.setLayoutManager(new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.HORIZONTAL,false));
        moviesShowAdapter.notifyDataSetChanged();

    }
    private void iniPopularMovies() {
        moviesShowAdapter = new movieshowadapter(this, uploadsListpopular,this);
        //adding adapter to recyclerview
        MoviesRV.setAdapter(moviesShowAdapter);
        MoviesRV.setLayoutManager(new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.HORIZONTAL,false));
        moviesShowAdapter.notifyDataSetChanged();


    }

    private void movieviewtab() {
        getActionMovies();
        tabActionMovies.addTab(tabActionMovies.newTab().setText("Action"));
        tabActionMovies.addTab(tabActionMovies.newTab().setText("Advanture"));
        tabActionMovies.addTab(tabActionMovies.newTab().setText("Comedy"));
        tabActionMovies.addTab(tabActionMovies.newTab().setText("Romantic"));
        tabActionMovies.addTab(tabActionMovies.newTab().setText("Sports"));
        tabActionMovies.setTabGravity(TabLayout.GRAVITY_FILL);
        tabActionMovies.setTabTextColors(ColorStateList.valueOf(Color.WHITE));

        tabActionMovies.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

                switch (tab.getPosition()) {
                    case 0:
                        getActionMovies();
                        break;
                    case 1:
                        getAdvantureMovies();
                        break;
                    case 2:
                        getComedyMovies();
                        break;
                    case 3:
                        getRomanticMovies();
                        break;
                    case 4:
                        getSportMovies();
                        break;
                }

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });




    }

    private void iniSlider() {
        //uploadsslider = new ArrayList<>();
        sliderpageradapter adapterslider = new sliderpageradapter(this,uploadsslider);
        sliderpager.setAdapter(adapterslider);
        adapterslider.notifyDataSetChanged();
        // setup timer
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new SliderTimer(),4000,6000);
        indicator.setupWithViewPager(sliderpager,true);

    }



    private void iniViews() {
        tabActionMovies = findViewById(R.id.tabActionMovies);
        sliderpager = findViewById(R.id.slider_pager) ;
        indicator = findViewById(R.id.indicator);
        MoviesRV = findViewById(R.id.Rv_movies);
        moviesRvWeek = findViewById(R.id.rv_movies_week);
        tab = findViewById(R.id.tabrecyler);


    }


    @Override
    public void onMovieClick(getvideodetails movie, ImageView movieImageView) {

        Intent intent = new Intent(this, MovieDetail.class);
        // send movie information to deatilActivity
        intent.putExtra("title",movie.getVideo_name());
        intent.putExtra("imgURL",movie.getVideo_thumb());
        intent.putExtra("imgCover",movie.getVideo_thumb());
        intent.putExtra("movieDetails",movie.getVideo_description());
        intent.putExtra("movieUrl",movie.getVideo_url());
        intent.putExtra("movieCategory",movie.getVideo_category());

        ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(MainActivity.this,
                movieImageView,"sharedName");

        startActivity(intent,options.toBundle());
    }

    public class SliderTimer extends TimerTask {


        @Override
        public void run() {

            MainActivity.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (sliderpager.getCurrentItem()<uploadsslider.size()-1) {
                        sliderpager.setCurrentItem(sliderpager.getCurrentItem()+1);
                    }
                    else
                        sliderpager.setCurrentItem(0);
                }
            });


        }
    }



    private void getActionMovies(){
        moviesShowAdapter = new movieshowadapter(this, actionmovies,this);
        //adding adapter to recyclerview
        tab.setAdapter(moviesShowAdapter);
        tab.setLayoutManager(new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.HORIZONTAL,false));
        moviesShowAdapter.notifyDataSetChanged();
        Toast.makeText(MainActivity.this, "Tab Action Seleted:", Toast.LENGTH_SHORT).show();
    }

    private void getSportMovies(){
        moviesShowAdapter = new movieshowadapter(this, sportmovies,this);
        //adding adapter to recyclerview
        tab.setAdapter(moviesShowAdapter);
        tab.setLayoutManager(new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.HORIZONTAL,false));
        moviesShowAdapter.notifyDataSetChanged();

    }

    private void getAdvantureMovies(){
        moviesShowAdapter = new movieshowadapter(this, advanturemovies,this);
        //adding adapter to recyclerview
        tab.setAdapter(moviesShowAdapter);
        tab.setLayoutManager(new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.HORIZONTAL,false));
        moviesShowAdapter.notifyDataSetChanged();

    }

    private void getRomanticMovies(){
        moviesShowAdapter = new movieshowadapter(this, romanticmovies,this);
        //adding adapter to recyclerview
        tab.setAdapter(moviesShowAdapter);
        tab.setLayoutManager(new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.HORIZONTAL,false));
        moviesShowAdapter.notifyDataSetChanged();

    }

    private void getComedyMovies(){
        moviesShowAdapter = new movieshowadapter(this, comedymovies,this);
        //adding adapter to recyclerview
        tab.setAdapter(moviesShowAdapter);
        tab.setLayoutManager(new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.HORIZONTAL,false));
        moviesShowAdapter.notifyDataSetChanged();

    }


    public void askPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && !Settings.canDrawOverlays(this)) {
            Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                    Uri.parse("package:" + getPackageName()));
            startActivityForResult(intent, 2004);

        }
    }

}