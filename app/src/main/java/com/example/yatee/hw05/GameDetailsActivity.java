package com.example.yatee.hw05;
/*
InClass 06
GameDetailsActivity
Yateen Kedare
 */
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.view.ActionMode;
import android.util.Log;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

public class GameDetailsActivity extends AppCompatActivity implements GetGameDetailsAsync.IDataDetails{

    ProgressBar progressBar;
    GameDetails gameDetails;
    Button finish,similarGames;
    String urlDetails;
    TextView titleTextView, overViewTV, genreTV, publisherTV;
    ImageView iv;
    Boolean viewVideo = false;
    Boolean ifSimilarGames = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_details);

        progressBar = (ProgressBar) findViewById(R.id.progressBar2);
        finish = (Button) findViewById(R.id.finish);
        similarGames = (Button) findViewById(R.id.similarGames);

        titleTextView = (TextView) findViewById(R.id.gameTitle);
        overViewTV = (TextView) findViewById(R.id.tvOverview);
        genreTV = (TextView) findViewById(R.id.genre);
        publisherTV = (TextView) findViewById(R.id.publisher);
        iv = (ImageView) findViewById(R.id.imageView);

        ifSimilarGames = getIntent().getBooleanExtra("IFSIMILAR",true);
        if(ifSimilarGames) {
            urlDetails = getIntent().getStringExtra("GAME_DETAILS_ID");

            finish.setEnabled(false);
            similarGames.setEnabled(false);

            progressBar.setVisibility(View.VISIBLE);

            new GetGameDetailsAsync(this).execute(urlDetails);
        }
        else{
            similarGames.setVisibility(View.INVISIBLE);
            finish.setEnabled(true);

            titleTextView.setText(getIntent().getStringExtra("TITLE"));
            genreTV.setText(getIntent().getStringExtra("GENRE"));
            publisherTV.setText(getIntent().getStringExtra("PUBLISHER"));
            overViewTV.setText(getIntent().getStringExtra("OVERVIEW"));
            Picasso.with(GameDetailsActivity.this)
                    .load(getIntent().getStringExtra("IMAGEURL"))
                    .into(iv, new Callback() {
                        @Override
                        public void onSuccess() {
                            progressBar.setVisibility(View.GONE);
                        }

                        @Override
                        public void onError() {

                        }
                    });

        }
        finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public void returnedDetailsValues(final GameDetails gameDetails) {
        if(gameDetails!=null) {
            progressBar.setVisibility(View.INVISIBLE);
            finish.setEnabled(true);
            if(ifSimilarGames)
                similarGames.setEnabled(true);

            titleTextView.setText(gameDetails.getTitle());
            genreTV.setText(gameDetails.getGenre());
            publisherTV.setText(gameDetails.getPublisher());
            overViewTV.setText(gameDetails.getOverview());

            Picasso.with(GameDetailsActivity.this)
                    .load(gameDetails.getImageURL())
                    .into(iv, new Callback() {
                        @Override
                        public void onSuccess() {
                            progressBar.setVisibility(View.GONE);
                        }

                        @Override
                        public void onError() {

                        }
                    });
            if(ifSimilarGames)
                similarGames.setOnClickListener(new View.OnClickListener() {
                @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(GameDetailsActivity.this, SimilarGamesActivity.class);
                        intent.putStringArrayListExtra("SIMILAR_IDS", gameDetails.getSimilarGames());
                        intent.putExtra("TITLE", gameDetails.getTitle());
                        startActivity(intent);
                    }
                });


            Log.d("Game Details", gameDetails.toString());
        }
        else{
            new GetGameDetailsAsync(this).execute(urlDetails);
            Toast.makeText(getApplicationContext(), "Failed to get XML file 502 Error:: Retrying to load", Toast.LENGTH_LONG).show();
        }
    }

}
