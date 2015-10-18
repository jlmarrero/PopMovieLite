package com.example.jonathan.popmovielite;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

/**
 * Created by Jonathan on 10/15/2015.
 */
public class CommentsRecyclerViewAdapter extends RecyclerView.Adapter<CommentViewHolder> {
    private ArrayList<Comment> itemsList;

    public CommentsRecyclerViewAdapter(ArrayList<Comment> itemsList) {
        this.itemsList = itemsList;
    }

    @Override
    public CommentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.list_comment, parent, false);
        return new CommentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CommentViewHolder holder, int position) {
        Comment items = itemsList.get(position); Log.v("Items position: ", String.valueOf(position));

        if (holder != null) {
            Log.d("dbg", holder + "");
            holder.textViewAuthor.setText(String.valueOf(items.getmAuthor()));
            holder.textViewComment.setText(String.valueOf(items.getmComment()));
        } else {
            holder.textViewAuthor.setText("");
            holder.textViewAuthor.setText("");
        }
    }

    @Override
    public int getItemCount() {
        if (itemsList == null) {
            return 0;
        } else {
            return itemsList.size();
        }
    }
}
