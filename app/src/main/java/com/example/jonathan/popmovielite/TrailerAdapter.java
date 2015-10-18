package com.example.jonathan.popmovielite;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Jonathan on 10/16/2015.
 */
public class TrailerAdapter extends RecyclerView.Adapter<TrailerAdapter.ViewHolder> {
    private String[] mData;
    private int mLayout;
    private Context mContext;

    public TrailerAdapter(String[] data, int layout, Context context) {
        this.mContext = context;
        this.mData = data;
        this.mLayout = layout;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.row_trailer, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        String name = "Jonathan";
        holder.avatar.setImageResource(R.drawable.sample_1);
        holder.author.setText(name);
    }

    @Override
    public int getItemCount() {
            if (mData == null) {
                return 0;
            } else {
                return mData.length;
            }
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView avatar;
        public TextView author;

        public ViewHolder(View itemView) {
            super(itemView);
            author = (TextView) itemView.findViewById(R.id.authorName);
            avatar = (ImageView) itemView.findViewById(R.id.avatarImage);

        }

    }
}
