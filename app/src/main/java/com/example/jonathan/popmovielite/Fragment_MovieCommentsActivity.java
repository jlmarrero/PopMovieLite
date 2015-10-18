package com.example.jonathan.popmovielite;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.json.JSONException;

import java.util.ArrayList;

/**
 * Created by Jonathan on 10/10/2015.
 */
public class Fragment_MovieCommentsActivity extends Fragment {
    private Context mContext;
    private CommentsRecyclerViewAdapter mAdapter;
    private ArrayList it = new ArrayList<>();
    private RecyclerView recyclerView;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mContext=activity;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_movie_comments, container, false);

        return rootView;
    }

    public ArrayList<Comment> getData() throws JSONException {
        /*
        JSONArray jComments = new JSONArray(value);
        String mComment;

        JSONObject jCommentData;
        Comment item = new Comment(null, null);
        Comment item2 = new Comment(null, null);
        Comment item3 = new Comment(null, null);
        Comment item4 = new Comment(null, null);
        Comment item5 = new Comment(null, null);

        // Is there a better way to load Comment objects into an array list?
        // In my first iteration, I was only adding the last Comment -- it replicated 3 times somehow


        for (int i=0;i<jComments.length();i++){
            mComment = jComments.getString(i); // Log.v("Comment string: ", mComment);
            jCommentData = new JSONObject(mComment);
            if(i==0){
                item.setmAuthor(jCommentData.getString("author")); Log.v("AUTHOR: ", jCommentData.getString("author"));
                item.setmComment(jCommentData.getString("content")); Log.v("COMMENT: ", jCommentData.getString("content"));
                it.add(item);
            }
            if(i==1){
                item2.setmAuthor(jCommentData.getString("author")); Log.v("AUTHOR: ", jCommentData.getString("author"));
                item2.setmComment(jCommentData.getString("content")); Log.v("COMMENT: ", jCommentData.getString("content"));
                it.add(item2);
            }
            if(i==2){
                item3.setmAuthor(jCommentData.getString("author")); Log.v("AUTHOR: ", jCommentData.getString("author"));
                item3.setmComment(jCommentData.getString("content")); Log.v("COMMENT: ", jCommentData.getString("content"));
                it.add(item3);
            }
        }
        for (int i=0;i<jComments.length();i++){
            //Log.v("IT A "+i+" :", String.valueOf(it.get(i).getmAuthor()));
            //Log.v("IT C "+i+" :", String.valueOf(it.get(i).getmComment()));
        }
        */

        it.add(new Comment("Jonathan", "//item.setmAuthor(jCommentData.getString(\"author\")); Log.v(\"AUTHOR: \", jCommentData.getString(\"author\"));\n" +
                "            //item.setmComment(jCommentData.getString(\"content\")); Log.v(\"COMMENT: \", jCommentData.getString(\"content\"));"));
        it.add(new Comment("Jonathan", "//item.setmAuthor(jCommentData.getString(\"author\")); Log.v(\"AUTHOR: \", jCommentData.getString(\"author\"));\n" +
                "            //item.setmComment(jCommentData.getString(\"content\")); Log.v(\"COMMENT: \", jCommentData.getString(\"content\"));"));
        it.add(new Comment("Jonathan", "//item.setmAuthor(jCommentData.getString(\"author\")); Log.v(\"AUTHOR: \", jCommentData.getString(\"author\"));\n" +
                "            //item.setmComment(jCommentData.getString(\"content\")); Log.v(\"COMMENT: \", jCommentData.getString(\"content\"));"));
        it.add(new Comment("Jonathan", "//item.setmAuthor(jCommentData.getString(\"author\")); Log.v(\"AUTHOR: \", jCommentData.getString(\"author\"));\n" +
                "            //item.setmComment(jCommentData.getString(\"content\")); Log.v(\"COMMENT: \", jCommentData.getString(\"content\"));"));
        it.add(new Comment("Jonathan", "//item.setmAuthor(jCommentData.getString(\"author\")); Log.v(\"AUTHOR: \", jCommentData.getString(\"author\"));\n" +
                "            //item.setmComment(jCommentData.getString(\"content\")); Log.v(\"COMMENT: \", jCommentData.getString(\"content\"));"));
        it.add(new Comment("Jonathan", "//item.setmAuthor(jCommentData.getString(\"author\")); Log.v(\"AUTHOR: \", jCommentData.getString(\"author\"));\n" +
                "            //item.setmComment(jCommentData.getString(\"content\")); Log.v(\"COMMENT: \", jCommentData.getString(\"content\"));"));
        it.add(new Comment("Jonathan", "//item.setmAuthor(jCommentData.getString(\"author\")); Log.v(\"AUTHOR: \", jCommentData.getString(\"author\"));\n" +
                "            //item.setmComment(jCommentData.getString(\"content\")); Log.v(\"COMMENT: \", jCommentData.getString(\"content\"));"));
        it.add(new Comment("Jonathan", "//item.setmAuthor(jCommentData.getString(\"author\")); Log.v(\"AUTHOR: \", jCommentData.getString(\"author\"));\n" +
                "            //item.setmComment(jCommentData.getString(\"content\")); Log.v(\"COMMENT: \", jCommentData.getString(\"content\"));"));

        return it;
    }
}
