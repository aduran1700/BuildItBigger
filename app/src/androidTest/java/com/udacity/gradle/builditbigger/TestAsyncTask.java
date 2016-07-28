package com.udacity.gradle.builditbigger;

import android.os.AsyncTask;
import android.test.AndroidTestCase;

import com.example.aduran.myapplication.backend.myApi.MyApi;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;

import java.io.IOException;


/**
 * Created by Androcles on 7/27/2016.
 */
public class TestAsyncTask extends AndroidTestCase {
    public void testVerifyAsyncTaskResponse() {
        final AsyncTask<String, Void, String> myTask = new AsyncTask<String, Void, String>() {
            private MyApi myApiService = null;

            @Override
            protected String doInBackground(String... params) {
                if(myApiService == null) {  // Only do this once
                    MyApi.Builder builder = new MyApi.Builder(AndroidHttp.newCompatibleTransport(), new AndroidJsonFactory(), null)
                            .setRootUrl("https://builditbigger-1385.appspot.com/_ah/api/");
                    // end options for devappserver

                    myApiService = builder.build();
                }


                try {
                    return myApiService.sayJoke().execute().getData();
                } catch (IOException e) {
                    return e.getMessage();
                }
            }

            @Override
            protected void onPostExecute(String result) {

                assertTrue(!result.equals(""));

            }
        };

        myTask.execute("");
    }
}
