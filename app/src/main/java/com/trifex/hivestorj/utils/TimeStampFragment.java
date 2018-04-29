package com.trifex.hivestorj.utils;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import io.storj.libstorj.Storj;
import name.org.trifex.HiveStorj.R;

/**
 * Created by ascendance on 4/29/2018.
 */

public class TimeStampFragment extends Fragment {
    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public TimeStampFragment() {
    }

    @SuppressLint("SetTextI18n")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.content_timestamp, container, false);

        final TextView valueView = (TextView) rootView.findViewById(R.id.value);
        valueView.setText(Long.toString(Storj.getTimestamp()));

        final Button button = (Button) rootView.findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                valueView.setText(Long.toString(Storj.getTimestamp()));
            }
        });

        return rootView;
    }


}
