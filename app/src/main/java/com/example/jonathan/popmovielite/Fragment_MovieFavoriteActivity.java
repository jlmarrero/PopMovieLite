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
import android.widget.Toast;

/**
 * Created by Jonathan on 10/10/2015.
 */
public class Fragment_MovieFavoriteActivity extends android.support.v4.app.Fragment {
    private GridView gridView;
    private Obj_Movie[] objMovieArray;

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

                Intent intent = new Intent(v.getContext(), Activity_Description.class);
                Bundle b = new Bundle();
                b.putParcelable("MOVIE", objMovieArray[position]);
                Log.v("BYE BYE! ", "-----------> " + objMovieArray[position].getTitle());
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


    public class FetchMovieTask extends AsyncTask<Void, Obj_Movie[], Obj_Movie[]> {
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

        @Override
        protected Obj_Movie[] doInBackground(Void... params) {
            MovieDatabaseHelper helper = new MovieDatabaseHelper(getActivity().getApplicationContext());
            Cursor cursor = helper.getAllData();

            while(cursor.moveToNext()) {
                objMovieArray =new Obj_Movie[cursor.getCount()];
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

                    Obj_Movie objMovie = new Obj_Movie(id, title, popularity, description, poster_path, vote_average, release_date, backdrop_path,
                            trailer_json, comments_json);

                    objMovieArray[i] = objMovie;

                    i++;
                }while(cursor.moveToNext());

            }

        return objMovieArray;
        }

        @Override
        protected void onPostExecute(Obj_Movie[] result) {
            if(result == null){
                Toast.makeText(getActivity(), "You have not favorited any movies.", Toast.LENGTH_LONG).show();
            }else{
                String[] posterPathArray = new String[result.length];
                for(int i=0;i<result.length;i++){
                    posterPathArray[i]=result[i].getPoster_path();
                }
                gridView.setAdapter(new Adapter_GridActivity(getActivity(), posterPathArray));
            }
        }
    }
}

