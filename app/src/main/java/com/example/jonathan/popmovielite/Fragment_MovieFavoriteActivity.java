package com.example.jonathan.popmovielite;

import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

/**
 * Created by Jonathan on 10/10/2015.
 */
public class Fragment_MovieFavoriteActivity extends android.support.v4.app.Fragment {
    private Movie[] mMovieArray;
    private GridView gridView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        updateMovie();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_grid_view, container, false);
        gridView = (GridView) view.findViewById(R.id.gridview);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {

                Intent intent = new Intent(v.getContext(), DescriptionActivity.class);
                Bundle b = new Bundle();
                b.putParcelable("MOVIE", mMovieArray[position]);
                Log.v("BYE BYE! ", "-----------> " + mMovieArray[position].getTitle());
                intent.putExtras(b);
                startActivity(intent);
            }
        });
        return view;
    }

    private void updateMovie() {
        FetchMovieTask updateMovies = new FetchMovieTask();
        updateMovies.execute();
    }


    public class FetchMovieTask extends AsyncTask<Void, Movie[], Movie[]> {
        private String id;
        private String title;
        private String popularity;
        private String description;
        private String poster_path;
        private String release_date;
        private String vote_average;
        private String backdrop_path;
        private String trailer_json;
        private String comments_json;
        private Movie[] movieArray;

        @Override
        protected Movie[] doInBackground(Void... params) {
            MovieDatabaseHelper helper = new MovieDatabaseHelper(getActivity().getApplicationContext());
            Cursor cursor = helper.getAllData();

            while(cursor.moveToNext()) {
                movieArray=new Movie[cursor.getCount()];
                int i=0;
                do{
                    id = cursor.getString(cursor.getColumnIndex("movie_id"));
                    title = cursor.getString(cursor.getColumnIndex("title"));
                    popularity = cursor.getString(cursor.getColumnIndex("popularity"));;
                    description = cursor.getString(cursor.getColumnIndex("description"));;
                    poster_path = cursor.getString(cursor.getColumnIndex("poster_path"));;
                    release_date = cursor.getString(cursor.getColumnIndex("release_date"));;
                    vote_average = cursor.getString(cursor.getColumnIndex("vote_average"));;
                    backdrop_path = cursor.getString(cursor.getColumnIndex("backdrop_path"));;
                    trailer_json = cursor.getString(cursor.getColumnIndex("trailer_json"));;
                    comments_json = cursor.getString(cursor.getColumnIndex("comments_json"));;

                    Movie movie = new Movie(id, title, popularity, description, poster_path, vote_average, release_date, backdrop_path,
                            trailer_json, comments_json);

                    movieArray[i] = movie;

                    i++;
                }while(cursor.moveToNext());

            }

        return movieArray;
        }

        @Override
        protected void onPostExecute(Movie[] result) {
            mMovieArray = result;
            String[] posterPathArray = new String[result.length];
            for(int i=0;i<result.length;i++){
                posterPathArray[i]=result[i].getPoster_path();
            }
            gridView.setAdapter(new ImageAdapter(getActivity(), posterPathArray));


        }
    }
}

