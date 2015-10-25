package com.example.jonathan.popmovielite;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;

/**
 * Created by Jonathan on 10/10/2015.
 */
public class Activity_Description extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_description);

        if (findViewById(R.id.fragment_container_description) != null) {

            if (savedInstanceState != null) {
                return;
            }

            Fragment_MovieDescriptionActivity firstFragment = new Fragment_MovieDescriptionActivity();
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment_container_description, firstFragment, "description fragment").commit();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_desc, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
