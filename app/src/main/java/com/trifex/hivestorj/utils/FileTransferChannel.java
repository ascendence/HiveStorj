package com.trifex.hivestorj.utils;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;

import name.org.trifex.HiveStorj.R;

/**
 * Created by ascendance on 4/29/2018.
 */

public class FileTransferChannel {


    public static final String ID = "file_transfer";

    public static void create(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationManager notificationManager = (NotificationManager)
                    context.getSystemService(Context.NOTIFICATION_SERVICE);
            // The user-visible name of the channel.
            CharSequence name = context.getString(R.string.channel_name);
            // The user-visible description of the channel.
            String description = context.getString(R.string.channel_description);
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new android.app.NotificationChannel(ID, name, importance);
            // Configure the notification channel.
            channel.setDescription(description);
            channel.enableLights(false);
            channel.enableVibration(false);
            notificationManager.createNotificationChannel(channel);
        }
    }
}
