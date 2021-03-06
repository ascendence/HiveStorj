package com.trifex.hivestorj.utils;

/**
 * Created by ascendance on 4/29/2018.
 */


import android.app.Activity;
import android.os.Bundle;
import android.os.Process;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import io.storj.libstorj.GetInfoCallback;
import io.storj.libstorj.android.StorjAndroid;
import name.org.trifex.HiveStorj.R;


public class BridgeInfoFragment extends Fragment implements GetInfoCallback{

    private TextView mValue;
    private ProgressBar mProgress;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public BridgeInfoFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.content_bridge_info, container, false);

        mValue = (TextView) rootView.findViewById(R.id.value);
        mProgress = (ProgressBar) rootView.findViewById(R.id.progress);

        final Button button = (Button) rootView.findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                getBridgeInfo();
            }
        });

        getBridgeInfo();

        return rootView;
    }

    private void getBridgeInfo() {
        mProgress.setVisibility(View.VISIBLE);
        mValue.setVisibility(View.INVISIBLE);

        new Thread() {
            @Override
            public void run() {
                Process.setThreadPriority(Process.THREAD_PRIORITY_BACKGROUND);
                StorjAndroid.getInstance(getContext())
                        .getInfo(BridgeInfoFragment.this);
            }
        }.start();
    }

    public void onInfoReceived(final String title, final String description, final String version, final String host) {
        Activity activity = getActivity();
        if (activity != null) {
            activity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    String info = String.format("Title: %s\nDescription: %s\nVersion: %s\nHost: %s",
                            title,
                            description,
                            version,
                            host);
                    mValue.setText(info);
                    mProgress.setVisibility(View.GONE);
                    mValue.setVisibility(View.VISIBLE);
                }
            });
        }
    }

    @Override
    public void onError(final int code, final String message) {
        Activity activity = getActivity();
        if (activity != null) {
            activity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    mValue.setText(message);
                    mProgress.setVisibility(View.GONE);
                    mValue.setVisibility(View.VISIBLE);
                }
            });
        }
    }

}