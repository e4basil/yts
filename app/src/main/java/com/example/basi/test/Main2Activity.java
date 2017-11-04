package com.example.basi.test;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.basi.test.adapters.Adapter;
import com.example.basi.test.api.MovieApi;
import com.example.basi.test.model.Movie;
import com.example.basi.test.model.MovieList;
import com.example.basi.test.services.MovieService;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Main2Activity extends AppCompatActivity {

    private static final String TAG = Main2Activity.class.getSimpleName();
    @BindView(R.id.rv_main)
    RecyclerView rvMain;
    @BindView(R.id.pb_main)
    ProgressBar pbMain;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.coordinator)
    CoordinatorLayout coordinator;


    private TextView mTextMessage;
    private Unbinder bind;
    private Adapter adapter;
    private MovieApi movieApi;
    private View bottomSheet;
    private BottomSheetBehavior behavior;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        bind = ButterKnife.bind(this);

        setSupportActionBar(toolbar);


//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });

        findViewById();
        bottomSheetInit();
        init();


        Animation growAnimation = AnimationUtils.loadAnimation(this, R.anim.grow);
        Animation shrinkAnimation = AnimationUtils.loadAnimation(this, R.anim.shrink);
    }

    private void bottomSheetInit() {
        behavior = BottomSheetBehavior.from(bottomSheet);
        behavior.setState(BottomSheetBehavior.STATE_HIDDEN);
        behavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                switch (newState) {
                    case BottomSheetBehavior.STATE_DRAGGING:
                        Log.d(TAG, "onStateChanged:1 ");

                    case BottomSheetBehavior.STATE_COLLAPSED:
                        Log.d(TAG, "onStateChanged:2 ");
                        break;

                    case BottomSheetBehavior.STATE_EXPANDED:
                        Log.d(TAG, "onStateChanged:3 ");
                        break;
                }
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {
                Log.d(TAG, "onSlide:4 ");
            }
        });
    }

    private void findViewById() {
        bottomSheet = coordinator.findViewById(R.id.bottom_sheet);
    }

    private void init() {
        movieApi = MovieService.getClient(this).create(MovieApi.class);

        initRV();

        loadInitialData();
    }

    private void initRV() {
        adapter = new Adapter(this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rvMain.setLayoutManager(linearLayoutManager);
        rvMain.setItemAnimator(new DefaultItemAnimator());
        rvMain.setAdapter(adapter);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        bind.unbind();
    }


    private Call<MovieList> callMoviesApi() {
        return movieApi.getMovies(20, "seed");
    }


    private void loadInitialData() {
        callMoviesApi().enqueue(new Callback<MovieList>() {
            @Override
            public void onResponse(Call<MovieList> call, Response<MovieList> response) {
                List<Movie> movies = fetchResults(response);
                pbMain.setVisibility(View.GONE);
                adapter.setMoviesList(movies);
            }

            @Override
            public void onFailure(Call<MovieList> call, Throwable t) {
                Log.d(TAG, "onFailure: " + t.getMessage());

            }
        });
    }

    private List<Movie> fetchResults(Response<MovieList> response) {
        MovieList movieList = response.body();
        Log.d(TAG, "fetchResults: body : " + movieList.getData().getMovieCount());
        return movieList.getData().getMovies();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        MenuItem search = menu.findItem(R.id.search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(search);
        search(searchView);
        return true;
    }

    private void search(SearchView searchView) {
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                return true;
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

}
