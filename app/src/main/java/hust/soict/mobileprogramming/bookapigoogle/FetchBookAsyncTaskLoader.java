package hust.soict.mobileprogramming.bookapigoogle;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.loader.content.AsyncTaskLoader;

public class FetchBookAsyncTaskLoader extends AsyncTaskLoader<String> {

    private String mQueryString;
    public FetchBookAsyncTaskLoader(@NonNull Context context, String queryString) {
        super(context);
        mQueryString = queryString;
    }

    @Nullable
    @Override
    public String loadInBackground() {
        if(!mQueryString.isEmpty()) {
            String jsonString = NetUtils.getBookfromGoogle(mQueryString);
            return jsonString;
        }
        Log.d("hiephv", "AsyncTask doinBackground is Running");
        return null;
    }

    @Override
    protected void onStartLoading() {
        super.onStartLoading();
        forceLoad();
    }
}
