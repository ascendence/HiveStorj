package com.trifex.hivestorj.utils;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.readystatesoftware.chuck.ChuckInterceptor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import name.org.trifex.HiveStorj.R;
import okhttp3.OkHttpClient;

/**
 * Created by ascendance on 4/29/2018.
 */

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(new ChuckInterceptor(getApplicationContext()))
                .build();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(getTitle());

        View recyclerView = findViewById(R.id.main_list);
        assert recyclerView != null;
        setupRecyclerView((RecyclerView) recyclerView);

        FileTransferChannel.create(this);


    }

    private void setupRecyclerView(@NonNull RecyclerView recyclerView) {
        ArrayList<Integer> items = new ArrayList<>(Arrays.asList(Fragments.getTitles()));
        recyclerView.setAdapter(new SimpleItemRecyclerViewAdapter(items));
    }

    class SimpleItemRecyclerViewAdapter
            extends RecyclerView.Adapter<SimpleItemRecyclerViewAdapter.ViewHolder> {

        private final List<Integer> mValues;

        SimpleItemRecyclerViewAdapter(List<Integer> items) {
            mValues = items;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(android.R.layout.simple_list_item_1, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, final int position) {
            holder.mText.setText(mValues.get(position));

            holder.mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (getResources().getBoolean(R.bool.twoPaneMode)) {
                        Fragment fragment = Fragments.values()[position].newInstance();
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.detail_container, fragment)
                                .commit();
                    } else {
                        Context context = v.getContext();
                        Intent intent = new Intent(context, DetailActivity.class);
                        intent.putExtra(DetailActivity.EXTRA_INDEX, position);
                        context.startActivity(intent);
                    }
                }
            });
        }

        @Override
        public int getItemCount() {
            return mValues.size() - 2;
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            final View mView;
            final TextView mText;

            ViewHolder(View view) {
                super(view);
                mView = view;
                mText = (TextView) itemView.findViewById(android.R.id.text1);
            }

            @Override
            public String toString() {
                return super.toString() + " '" + mText.getText() + "'";
            }
        }
    }

}
