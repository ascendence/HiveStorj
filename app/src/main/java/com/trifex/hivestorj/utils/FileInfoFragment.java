package com.trifex.hivestorj.utils;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.text.format.Formatter;
import android.view.View;
import android.widget.TextView;

import io.storj.libstorj.Bucket;
import io.storj.libstorj.File;
import name.org.trifex.HiveStorj.R;

/**
 * Created by ascendance on 4/29/2018.
 */

public class FileInfoFragment extends DialogFragment {


    public static final String FILE = "file";

    interface DownloadListener {
        void onDownload(Bucket bucket, File file);

    }

    interface DeleteListener{
        void onDelete(Bucket bucket, File file);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final Bucket bucket = (Bucket) getArguments().getSerializable(FilesFragment.BUCKET);
        final File file = (File) getArguments().getSerializable(FILE);
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(R.string.title_fileinfo)
                .setMessage(String.format("ID: %s\nBucket: %s\nName: %s\nCreated: %s\nDecrypted: %b\nSize: %s\nMIME Type: %s\nErasure: %s\nIndex: %s\nHMAC: %s",
                        file.getId(),
                        file.getBucketId(),
                        file.getName(),
                        file.getCreated(),
                        file.isDecrypted(),
                        Formatter.formatFileSize(getContext(), file.getSize()),
                        file.getMimeType(),
                        file.getErasure(),
                        file.getIndex(),
                        file.getHMAC()))
                .setPositiveButton(R.string.button_download, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        ((DownloadListener) getActivity()).onDownload(bucket, file);
                    }
                })
        .setNeutralButton(R.string.button_delete, new DialogInterface.OnClickListener(){
            public void onClick (DialogInterface dialog, int id){
                //(DeleteListener) getActivity()).onDelete(bucket, file);
                ((DeleteListener) getActivity()).onDelete(bucket, file);
            }
        });
        // Create the AlertDialog object and return it
        return builder.create();
    }


}
