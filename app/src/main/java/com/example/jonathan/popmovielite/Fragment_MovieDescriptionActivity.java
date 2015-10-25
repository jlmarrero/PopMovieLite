package com.example.jonathan.popmovielite;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jonathan on 10/10/2015.
 */
public class Fragment_MovieDescriptionActivity extends android.support.v4.app.Fragment {
    private List<Obj_Comment> objCommentArrayList = new ArrayList<>();
    private List<Obj_Trailer> objTrailerArrayList = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_movie_description, container, false);

        Bundle b = getActivity().getIntent().getExtras();
        final Obj_Movie mObjMovie = b.getParcelable("MOVIE");

        TextView title= (TextView) rootView.findViewById(R.id.movie_detail_title);
        TextView date= (TextView) rootView.findViewById(R.id.movie_detail_date);
        TextView synopsis= (TextView) rootView.findViewById(R.id.movie_detail_synopsis);
        RatingBar ratingBar= (RatingBar) rootView.findViewById(R.id.ratingBar);
        ImageView hero= (ImageView) rootView.findViewById(R.id.movie_detail_hero_image);
        ImageView poster= (ImageView) rootView.findViewById(R.id.movie_detail_poster);
        Float step= (Float.parseFloat("0.01"));
        Float realFloat = Float.parseFloat(mObjMovie.getVote_average());

        ListView listView = (ListView) rootView.findViewById(R.id.list_view2);
        ListView listView1 = (ListView) rootView.findViewById(R.id.list_view3);

        if(mObjMovie ==null)
            Toast.makeText(getActivity(), "Obj_Movie title: DOES NOT EXIST", Toast.LENGTH_LONG).show();
        else
            title.setText(mObjMovie.getTitle());
            Picasso.with(rootView.getContext()).load(mObjMovie.getBackdrop_path()).into(hero);
            Picasso.with(rootView.getContext()).load(mObjMovie.getPoster_path()).into(poster);
            date.setText(mObjMovie.getRelease_date());
            ratingBar.setStepSize(step);
            ratingBar.setRating(realFloat / 2);
            synopsis.setText(mObjMovie.getDescription());

        ImageButton buttonFavorite = (ImageButton) rootView.findViewById(R.id.imageButton_Favorite);
        buttonFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MovieDatabaseHelper helper = new MovieDatabaseHelper(getActivity());
                long dbCheck = 1;
                boolean check = helper.getRowExists(mObjMovie.getId());
                Log.v("ROW CHECK: ", String.valueOf(check));

                if (check == false) {
                    // DATABASE CALL TO INSERT A NEW ROW INTO MOVIE TABLE
                    dbCheck = helper.insertData(mObjMovie);
                    String message = mObjMovie.getTitle() + " was saved to your favorites list.";
                    Toast toast = Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT);
                    toast.show();
                } else if (check == true) {
                    String message = mObjMovie.getTitle() + " is already saved.";
                    Toast toast = Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT);
                    toast.show();
                }
            }
        });
        try {
            objTrailerArrayList = getTrailerData(mObjMovie.getTrailer_json());
            objCommentArrayList = getCommentData(mObjMovie.getComments_json());

        } catch (JSONException e) {
            e.printStackTrace();
        }
        Obj_Trailer[] trail = new Obj_Trailer[objTrailerArrayList.size()];
        Obj_Comment[] objComments = new Obj_Comment[objCommentArrayList.size()];
        for(int i=0;i< objCommentArrayList.size();i++){
            objComments[i]= objCommentArrayList.get(i); // Log.v("Obj_Comment from array:", objComments[i].getmAuthor());
        }
        for(int i=0;i< objTrailerArrayList.size();i++){
            trail[i]= objTrailerArrayList.get(i);
        }
        // Create Trailers list
        Adapter_Trailers adapter_trailers=new Adapter_Trailers(getActivity().getApplicationContext(),trail, getActivity());
        listView1.setAdapter(adapter_trailers);
        setListViewHeightBasedOnChildren(listView1);
        // Create Comments list
        Adapter_Comments adapter= new Adapter_Comments(getActivity().getApplicationContext(), objComments);
        listView.setAdapter(adapter);
        setListViewHeightBasedOnChildren(listView);

        return rootView;
    }

    private List<Obj_Trailer> getTrailerData(String trailer_json) throws JSONException {
        String trailerJSON=trailer_json;
        List<Obj_Trailer> it=new ArrayList<>();
        Log.v("Hello trailers: ", trailerJSON);

        JSONObject trailer;
        JSONArray jTrailerArray;

        try {
            trailer = new JSONObject(trailerJSON);
            jTrailerArray = trailer.getJSONArray("youtube");
            for(int i=0; i<jTrailerArray.length();i++){
                JSONObject jsonObject=jTrailerArray.getJSONObject(i);
                Obj_Trailer t = new Obj_Trailer(jsonObject.getString("name"), jsonObject.getString("source"));
                it.add(t);
            }
        }catch (JSONException e) {
            e.printStackTrace();
        }
        return it;
    }

    public List<Obj_Comment> getCommentData(String data) throws JSONException {
        List<Obj_Comment> it=new ArrayList<>();
        JSONObject comment;
        JSONArray jReviewsArray;

        try {
            comment= new JSONObject(data);
            jReviewsArray = comment.getJSONArray("results");
            Log.v("jREVIEWArray: ", String.valueOf(jReviewsArray.getJSONObject(0).get("author")));

            Log.v("Obj_Comment LEN:", String.valueOf(jReviewsArray.length()));

            for(int i=0;i<jReviewsArray.length();i++) {
                JSONObject jObject = jReviewsArray.getJSONObject(i);
                Obj_Comment mObjComment = new Obj_Comment(jObject.getString("author"), jObject.getString("content"));
                it.add(mObjComment);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return it;
    }

    public static void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null)
            return;

        int desiredWidth = View.MeasureSpec.makeMeasureSpec(listView.getWidth(),
                View.MeasureSpec.UNSPECIFIED);
        int totalHeight = 0;
        View view = null;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            view = listAdapter.getView(i, view, listView);
            if (i == 0)
                view.setLayoutParams(new ViewGroup.LayoutParams(desiredWidth,
                        LinearLayout.LayoutParams.WRAP_CONTENT));

            view.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);
            totalHeight += view.getMeasuredHeight();
        }
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight
                + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
        listView.requestLayout();
    }

}
