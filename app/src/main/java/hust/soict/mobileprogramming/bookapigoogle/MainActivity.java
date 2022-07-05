package hust.soict.mobileprogramming.bookapigoogle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    Button mBtnSearch = null;
    TextView mTxtTitle = null;
    TextView mTxtAuthors = null;
    EditText mEdtQueryString = null;
    ProgressBar mProgressbar = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTxtTitle = findViewById(R.id.txtTitle);
        mTxtAuthors = findViewById(R.id.txtAuthors);
        mEdtQueryString = findViewById(R.id.edtQueryString);

        mProgressbar = findViewById(R.id.progressBar);

        mBtnSearch = findViewById(R.id.btnSearch);
        mBtnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String queryString = mEdtQueryString.getText().toString();
                ConnectivityManager connMgr = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo networkInfo = null;
                if (connMgr != null) {
                    networkInfo = connMgr.getActiveNetworkInfo();
                }
                if(networkInfo.isAvailable()) {
                    //new FetchBookAsyncTask(mTxtTitle, mTxtAuthors, mProgressbar).execute(queryString);
                    LoaderManager loaderManager = LoaderManager.getInstance(MainActivity.this);
                    LoaderCallbacksImplementation loaderCallbacksImplementation = new LoaderCallbacksImplementation(MainActivity.this, mEdtQueryString);

                    loaderManager.restartLoader(0, null, loaderCallbacksImplementation);
                }
            }
        });
    }

    public TextView getmTxtTitle()
    {
        return mTxtTitle;
    }
    public TextView getmTxtAuthors()
    {
        return mTxtAuthors;
    }
}