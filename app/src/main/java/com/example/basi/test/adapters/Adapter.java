package com.example.basi.test.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.basi.test.R;
import com.example.basi.test.model.Movie;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Basi on 06-07-2017.
 */

public class Adapter extends RecyclerView.Adapter<Adapter.MovieViewHolder> implements Filterable {

    private static final String TAG = Adapter.class.getSimpleName();
    private List<Movie> moviesList, mFilteredList;
    private Context context;

    public Adapter(Context context) {
        this.context = context;
        this.moviesList = new ArrayList<>();
        this.mFilteredList = new ArrayList<>();
    }

    public List<Movie> getMoviesList() {
        return moviesList;
    }

    public void setMoviesList(List<Movie> moviesList) {
        this.moviesList = moviesList;
        notifyDataSetChanged();
    }

    @Override
    public MovieViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list, parent, false);
        return new MovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MovieViewHolder holder, int position) {

        Movie movie = mFilteredList.get(position);
        holder.movieTitle.setText(movie.getTitle());
        holder.movieDesc.setText(movie.getDescriptionFull());
    }

    @Override
    public int getItemCount() {
        Log.d(TAG, "getItemCount: ");
        return mFilteredList.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            /**
             *  loop through the original ArrayList and check
             *  if it contains the string using contains() method and
             *  add it to a filtered ArrayList
             * @param constraint
             * @return
             */
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String charString = constraint.toString();

                if (charString.isEmpty()) {

                    mFilteredList = moviesList;
                } else {

                    List<Movie> filteredList = new ArrayList<>();

                    for (Movie movie : moviesList) {

                        if (movie.getTitle().toLowerCase().contains(charString)) {
                            filteredList.add(movie);
                        }
                    }

                    mFilteredList = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = mFilteredList;
                return filterResults;
            }

            /**
             * convert the FilterResults object to the ArrayList object
             * @param constraint
             * @param results
             */
            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                mFilteredList = (ArrayList<Movie>) results.values;
                notifyDataSetChanged();
            }
        };
    }

    public static class MovieViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.movie_progress)
        ProgressBar movieProgress;
        @BindView(R.id.movie_poster)
        ImageView moviePoster;
        @BindView(R.id.movie_title)
        TextView movieTitle;
        @BindView(R.id.movie_desc)
        TextView movieDesc;

        public MovieViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }


    public void add(Movie r) {
        moviesList.add(r);
        notifyItemInserted(moviesList.size() - 1);
    }

    public void addAll(List<Movie> moveResults) {
        for (Movie result : moveResults) {
            add(result);
        }
    }

    public void remove(Movie r) {
        int position = moviesList.indexOf(r);
        if (position > -1) {
            moviesList.remove(position);
            notifyItemRemoved(position);
        }
    }

    public void clear() {

        while (getItemCount() > 0) {
            remove(getItem(0));
        }
    }

    public Movie getItem(int position) {
        return moviesList.get(position);
    }
}
