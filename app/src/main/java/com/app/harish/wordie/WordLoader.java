package com.app.harish.wordie;


import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.AsyncTaskLoader;

public class WordLoader extends AsyncTaskLoader<String> {

    private String mQuery;

    WordLoader(@NonNull Context context , String queryString) {
        super(context);
        mQuery = queryString;
    }

    @Override
    protected void onStartLoading() {
        super.onStartLoading();
        forceLoad();
    }

    @Nullable
    @Override
    public String loadInBackground() {


        return GetJSONClass.getJSON(mQuery);
    }

}
