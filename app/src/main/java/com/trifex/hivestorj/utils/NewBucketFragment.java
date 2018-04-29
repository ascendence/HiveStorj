package com.trifex.hivestorj.utils;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;

import name.org.trifex.HiveStorj.R;

/**
 * Created by ascendance on 4/29/2018.
 */

public class NewBucketFragment extends DialogFragment {


    static final String BUCKET_NAME = "bucketName";

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        View v = getActivity().getLayoutInflater().inflate(R.layout.content_new_bucket, null);
        final EditText input = (EditText) v.findViewById(R.id.input);

        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(R.string.title_new_bucket)
                .setView(v)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Intent intent = getActivity().getIntent();
                        intent.putExtra(BUCKET_NAME, input.getText().toString());
                        getTargetFragment().onActivityResult(getTargetRequestCode(), Activity.RESULT_OK, intent);
                    }
                })
                .setNegativeButton(android.R.string.cancel, null);
        // Create the AlertDialog object
        final AlertDialog dialog = builder.create();

        // Update the enable state of the OK button when input changes
        input.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                enableOK(dialog, s);
            }
        });

        // Set the initial state of the OK button
        dialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {
                enableOK((AlertDialog) dialog, input.getText());
            }
        });

        // Return the dialog object
        return dialog;
    }

    private void enableOK(AlertDialog dialog, Editable input) {
        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(!TextUtils.isEmpty(input));
    }

}
