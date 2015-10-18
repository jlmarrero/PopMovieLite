package com.example.jonathan.popmovielite;

/**
 * Created by Jonathan on 9/25/2015.
 */
public class Comment {
    private static String mAuthor;
    private static String mComment;
    public Comment(String author, String comment){
        mAuthor = author;
        mComment = comment;
    }

    public String getmComment() {
        return mComment;
    }

    public void setmComment(String mComment) {
        Comment.mComment = mComment;
    }

    public String getmAuthor() {
        return mAuthor;
    }

    public void setmAuthor(String mAuthor) {
        Comment.mAuthor = mAuthor;
    }
}
