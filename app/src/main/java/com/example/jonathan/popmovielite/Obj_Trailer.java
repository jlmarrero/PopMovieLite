package com.example.jonathan.popmovielite;

/**
 * Created by Jonathan on 10/24/2015.
 */
public class Obj_Trailer {
    private String mTitle;
    private String mSource;

    public Obj_Trailer(String title, String source){
        this.mTitle = title;
        this.mSource = source;
    }

    public String getmTitle() {
        return mTitle;
    }

    public void setmTitle(String mTitle) {
        this.mTitle = mTitle;
    }

    public String getmSource() {
        return mSource;
    }

    public void setmSource(String mSource) {
        this.mSource = mSource;
    }
}
