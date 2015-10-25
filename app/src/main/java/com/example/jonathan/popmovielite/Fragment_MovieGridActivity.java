package com.example.jonathan.popmovielite;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Jonathan on 10/10/2015.
 */
public class Fragment_MovieGridActivity extends android.support.v4.app.Fragment {
    private Obj_Movie[] movies;
    private Obj_Movie[] mObjMovieArray;
    private String[] moviePaths;
    private Adapter_GridActivity imageAdapter;
    // preference variable
    private String Pref;
    private HttpURLConnection urlConnection = null;
    private BufferedReader reader = null;
    private String movieJsonStr = null;
    private String line;
    private GridView gridView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view;

        if (savedInstanceState == null || !savedInstanceState.containsKey("Movies")) {
            updateMovie();
            view = inflater.inflate(R.layout.fragment_grid_view, container, false);
            gridView = (GridView) view.findViewById(R.id.gridview);
        } else {
            movies = (Obj_Movie[]) savedInstanceState.getParcelableArray("Movies");
            String[] urlArray = new String[movies.length];

            for (int i = 0; i < movies.length; i++) {
                urlArray[i] = movies[i].getPoster_path();
            }

            view = inflater.inflate(R.layout.fragment_grid_view, container, false);
            gridView = (GridView) view.findViewById(R.id.gridview);
            imageAdapter = new Adapter_GridActivity(getActivity(), urlArray);
            gridView.setAdapter(imageAdapter);
        }

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {

                Intent intent = new Intent(v.getContext(), Activity_Description.class);
                Bundle b = new Bundle();
                b.putParcelable("MOVIE", movies[position]); // Log.v("BYE BYE! ", "-----------> " + movies[position].getTitle());
                intent.putExtras(b);
                startActivity(intent);
            }
        });
        return view;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        // save Activity details
        outState.putParcelableArray("Movies", movies);
        // preference variable
        outState.putString("Preference", Pref);
    }

    private void updateMovie() {
        FetchMovieTask updateMovies = new FetchMovieTask();
        updateMovies.execute();
    }


    public class FetchMovieTask extends AsyncTask<Void, Obj_Movie[], Obj_Movie[]> {

        @Override
        protected Obj_Movie[] doInBackground(Void... params) {
            BufferedReader reader = null;
            String movieJsonStr;

            try {
                Uri buildUri = Uri.parse(getString(R.string.BASE_URL_MOVIE)).buildUpon()
                        .appendQueryParameter(getString(R.string.primary_release_year), getString(R.string.year_param))
                        .appendQueryParameter(getString(R.string.cert_country), getString(R.string.search_country_param))
                        .appendQueryParameter(getString(R.string.query_param), "popularity.desc") // Replace this with pref variable
                        .appendQueryParameter(getString(R.string.api_param), getString(R.string.api_key))
                        .build();

                URL url = new URL(buildUri.toString()); Log.v("MY NEW URL: ", url.toString());
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();

                // Read the input stream into a String
                InputStream inputStream = urlConnection.getInputStream();
                StringBuffer buffer = new StringBuffer();
                if (inputStream == null) {
                    // Nothing to do.
                    movieJsonStr = null;
                }
                reader = new BufferedReader(new InputStreamReader(inputStream));

                String line;
                while ((line = reader.readLine()) != null) {
                    // Since it's JSON, adding a newline isn't necessary (it won't affect parsing)
                    // But it does make debugging a *lot* easier if you print out the completed
                    // buffer for debugging.
                    buffer.append(line + "\n");
                }

                if (buffer.length() == 0) {
                    // Stream was empty.  No point in parsing.
                    movieJsonStr = null;
                }
                movieJsonStr = buffer.toString();
                movies = getMovieDataFromJson(movieJsonStr);

            } catch (IOException e) {
                Log.e("PlaceholderFragment", "Error ", e);
                movieJsonStr = null;
            } catch (JSONException e) {
                e.printStackTrace();
            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (final IOException e) {
                        Log.e("PlaceholderFragment", "Error closing stream", e);
                    }
                }
            }

            for (int i=0;i<movies.length;i++){
                moviePaths[i]=movies[i].getPoster_path();
            }
        return movies;
        }

        private Obj_Movie[] getMovieDataFromJson(String movieJsonStr) throws JSONException {

            JSONObject jData = new JSONObject(movieJsonStr);
            JSONArray jArray = jData.getJSONArray("results");

            String[] ids = new String[jArray.length()];
            movies = new Obj_Movie[jArray.length()];
            moviePaths = new String[jArray.length()];
            mObjMovieArray = new Obj_Movie[jArray.length()];

            String trailer_json = "";
            String reviews_json = "";

            // Grab ids for us in pulling complete (Youtube source, comments) JSON
            for (int i = 0; i < jArray.length(); i++) {
                JSONObject jActualMovie = jArray.getJSONObject(i);
                String id = jActualMovie.getString("id"); // Log.v("Obj_Movie ID: ", id);
                ids[i] = id;
            }
            // Call for movie JSON that includes Obj_Trailer and Commment information
            for (int i = 0; i < jArray.length(); i++) {
                trailer_json = getMovieTrailerJson(ids[i]); // this string contains the JSON that includes Youtube source id
                // Log.v("COMPLETE JSON: ", trailer_json);

                JSONObject jRawData = new JSONObject(trailer_json);
                JSONObject jTrailer = jRawData.getJSONObject("trailers");
                JSONObject jReviews = jRawData.getJSONObject("reviews");

                trailer_json = jTrailer.toString(); // Log.v("TRAILER JSONArray: ", jTrailerArray.toString());
                reviews_json = jReviews.toString(); // Log.v("REVIEWS JSONArray: ", jReviewsArray.toString());

                String id = jRawData.getString("id");
                String title = jRawData.getString("title");

                String posterPath = "";
                String backdropPath = "";

                if (jRawData.getString("poster_path") == "null") {
                    posterPath = "empty";
                } else {
                    posterPath = getString(R.string.base_poster_path) + jRawData.getString("poster_path");
                }

                if (jRawData.getString("backdrop_path") == "null") {
                    backdropPath = "empty";
                } else {
                    backdropPath = getString(R.string.base_backdrop_path) + jRawData.getString("backdrop_path"); // Log.v("BACKDROP: ", backdropPath);
                }

                String release = jRawData.getString("release_date");
                String popularity = jRawData.getString("popularity");
                String overview = jRawData.getString("overview");
                String vote_average = jRawData.getString("vote_average");

                Obj_Movie m = new Obj_Movie(id, title, popularity, overview, posterPath,
                        vote_average, release, backdropPath, trailer_json, reviews_json);
                mObjMovieArray[i] = m;
            }

            if (mObjMovieArray == null) {
                return null;
            } else {
                return mObjMovieArray;
            }
        }

        private String getMovieTrailerJson(String movieID) throws JSONException {
            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;

            // This String will contain the raw JSON response as a string.
            String rawJson = null;
            String append = "append_to_response";
            String tr = "trailers,reviews";

            try {
                Uri buildUri = Uri.parse("http://api.themoviedb.org/3/movie/").buildUpon()
                        .appendPath(movieID)
                        .appendQueryParameter(getString(R.string.api_param), getString(R.string.api_key))
                        .appendQueryParameter(append, tr)
                        .build();

                URL url = new URL(buildUri.toString());

                // Create the request to MDB, and open the connection
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();

                // Read the input stream into a String
                InputStream inputStream = urlConnection.getInputStream();
                StringBuffer buffer = new StringBuffer();
                if (inputStream == null) {
                    // Nothing to do.
                    rawJson = null;
                }
                reader = new BufferedReader(new InputStreamReader(inputStream));

                String line;
                while ((line = reader.readLine()) != null) {
                    buffer.append(line + "\n");
                }

                if (buffer.length() == 0) {
                    // Stream was empty.  No point in parsing.
                    return null;
                }
                rawJson = buffer.toString(); // Log.v("JSON Trails/Comms: ", rawJson);

            } catch (IOException e) {
                Log.e("", "Error ", e);
                rawJson = null;
            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (final IOException e) {
                        Log.e("", "Error closing stream", e);
                    }
                }
            }

            return rawJson;
        }

        @Override
        protected void onPostExecute(Obj_Movie[] result) {
            moviePaths = new String[result.length];

            if (result != null) {
                movies = result;
                for (int i = 0; i < movies.length; i++) {
                    moviePaths[i] = movies[i].getPoster_path();
                }
                gridView.setAdapter(new Adapter_GridActivity(getActivity(), moviePaths));
            }
        }
    }
}

