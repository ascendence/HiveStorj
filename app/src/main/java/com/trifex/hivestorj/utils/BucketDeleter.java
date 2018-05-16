package com.trifex.hivestorj.utils;

import android.Manifest;
import android.app.Activity;
import android.app.NotificationManager;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import io.storj.libstorj.Bucket;
import io.storj.libstorj.DeleteBucketCallback;
import io.storj.libstorj.android.StorjAndroid;
import name.org.trifex.HiveStorj.R;

/**
 * Created by ascendance on 5/16/2018.
 */

public class BucketDeleter implements DeleteBucketCallback {

    static final int PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE = 1;

    private Activity mActivity;
    private Bucket mBucket;
    private DeleteBucketCallback mCallback;

    private NotificationManager mNotifyManager;
    private NotificationCompat.Builder mBuilder;

    Map<String, Long> lastNotifiedMap = Collections.synchronizedMap(new HashMap<String, Long>());

    static boolean deleted = false;

    BucketDeleter(Activity activity, Bucket bucket){
        mActivity = activity;
        mBucket = bucket;
    }

    public void delete(){
        if (isPermissionGranted()) {
            doDelete();
        }
        else {
            requestPermission();
        }
    }

    public void doDelete(){
        StorjAndroid.getInstance(mActivity).deleteBucket(mBucket, BucketDeleter.this);


    }

    private boolean isPermissionGranted() {
        return ContextCompat.checkSelfPermission(mActivity,
                Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED;
    }

    private void requestPermission() {
        ActivityCompat.requestPermissions(mActivity,
                new String[] { Manifest.permission.WRITE_EXTERNAL_STORAGE },
                PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE);
    }

    void onRequestPermissionsResult(@NonNull int[] grantResults) {
        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            doDelete();
        } else {
            Snackbar.make(mActivity.findViewById(R.id.browse_list),
                    R.string.download_permission_denied,
                    Snackbar.LENGTH_LONG).show();
        }
    }

    @Override
    public void onBucketDeleted(String bucketId) {
        Snackbar.make(mActivity.findViewById(R.id.browse_list),
                R.string.folder_deleted,
                Snackbar.LENGTH_LONG).show();
        deleted = true;

    }

    @Override
    public void onError(String bucketId, int code, String message) {

    }
}
