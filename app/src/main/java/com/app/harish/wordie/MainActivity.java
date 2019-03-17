package com.app.harish.wordie;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<String> {

    EditText word;
    TextView defin;
    TextView POS;
    TextView DnPOS;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        word = findViewById(R.id.wordinput);
        defin = findViewById(R.id.defn);
        POS = findViewById(R.id.partsofspeech);
        DnPOS = findViewById(R.id.DnPOS);

        Toolbar wToolbar = findViewById(R.id.wordietoolbar);
        setSupportActionBar(wToolbar);
    }


    public void searchWords(View view) {

        String inputWord = word.getText().toString();

        // Hide the keyboard when the button is pushed.
        InputMethodManager inputManager = (InputMethodManager)
                getSystemService(Context.INPUT_METHOD_SERVICE);
        if (inputManager != null) {
            inputManager.hideSoftInputFromWindow(view.getWindowToken(),
                    InputMethodManager.HIDE_NOT_ALWAYS);
        }

        // Check the status of the network connection.
        ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = null;
        if (connMgr != null) {
            networkInfo = connMgr.getActiveNetworkInfo();
        }

        // If the network is available, connected, and the search field
        // is not empty, start a WordLoader AsyncTask.
        if (networkInfo != null && networkInfo.isConnected()
                && inputWord.length() != 0) {

            Bundle queryBundle = new Bundle();
            queryBundle.putString("queryString", inputWord);
            getSupportLoaderManager().restartLoader(0, queryBundle, this);

            defin.setText(R.string.loading);
        }
        else {
            if (inputWord.length() == 0) {
                defin.setText(R.string.no_input);
            } else {
                defin.setText(R.string.no_network);
            }
        }

    }

    @NonNull
    @Override
    public Loader<String> onCreateLoader(int id, @Nullable Bundle args) {
        String queryString = "";

        if (args != null) {
            queryString = args.getString("queryString");
        }

        return new WordLoader(this, queryString);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<String> loader, String data) {

        JSONArray wordsArray;
        JSONObject wordObject;
        String partOfSpeech = "";
        String definition = "";
        try {
            wordsArray = new JSONArray(data);
            wordObject = wordsArray.getJSONObject(0);
            partOfSpeech = wordObject.getString("partOfSpeech");
            definition = wordObject.getString("text");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        if (definition != null) {
            DnPOS.setText(R.string.DnPOS);
            defin.setText(definition);
            POS.setText(partOfSpeech);
        } else {
            DnPOS.setText(R.string.no_results);
        }
    }

    @Override
    public void onLoaderReset(@NonNull Loader<String> loader) {

    }
}

