package com.example.jonathan.popmovielite;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;


/**
 * Created by Jonathan on 7/27/2015.
 */
public class Adapter_GridActivity extends BaseAdapter {
    private Context mContext;
    private String[] mUrls;
    private ImageView imageView;

    public Adapter_GridActivity(Context c, String[] arrays) {
        mContext = c;
        mUrls = arrays;
        imageView = new ImageView(mContext);
    }

    public int getCount() {
        return mUrls.length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    public long getItemId(int position) {
        return 0;
    }

    // create a new ImageView for each item referenced by the Adapter
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            // if it's not recycled, initialize some attributes
            imageView = new ImageView(mContext);
            imageView.setAdjustViewBounds(true);
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setPadding(1, 1, 1, 1);
        } else {
            imageView = (ImageView) convertView;
        }

        // Check to see if post file exists. If not, replace with default image
        String checker = "empty";
        if (mUrls[position] == checker){
            int draw = android.R.drawable.btn_star_big_on;
            Picasso.with(mContext).load(R.drawable.sample_2).into(imageView);
        } else {
            Picasso.with(mContext).load(mUrls[position]).into(imageView);
        }
        return imageView;
    }
}
