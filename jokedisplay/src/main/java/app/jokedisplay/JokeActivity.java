package app.jokedisplay;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;


public class JokeActivity extends AppCompatActivity {

    public final static String JOKE_TEXT_EXTRA = "app.jokedisplay.EXTRA_JOKE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_joke);


        String joke =   getIntent().getStringExtra(JOKE_TEXT_EXTRA);

        TextView jokeText = (TextView) findViewById(R.id.joke_text);
        jokeText.setText(joke);

    }
}
