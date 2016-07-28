package com.udacity.gradle.builditbigger;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.util.Pair;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.aduran.myapplication.backend.myApi.MyApi;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;

import java.io.IOException;

import app.jokedisplay.JokeActivity;


public class MainActivity extends AppCompatActivity {
    private ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void tellJoke(View view){
        dialog = ProgressDialog.show(this, "",
                getString(com.udacity.gradle.builditbigger.R.string.loading_text), true);

        new EndpointsAsyncTask().execute(new Pair<Context, ProgressDialog>(this, dialog));
    }

    class EndpointsAsyncTask extends AsyncTask<Pair<Context, ProgressDialog>,Void, String> {
        private MyApi myApiService = null;
        private Context context;
        private ProgressDialog dialog;

        @Override
        protected String doInBackground(Pair<Context, ProgressDialog>... params) {

            if(myApiService == null) {  // Only do this once
                MyApi.Builder builder = new MyApi.Builder(AndroidHttp.newCompatibleTransport(), new AndroidJsonFactory(), null)
                        .setRootUrl("https://builditbigger-1385.appspot.com/_ah/api/");
                // end options for devappserver

                myApiService = builder.build();
            }

            context = params[0].first;
            dialog = params[0].second;

            try {
                return myApiService.sayJoke().execute().getData();
            } catch (IOException e) {
                return e.getMessage();
            }
        }

        @Override
        protected void onPostExecute(String result) {

            Intent intent = new Intent(context, JokeActivity.class);
            intent.putExtra(JokeActivity.JOKE_TEXT_EXTRA, result);

            startActivity(intent);
            dialog.dismiss();
        }
    }


}
