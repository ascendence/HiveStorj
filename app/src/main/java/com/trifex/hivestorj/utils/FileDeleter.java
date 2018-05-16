package com.trifex.hivestorj.utils;

import android.Manifest;
import android.app.Activity;
import android.app.NotificationManager;
import android.content.Intent;
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
import io.storj.libstorj.DeleteFileCallback;
import io.storj.libstorj.File;
import io.storj.libstorj.android.StorjAndroid;
import name.org.trifex.HiveStorj.R;

import static android.support.v4.content.ContextCompat.startActivity;

/**
 * Created by ascendance on 5/16/2018.
 */

public class FileDeleter implements DeleteFileCallback {

    static final int PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE = 1;

    private Activity mActivity;
    private Bucket mBucket;
    private File mFile;

    private NotificationManager mNotifyManager;
    private NotificationCompat.Builder mBuilder;

    Map<String, Long> lastNotifiedMap = Collections.synchronizedMap(new HashMap<String, Long>());

    static boolean deleted = false;

    FileDeleter(Activity activity, Bucket bucket, File file) {
        mActivity = activity;
        mBucket = bucket;
        mFile = file;
    }

    public void delete(){
        if (isPermissionGranted()) {
            doDelete();
        }
        else {
            requestPermission();
        }
    }

    public boolean isDeleted(){
        return deleted;
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

    private void doDelete(){

        StorjAndroid.getInstance(mActivity).deleteFile(mBucket, mFile, FileDeleter.this);
    }





    @Override
    public void onFileDeleted(String fileId) {
        Snackbar.make(mActivity.findViewById(R.id.browse_list),
                R.string.file_deleted,
                Snackbar.LENGTH_LONG).show();
        deleted = true;




    }

    @Override
    public void onError(String fileId, int code, String message) {

    }
}
