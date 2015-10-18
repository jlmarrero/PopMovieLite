package com.example.jonathan.popmovielite;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

/**
 * Created by Jonathan on 10/15/2015.
 */
public class CommentViewHolder extends RecyclerView.ViewHolder {
    TextView textViewAuthor;
    TextView textViewComment;

    public CommentViewHolder(View itemView) {
        super(itemView);
        this.textViewAuthor = (TextView) itemView.findViewById(R.id.author);
        this.textViewComment = (TextView) itemView.findViewById(R.id.comment);

    }
}
