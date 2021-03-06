package com.trifex.hivestorj.utils;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import io.storj.libstorj.Keys;
import io.storj.libstorj.Storj;
import io.storj.libstorj.android.StorjAndroid;
import name.org.trifex.HiveStorj.R;

/**
 * Created by ascendance on 4/29/2018.
 */

public class KeysFragment extends Fragment {

    private static final String TAG = "KeyFragment";

    private Button button;
    private ProgressBar progress;
    private Context appContext;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public KeysFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.content_keys, container, false);

        final EditText userEdit = (EditText) rootView.findViewById(R.id.edit_user);
        final EditText passEdit = (EditText) rootView.findViewById(R.id.edit_pass);
        final EditText mnemonicEdit = (EditText) rootView.findViewById(R.id.edit_mnemonic);

        button = (Button) rootView.findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String user = userEdit.getText().toString();
                String pass = passEdit.getText().toString();
                String mnemonic = mnemonicEdit.getText().toString();

                boolean error = false;

                if (!isValidEmail(user)) {
                    userEdit.setError(getText(R.string.error_keys_user));
                    error = true;
                } else {
                    userEdit.setError(null);
                }

                if (!isValidPassword(pass)) {
                    passEdit.setError(getText(R.string.error_keys_pass));
                    error = true;
                } else {
                    passEdit.setError(null);
                }

                if (!isValidMnemonic(mnemonic)) {
                    mnemonicEdit.setError(getText(R.string.error_keys_mnemonic));
                    error = true;
                } else {
                    mnemonicEdit.setError(null);
                }

                if (!error) {
                    button.setEnabled(false);
                    progress.setVisibility(View.VISIBLE);
                    new ImportKeysTask().execute(user, pass, mnemonic);
                }
            }
        });

        progress = (ProgressBar) rootView.findViewById(R.id.progress);

        appContext = getActivity().getApplicationContext();

        return rootView;
    }

    private boolean isValidEmail(String email) {
        String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    private boolean isValidPassword(String pass) {
        return pass != null && pass.length() > 0;
    }

    private boolean isValidMnemonic(String mnemonic) {
        return Storj.checkMnemonic(mnemonic);
    }

    private class ImportKeysTask extends AsyncTask<String, Void, Integer> {
        @Override
        protected Integer doInBackground(String... params) {
            String user = params[0];
            String pass = params[1];
            String mnemonic = params[2];

            Storj storj = StorjAndroid.getInstance(getContext());
            try {
                int result = storj.verifyKeys(new Keys(user, pass, mnemonic));
                if (result != Storj.NO_ERROR) {
                    switch (result) {
                        case Storj.HTTP_UNAUTHORIZED:
                            return R.string.keys_import_invalid_credentials;
                        case Storj.STORJ_META_DECRYPTION_ERROR:
                            return R.string.keys_import_invalid_mnemonic;
                        default:
                            return R.string.keys_import_fail;
                    }
                }
            } catch (InterruptedException e) {
                Log.e(TAG, "Verify keys interrupted", e);
                return R.string.keys_import_fail;
            }

            if (!storj.importKeys(new Keys(user, pass, mnemonic), "")) {
                return R.string.keys_import_fail;
            }

            return R.string.keys_import_success;
        }

        @Override
        protected void onPostExecute(Integer message) {
            button.setEnabled(true);
            progress.setVisibility(View.GONE);

            Toast.makeText(appContext, message, Toast.LENGTH_LONG).show();
        }
    }

}
