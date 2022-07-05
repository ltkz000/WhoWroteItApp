package hust.soict.mobileprogramming.bookapigoogle;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.ref.WeakReference;

public class FetchBookAsyncTask extends AsyncTask<String, Void, String> {

    WeakReference<TextView> mWTxtTitle;
    WeakReference<TextView> mWTxtAuthors;
    WeakReference<ProgressBar> mWProgressbar;

    public FetchBookAsyncTask(TextView _txtTitle, TextView _txtAuthors, ProgressBar _progressbar)
    {
        mWTxtTitle = new WeakReference<>(_txtTitle);
        mWTxtAuthors = new WeakReference<>(_txtAuthors);
        mWProgressbar = new WeakReference<>(_progressbar);
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

        mWProgressbar.get().setVisibility(View.VISIBLE);
    }

    @Override
    protected String doInBackground(String... strings) {
        String queryString = strings[0];

        if(!queryString.isEmpty()) {
            String jsonString = NetUtils.getBookfromGoogle(queryString);
            return jsonString;
        }
        Log.d("hiephv", "AsyncTask doinBackground is Running");
        return null;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);

        //parse json
        String title = "";
        String authors = "";

        if(s != null && !s.isEmpty()) {
            try {
                JSONObject jsonObject = new JSONObject(s);
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

        mWTxtTitle.get().setText(title);
        mWTxtAuthors.get().setText(authors);
        mWProgressbar.get().setVisibility(View.GONE);
        Log.d("hiephv", "AsyncTask onPostExecute is Running");
    }
}
