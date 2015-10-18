package com.example.jonathan.popmovielite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Jonathan on 10/16/2015.
 */
public class MovieDatabaseHelper {
    private MovieHelper helper;

    public MovieDatabaseHelper(Context context){
        helper = new MovieHelper(context);
    }

    public long insertData(Movie movie){
        ContentValues contentValues = new ContentValues();
        SQLiteDatabase db = helper.getWritableDatabase();

        contentValues.put(helper.MOVIE_ID, movie.getId());
        contentValues.put(helper.TITLE, movie.getTitle());
        contentValues.put(helper.POPULARITY, movie.getPopularity());
        contentValues.put(helper.RELEASE_DATE, movie.getRelease_date());
        contentValues.put(helper.VOTE_AVERAGE, movie.getVote_average());
        contentValues.put(helper.DESCRIPTION, movie.getDescription());
        contentValues.put(helper.POSTER_PATH, movie.getPoster_path());
        contentValues.put(helper.BACKDROP, movie.getBackdrop_path());
        contentValues.put(helper.TRAILER_JSON, movie.getTrailer_json());
        contentValues.put(helper.COMMENTS_JSON, movie.getComments_json());

        long dbCheck = db.insert(helper.TABLE_NAME, null, contentValues);
        db.close();
        return dbCheck;
    }

    public void clearData(){;
        SQLiteDatabase db = helper.getWritableDatabase();
        db.execSQL(helper.DROP_TABLE);
        helper.onCreate(db);
    }

    public Cursor getAllData(){;
        int i = 0;
        SQLiteDatabase db = helper.getWritableDatabase();
        StringBuffer buffer = new StringBuffer();
        String[] columns = {helper.MOVIE_ID, helper.TITLE, helper.POPULARITY,
                helper.RELEASE_DATE, helper.VOTE_AVERAGE, helper.DESCRIPTION, helper.POSTER_PATH,
                helper.BACKDROP, helper.TRAILER_JSON, helper.COMMENTS_JSON};
        Cursor cursor = db.query(helper.TABLE_NAME, columns, null, null, null, null, null);

        return cursor;
    }

    public String getMovie(String id){;
        SQLiteDatabase db = helper.getWritableDatabase();
        StringBuffer buffer = new StringBuffer();
        String[] columns = {helper.MOVIE_ID, helper.TITLE, helper.POPULARITY,
                helper.RELEASE_DATE, helper.VOTE_AVERAGE, helper.DESCRIPTION, helper.POSTER_PATH,
                helper.BACKDROP, helper.TRAILER_JSON, helper.COMMENTS_JSON};
        String[] selectArray = {id};

        Cursor cursor = db.query(helper.TABLE_NAME, columns, helper.MOVIE_ID + " = '" + id + "'", null, null, null, null, null);
        cursor.moveToFirst();
        int index1 = cursor.getColumnIndex(helper.DESCRIPTION);
        String description = cursor.getString(index1);

        buffer.append(description);
        return buffer.toString();
    }

    public boolean getRowExists(String id){
        SQLiteDatabase db = helper.getWritableDatabase();
        //result = db.execSQL("SELECT EXISTS(SELECT 1 FROM movies WHERE movie_id="+id+" LIMIT 1);");

        String sql = "SELECT * FROM movies WHERE movie_id = '" + id + "' LIMIT 1";
        Cursor data = db.rawQuery(sql, null);

        if (data.moveToFirst()) {
            return true; // record exists
        } else {
            return false; // record not found
        }



    }

    static class MovieHelper extends SQLiteOpenHelper {
        private static final String DATABASE_NAME = "movies.db";
        private static final String TABLE_NAME = "movies";
        private static final int DB_VERSION = 3;

        private static final String COLUMN_ID = "_id";
        private static final String MOVIE_ID = "movie_id";
        private static final String TITLE = "title";
        private static final String POPULARITY = "popularity";
        private static final String DESCRIPTION = "description";
        private static final String POSTER_PATH = "poster_path";
        private static final String RELEASE_DATE = "release_date";
        private static final String VOTE_AVERAGE = "vote_average";
        private static final String BACKDROP = "backdrop_path";
        private static final String TRAILER_JSON = "trailer_json";
        private static final String COMMENTS_JSON = "comments_json";

        // Database creation sql statement
        private static final String DATABASE_CREATE = "CREATE TABLE "
                + TABLE_NAME + "(" + COLUMN_ID
                + " integer primary key autoincrement, " + MOVIE_ID
                + " text not null, " + TITLE
                + " text not null, " + POPULARITY
                + " text not null, " + DESCRIPTION
                + " text not null, " + POSTER_PATH
                + " text not null, " + RELEASE_DATE
                + " text not null, " + VOTE_AVERAGE
                + " text not null, " + BACKDROP
                + " text not null, " + TRAILER_JSON
                + " text not null, " + COMMENTS_JSON
                + " text not null "
                + ");";
        //Drop table statement
        private static final String DROP_TABLE = "DROP TABLE "
                + TABLE_NAME;


        MovieHelper(Context context){
            super(context, DATABASE_NAME, null, DB_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            Log.v("ON CREATE: ", "WAS CALLED !!!");
            //db.execSQL(DROP_TABLE);
            db.execSQL(DATABASE_CREATE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            Log.v("ON UPDATE: ", "WAS CALLED !!!");

            if (newVersion > oldVersion) {
                db.execSQL(DROP_TABLE);
                onCreate(db);
            }
        }
    }
}
