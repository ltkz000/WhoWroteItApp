package hust.soict.mobileprogramming.bookapigoogle;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.ref.WeakReference;

public class LoaderCallbacksImplementation implements LoaderManager.LoaderCallbacks {
    WeakReference<EditText> mWEdtQueryString = null;
    WeakReference<TextView> mWTitle = null;
    WeakReference<TextView> mWAuthors = null;

    MainActivity mMainActivity = null;

    public LoaderCallbacksImplementation(MainActivity activity, EditText _edt)
    {
        mWEdtQueryString = new WeakReference<>(_edt);
        mMainActivity = activity;
    }

    @NonNull
    @Override
    public Loader onCreateLoader(int id, @Nullable Bundle args) {
        String queryString = mWEdtQueryString.get().getText().toString();
        return new FetchBookAsyncTaskLoader(mMainActivity, queryString);
    }

    @Override
    public void onLoadFinished(@NonNull Loader loader, Object data) {
        String jsonResult = (String)data;
        //parse json
        String title = "";
        String authors = "";

        if(jsonResult != null && !jsonResult.isEmpty()) {
            try {
                JSONObject jsonObject = new JSONObject(jsonResult);
                String kind = jsonObject.getString("kind");
                int totalItems = jsonObject.getInt("totalItems");
                JSONArray items = jsonObject.getJSONArray("items");

                if (items.length() > 0) {
                    JSONObject item = items.getJSONObject(0);
                    if (item != null) {
                        JSONObject volumnInfo = item.getJSONObject("volumeInfo");

                        if (volumnInfo != null) {
                            title = volumnInfo.getString("title");
                            authors = volumnInfo.getString("authors");
                        }
                    }
                }


            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        mMainActivity.getmTxtTitle().setText(title);
        mMainActivity.getmTxtAuthors().setText(authors);
    }

    @Override
    public void onLoaderReset(@NonNull Loader loader) {

    }
}
