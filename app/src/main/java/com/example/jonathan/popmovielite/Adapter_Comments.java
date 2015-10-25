package com.example.jonathan.popmovielite;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;


/**
 * Created by Jonathan on 7/27/2015.
 */
public class Adapter_Comments extends BaseAdapter {
    private Context mContext;
    private Obj_Comment[] mObjComment;
    private String mAuthor;
    private String mContent;
    private ImageView imageView;

    public Adapter_Comments(Context c, Obj_Comment[] contents) {
        mContext = c;
        mObjComment = contents;
        imageView = new ImageView(mContext);
    }

    public int getCount() {
        return mObjComment.length;
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
        TextView c;
        TextView a;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;

        LayoutInflater mInflater = (LayoutInflater) mContext.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.list_comment, null);
            holder = new ViewHolder();
            holder.c = (TextView) convertView.findViewById(R.id.comment);
            holder.a = (TextView) convertView.findViewById(R.id.author);
            convertView.setTag(holder);
        }
        else {
            holder = (ViewHolder) convertView.getTag();
        }

        Obj_Comment objComment = (Obj_Comment) mObjComment[position];

        holder.a.setText(objComment.getmAuthor());
        holder.c.setText(objComment.getmComment());

        return convertView;
    }
}
