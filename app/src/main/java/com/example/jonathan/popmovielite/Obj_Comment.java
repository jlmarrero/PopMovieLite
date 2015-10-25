package com.example.jonathan.popmovielite;

/**
 * Created by Jonathan on 9/25/2015.
 */
public class Obj_Comment {
    private String mAuthor;
    private String mComment;
    public Obj_Comment(String author, String comment){
        mAuthor = author;
        mComment = comment;
    }

    public String getmComment() {
        return mComment;
    }

    public void setmComment(String mComment) {
        mComment = mComment;
    }

    public String getmAuthor() {
        return mAuthor;
    }

    public void setmAuthor(String mAuthor) {
        mAuthor = mAuthor;
    }
}
