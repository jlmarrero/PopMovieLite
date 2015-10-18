package com.example.jonathan.popmovielite;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

/**
 * Created by Jonathan on 10/10/2015.
 */
public class Fragment_MovieDescriptionActivity extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_movie_description, container, false);

        Bundle b = getActivity().getIntent().getExtras();
        final Movie mMovie= b.getParcelable("MOVIE");
        TextView title= (TextView) rootView.findViewById(R.id.movie_detail_title);
        TextView date= (TextView) rootView.findViewById(R.id.movie_detail_date);
        TextView synopsis= (TextView) rootView.findViewById(R.id.movie_detail_synopsis);
        RatingBar ratingBar= (RatingBar) rootView.findViewById(R.id.ratingBar);
        ImageView hero= (ImageView) rootView.findViewById(R.id.movie_detail_hero_image);
        ImageView poster= (ImageView) rootView.findViewById(R.id.movie_detail_poster);
        Float step= (Float.parseFloat("0.01"));
        Float realFloat = Float.parseFloat(mMovie.getVote_average());

        if(mMovie==null)
            Toast.makeText(getActivity(), "Movie title: DOES NOT EXIST", Toast.LENGTH_LONG).show();
        else
            title.setText(mMovie.getTitle());
            Picasso.with(rootView.getContext()).load(mMovie.getBackdrop_path()).into(hero);
            Picasso.with(rootView.getContext()).load(mMovie.getPoster_path()).into(poster);
            date.setText(mMovie.getRelease_date());
            ratingBar.setStepSize(step);
            ratingBar.setRating(realFloat / 2);
            Log.v("Rating bar: ", String.valueOf(Float.parseFloat(mMovie.getVote_average()) / 2));
            synopsis.setText(mMovie.getDescription());

        ImageButton buttonFavorite = (ImageButton) rootView.findViewById(R.id.imageButton_Favorite);
        buttonFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create instance of MovieDBHelper
                MovieDatabaseHelper helper = new MovieDatabaseHelper(getActivity());
                long dbCheck = 1;
                boolean check = helper.getRowExists(mMovie.getId());
                Log.v("ROW CHECK: ", String.valueOf(check));

                if (check == false) {
                    // DATABASE CALL TO INSERT A NEW ROW INTO MOVIE TABLE
                    dbCheck = helper.insertData(mMovie);
                    String message = mMovie.getTitle() + " was saved to your favorites list.";
                    Toast toast = Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT);
                    toast.show();
                } else if (check == true) {
                    String message = mMovie.getTitle() + " is already saved.";
                    Toast toast = Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT);
                    toast.show();
                }
            }
        });

        return rootView;

    }

}
