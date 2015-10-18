package com.example.jonathan.popmovielite;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuItem;

/**
 * Created by Jonathan on 10/10/2015.
 */
public class DescriptionActivity extends FragmentActivity {
    private Context mContext=getApplication();
    //private Movie mMovie;
    private Intent intent = new Intent();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_description);

        if (findViewById(R.id.fragment_container_description) != null) {

            if (savedInstanceState != null) {
                return;
            }

            Fragment_MovieDescriptionActivity firstFragment = new Fragment_MovieDescriptionActivity();
            Fragment_MovieTrailersActivity secondFragment = new Fragment_MovieTrailersActivity();
            Fragment_MovieCommentsActivity thirdFragment = new Fragment_MovieCommentsActivity();
            // In case this activity was started with special instructions from an
            // Intent, pass the Intent's extras to the fragment as arguments
            // firstFragment.setArguments(getIntent().getExtras().getBundle("MOVIE"));
            // Add the fragment to the 'fragment_container' FrameLayout
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment_container_description, firstFragment, "my first fragement").commit();
                    //.add(R.id.fragment_container_trailers, secondFragment, "my second fragment")
                    //.add(R.id.fragment_container_comments, thirdFragment, "my third fragment").commit();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
