package com.example.basi.test;

import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.basi.test.adapters.Adapter;
import com.example.basi.test.api.MovieApi;
import com.example.basi.test.model.Data;
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

//import android.support.design.widget.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    @BindView(R.id.rv_main)
    RecyclerView rvMain;
    @BindView(R.id.pb_main)
    ProgressBar pbMain;
    @BindView(R.id.content)
    FrameLayout content;
    @BindView(R.id.container)
    LinearLayout container;

    private TextView mTextMessage;
    private Unbinder bind;
    private Adapter adapter;
    private MovieApi movieApi;

//    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
//            = new BottomNavigationView.OnNavigationItemSelectedListener() {
//
//        @Override
//        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//            switch (item.getItemId()) {
//                case R.id.navigation_home:
//                    mTextMessage.setText(R.string.title_home);
//                    return true;
//                case R.id.navigation_dashboard:
//                    mTextMessage.setText(R.string.title_dashboard);
//                    return true;
//                case R.id.navigation_notifications:
//                    mTextMessage.setText(R.string.title_notifications);
//                    return true;
//            }
//            return false;
//        }
//
//    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bind = ButterKnife.bind(this);

//        mTextMessage = (TextView) findViewById(R.id.message);
//        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
//        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);


        init();
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
        return movieApi.getMovies(10, "seed");
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
                Log.d(TAG, "onFailure: "+t.getMessage());

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
