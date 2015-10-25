package com.example.jonathan.popmovielite;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.android.youtube.player.YouTubeStandalonePlayer;
import com.squareup.picasso.Picasso;


/**
 * Created by Jonathan on 7/27/2015.
 */
public class Adapter_Trailers extends BaseAdapter {
    private Context mContext;
    private Obj_Trailer[] mObjTrailer;
    private ImageButton imageView;
    private Activity mActivity;

    public Adapter_Trailers(Context c, Obj_Trailer[] contents, Activity activity) {
        mContext = c;
        mObjTrailer = contents;
        mActivity = activity;
        Log.v("Obj_Trailer adapter: ", contents[0].getmTitle());
        imageView = new ImageButton(mContext);
    }

    public int getCount() {
        return mObjTrailer.length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    public long getItemId(int position) {
        return 0;
    }

    /*private view holder class*/
    private class ViewHolder {
        TextView t;
        ImageButton button;
    }

    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        LayoutInflater mInflater = (LayoutInflater) mContext.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.list_trailer, null);
            holder = new ViewHolder();
            holder.t = (TextView) convertView.findViewById(R.id.trailer_title);
            holder.button = (ImageButton) convertView.findViewById(R.id.trailer_play_button);
            convertView.setTag(holder);
        }
        else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.t.setText(mObjTrailer[position].getmTitle());
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Toast.makeText(v.getContext(),String.valueOf(position),Toast.LENGTH_SHORT).show();

                Intent intent = YouTubeStandalonePlayer.createVideoIntent(mActivity, mContext.getString(R.string.YT_API), mObjTrailer[position].getmSource());
                mContext.startActivity(intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
            }
        });
        Picasso.with(mContext).load(R.drawable.sample_2).into(imageView);

        return convertView;
    }
}
