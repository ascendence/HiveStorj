package com.trifex.hivestorj.utils;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import name.org.trifex.HiveStorj.R;

/**
 * Created by ascendance on 4/29/2018.
 */

public class DetailActivity extends AppCompatActivity {


    public static final String EXTRA_INDEX = "index";
    public static Fragment mParent ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //mParent = getParentFragment();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);

        // Show the Up button in the action bar.
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        // savedInstanceState is non-null when there is fragment state
        // saved from previous configurations of this activity_main
        // (e.g. when rotating the screen from portrait to landscape).
        // In this case, the fragment will automatically be re-added
        // to its container so we don't need to manually add it.
        // For more information, see the Fragments API guide at:
        //
        // http://developer.android.com/guide/components/fragments.html
        //

        if (savedInstanceState == null) {
            // Create the detail fragment and add it to the activity_main
            // using a fragment transaction.
            Bundle extras = getIntent().getExtras();
            if (extras != null) {
                int index = extras.getInt(EXTRA_INDEX);
                setTitle(Fragments.values()[index].getTitle());
                Fragment fragment = Fragments.values()[index].newInstance();
                getSupportFragmentManager().beginTransaction()
                        .add(R.id.detail_container, fragment)
                        .commit();
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            // This ID represents the Home or Up button. In the case of this
            // activity_main, the Up button is shown. For
            // more details, see the Navigation pattern on Android Design:
            //
            // http://developer.android.com/design/patterns/navigation.html#up-vs-back
            //
            navigateUpTo(new Intent(this, MainActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public static void setmParent(Fragment parent){
        mParent = parent;
    }



}
