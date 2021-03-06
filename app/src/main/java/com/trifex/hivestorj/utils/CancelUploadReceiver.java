package com.trifex.hivestorj.utils;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import io.storj.libstorj.android.StorjAndroid;

/**
 * Created by ascendance on 4/29/2018.
 */

public class CancelUploadReceiver extends BroadcastReceiver {


    public static final String NOTIFICATION_ID = "notificationId";
    public static final String UPLOAD_STATE = "uploadState";

    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle extras = intent.getExtras();
        if (extras != null) {
            int id = extras.getInt(NOTIFICATION_ID);
            long state = extras.getLong(UPLOAD_STATE);

            StorjAndroid.getInstance(context).cancelUpload(state);

            NotificationManager notifyManager = (NotificationManager)
                    context.getSystemService(Context.NOTIFICATION_SERVICE);
            notifyManager.cancel(id);
        }
    }
}
