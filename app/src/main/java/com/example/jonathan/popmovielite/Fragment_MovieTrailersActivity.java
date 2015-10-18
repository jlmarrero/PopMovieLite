package com.example.jonathan.popmovielite;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Jonathan on 10/10/2015.
 */
public class Fragment_MovieTrailersActivity extends Fragment {
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private String[] myDataset={"this","that","the third"};

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_movie_trailers, container, false);
        //mRecyclerView.setLayoutManager(new LinearLayoutManager(rootView.getContext()));

        String[] data = {"Jonathan", "Nelson", "Chico"};
        mAdapter = new TrailerAdapter(data , R.layout.row_trailer, container.getContext());
        mRecyclerView = (RecyclerView) container.findViewById(R.id.list_trailers);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(container.getContext()));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setAdapter(mAdapter);
        return rootView;
    }
}
