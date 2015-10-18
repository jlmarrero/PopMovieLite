package com.example.jonathan.popmovielite;

import android.content.Context;
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
    private Context mContext = getActivity();
    private GridView mGridView;
    private int[] mUrls = new int[20];
    private Movie[] movies;
    private Movie[] mMovieArray;
    private String[] moviePaths;
    private ImageAdapter imageAdapter;
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
        // gridView = (GridView) view.findViewById(R.id.gridview);

        if (savedInstanceState == null || !savedInstanceState.containsKey("Movies")) {
            updateMovie();
            view = inflater.inflate(R.layout.fragment_grid_view, container, false);
            gridView = (GridView) view.findViewById(R.id.gridview);
            Log.v("HELLO:: ", "saveInstanceState == null");
        } else {
            movies = (Movie[]) savedInstanceState.getParcelableArray("Movies");
            String[] urlArray = new String[movies.length];
            Log.v("HELLO:: ", "savedInstanceState is working!!!! ");

            for (int i = 0; i < movies.length; i++) {
                urlArray[i] = movies[i].getPoster_path();
            }

            view = inflater.inflate(R.layout.fragment_grid_view, container, false);
            gridView = (GridView) view.findViewById(R.id.gridview);
            ImageAdapter adapter = new ImageAdapter(getActivity(), urlArray);
            gridView.setAdapter(adapter);
        }

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {

                Intent intent = new Intent(v.getContext(), DescriptionActivity.class);
                Bundle b = new Bundle();
                b.putParcelable("MOVIE", movies[position]);
                Log.v("BYE BYE! ", "-----------> " + movies[position].getTitle());
                intent.putExtras(b);
                startActivity(intent);

                /*
                Bundle b = new Bundle();
                b.putParcelable("MOVIE", movies[position]);
                Fragment_MovieDescriptionActivity fd = new Fragment_MovieDescriptionActivity();
                fd.setArguments(b);
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.fragment_container_main,fd);
                ft.show(getFragmentManager().findFragmentById(R.id.fragment_container_main));
                ft.addToBackStack(null);
                ft.commit();
                */
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


    public class FetchMovieTask extends AsyncTask<Void, Movie[], Movie[]> {

        @Override
        protected Movie[] doInBackground(Void... params) {
            // These two need to be declared outside the try/catch
            // so that they can be closed in the finally block.
            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;

// Will contain the raw JSON response as a string.
            String movieJsonStr = null;

            try {
                // Construct the URL for the OpenWeatherMap query
                // Possible parameters are available at OWM's forecast API page, at
                // http://openweathermap.org/API#forecast
                //URL url = new URL("http://api.openweathermap.org/data/2.5/forecast/daily?q=94043&mode=json&units=metric&cnt=7");

                Uri buildUri = Uri.parse(getString(R.string.BASE_URL_MOVIE)).buildUpon()
                        .appendQueryParameter(getString(R.string.primary_release_year), getString(R.string.year_param))
                        .appendQueryParameter(getString(R.string.cert_country), getString(R.string.search_country_param))
                        .appendQueryParameter(getString(R.string.query_param), "popularity.desc")
                        .appendQueryParameter(getString(R.string.api_param), getString(R.string.api_key))
                        .build();

                URL url = new URL(buildUri.toString()); Log.v("MY NEW URL: ", url.toString());

                // Create the request to OpenWeatherMap, and open the connection
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
                movieJsonStr = buffer.toString(); //Log.v("MY MOVIE JSON: ", movieJsonStr);
                movies = getMovieDataFromJson(movieJsonStr);

            } catch (IOException e) {
                Log.e("PlaceholderFragment", "Error ", e);
                // If the code didn't successfully get the weather data, there's no point in attempting
                // to parse it.
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

        private Movie[] getMovieDataFromJson(String movieJsonStr) throws JSONException {

            JSONObject jData = new JSONObject(movieJsonStr);
            JSONArray jArray = jData.getJSONArray("results");

            Log.v("My JSON array len: ", String.valueOf(jArray.length()));

            String[] ids = new String[jArray.length()];
            movies = new Movie[jArray.length()];
            moviePaths = new String[jArray.length()];
            mMovieArray = new Movie[jArray.length()];

            String trailer_json = "";
            String reviews_json = "";

            // Create instance of MovieDBHelper
            // MovieDatabaseHelper helper = new MovieDatabaseHelper(getApplication());
            // helper.clearData();

            // Grab ids for us in pulling complete (Youtube source, comments) JSON
            for (int i = 0; i < jArray.length(); i++) {
                JSONObject jActualMovie = jArray.getJSONObject(i);
                String id = jActualMovie.getString("id"); // Log.v("Movie ID: ", id);
                ids[i] = id;
            }
            // Call for movie JSON that includes Trailer and Commment information
            for (int i = 0; i < jArray.length(); i++) {
                trailer_json = getMovieTrailerJson(ids[i]); // this string contains the JSON that includes Youtube source id
                // Log.v("COMPLETE JSON: ", trailer_json);

                // CREATE METHOD that will parse JSON, Create and Store a Movie Object
                // Use the code below to start the Method

                JSONObject jRawData = new JSONObject(trailer_json);
                JSONObject jTrailer = jRawData.getJSONObject("trailers");
                JSONObject jReviews = jRawData.getJSONObject("reviews");
                JSONArray jTrailerArray = jTrailer.getJSONArray("youtube");
                JSONArray jReviewsArray = jReviews.getJSONArray("results");

                // I will store my Trailer and Comments JSON as strings, then
                // will turn the String into a JSONObject for parsing
                trailer_json = jTrailerArray.toString();
                reviews_json = jReviewsArray.toString();

                Log.v("TRAILER JSONArray: ", jTrailerArray.toString());
                Log.v("REVIEWS JSONArray: ", jReviewsArray.toString());

                String id = jRawData.getString("id"); // Log.v("Movie ID: ", id);
                //JSONObject jMovie = jArray.getJSONObject(i);

                String title = jRawData.getString("title");
                String posterPath = "";
                String backdropPath = "";

                if (jRawData.getString("poster_path") == "null") {
                    posterPath = "empty";
                } else {
                    posterPath = getString(R.string.base_poster_path) + jRawData.getString("poster_path");
                }
                // Log.v("POSTER: ", posterPath);

                if (jRawData.getString("backdrop_path") == "null") {
                    backdropPath = "empty";
                } else {
                    backdropPath = getString(R.string.base_backdrop_path) + jRawData.getString("backdrop_path"); // Log.v("BACKDROP: ", backdropPath);
                }

                String release = jRawData.getString("release_date");
                String popularity = jRawData.getString("popularity");
                String overview = jRawData.getString("overview");
                String vote_average = jRawData.getString("vote_average");

                Movie m = new Movie(id, title, popularity, overview, posterPath,
                        vote_average, release, backdropPath, trailer_json, reviews_json);
                mMovieArray[i] = m;
            }

            if (mMovieArray == null) {
                return null;
            } else {
                return mMovieArray;
            }
        }

        private String getMovieTrailerJson(String movieID) throws JSONException {
            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;
            String moviePath = "";

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

                // Log.v("MY TRAILER URL: ", url.toString());

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
                    // Since it's JSON, adding a newline isn't necessary (it won't affect parsing)
                    // But it does make debugging a *lot* easier if you print out the completed
                    // buffer for debugging.
                    buffer.append(line + "\n");
                }

                if (buffer.length() == 0) {
                    // Stream was empty.  No point in parsing.
                    //return null;
                }
                rawJson = buffer.toString();
                // Log.v("JSON Trails/Comms: ", rawJson);

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

            // call getMovieDataFromJson function passing in raw JSON
            // Log.v("NEW JSON: ", rawJson);
            return rawJson;
        }

        @Override
        protected void onPostExecute(Movie[] result) {
            moviePaths = new String[result.length];

            if (result != null) {

                Log.v("MORE JSON: ", result[0].toString());
                movies = result;
                for (int i = 0; i < movies.length; i++) {
                    moviePaths[i] = movies[i].getPoster_path();
                }
                gridView.setAdapter(new ImageAdapter(getActivity(), moviePaths));

            }
        }
    }
}

