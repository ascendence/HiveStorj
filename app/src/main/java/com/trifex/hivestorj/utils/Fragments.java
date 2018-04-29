package com.trifex.hivestorj.utils;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;

import name.org.trifex.HiveStorj.R;

/**
 * Created by ascendance on 4/29/2018.
 */

public enum Fragments {


    BROWSE(R.string.title_buckets, BucketsFragment.class),
    REGISTER(R.string.title_register, RegisterFragment.class),
    KEYS(R.string.title_keys, KeysFragment.class),
    BRIDGE_INFO(R.string.title_bridge_info, BridgeInfoFragment.class),
    MNEMONIC(R.string.title_mnemonic, MnemonicFragment.class),
    TIMESTAMP(R.string.title_timestamp, TimeStampFragment.class),
    LIBS(R.string.title_libs, LibsFragment.class);

    private static final String TAG = "Fragments";

    private int title;
    private Class<? extends Fragment> clazz;

    Fragments(int title, Class<? extends Fragment> clazz) {
        this.title = title;
        this.clazz = clazz;
    }

    public static Integer[] getTitles() {
        Fragments[] fragments = values();
        Integer[] titles = new Integer[fragments.length];
        for (int i = 0; i < titles.length; i++) {
            titles[i] = fragments[i].getTitle();
        }
        return titles;
    }

    public int getTitle() {
        return title;
    }

    @Nullable
    public Fragment newInstance() {
        try {
            return clazz.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            Log.e(TAG, "newInstance: ", e);
            return null;
        }
    }
}
