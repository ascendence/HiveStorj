package com.trifex.hivestorj.utils;

import android.app.Activity;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;

import io.storj.libstorj.Bucket;

import io.storj.libstorj.DeleteBucketCallback;
import io.storj.libstorj.android.StorjAndroid;
import name.org.trifex.HiveStorj.R;

import static com.trifex.hivestorj.utils.BucketsFragment.mBucket;

/**
 * Created by ascendance on 5/16/2018.
 */

public class BucketInfoFragment extends android.support.v4.app.DialogFragment {

    public static final String BUCKET = "bucket";
    public static Bucket tempBucket;



    interface DeleteListener{
        void onDelete(Bucket bucket);


    }

    public Dialog onCreateDialog(Bundle savedInstanceState){
        final Bucket bucket = (mBucket);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(R.string.title_folderinfo)
                .setMessage(String.format("ID: %s\nCreated: %s",
                        bucket.getId(),
                        bucket.getCreated()));
        //.setNeutralButton(R.string.button_delete,  new DialogInterface.OnClickListener(){
            //public void onClick (DialogInterface dialog, int id){
                //(DeleteListener) getActivity()).onDelete(bucket, file);

                //System.out.println("DELETE CLICKED");
                //Fragment mParent =
                //System.out.println("BUCKET ID IS" + bucket.getId() + "PARENT ACT IS" + mParent.getPackageName());
                //((DeleteListener) mParent).onDelete(bucket);


                //((DeleteListener) getParentFragment()).onDelete(bucket);

            //}


        return builder.create();
    }

    public static void setBucket(Bucket bucket){
        tempBucket = bucket;
    }
}
