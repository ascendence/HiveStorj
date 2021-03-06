package com.trifex.hivestorj.utils;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import io.storj.libstorj.Bucket;
import io.storj.libstorj.File;
import name.org.trifex.HiveStorj.R;

/**
 * Created by ascendance on 4/29/2018.
 */

public class FilesActivity extends AppCompatActivity implements FileInfoFragment.DownloadListener, FileInfoFragment.DeleteListener {


    private FileDownloader mDownloader;
    private FileDeleter mDeleter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Show the Up button in new Intent(this, DetailActivity.class)the action bar.
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
                Bucket bucket = (Bucket) extras.getSerializable(FilesFragment.BUCKET);
                setTitle(bucket.getName());

                FilesFragment fragment = new FilesFragment();
                Bundle bundle = new Bundle();
                bundle.putSerializable(FilesFragment.BUCKET, bucket);
                fragment.setArguments(bundle);
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
            Intent intent = new Intent(this, DetailActivity.class);
            intent.putExtra(DetailActivity.EXTRA_INDEX, Fragments.BROWSE.ordinal());
            navigateUpTo(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case FileDownloader.PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE: {
                if (mDownloader != null) {
                    mDownloader.onRequestPermissionsResult(grantResults);
                }

                if (mDeleter != null) {
                    mDeleter.onRequestPermissionsResult(grantResults);
                }

                break;
            }
        }
    }

    @Override
    public void onDownload(Bucket bucket, File file) {
        mDownloader = new FileDownloader(this, bucket, file);
        mDownloader.download();
    }

    @Override
    public void onDelete(Bucket bucket, File file) {
        mDeleter = new FileDeleter(this, bucket, file);
        mDeleter.delete();


        //waits before refreshing screen
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                refreshView();
            }
        }, 2000);



    }

    private void refreshView(){
        if (mDeleter.isDeleted() == true){
            finish();
            startActivity(getIntent());
        }
    }
}
