package com.example.yatee.hw05;
/*
InClass 06
GameDetailsActivity
Yateen Kedare
 */
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class SimilarGamesActivity extends AppCompatActivity implements GetGameDetailsAsync.IDataDetails{

    ProgressBar pb;
    ArrayAdapter<GameDetails> adapter =null;
    ArrayList<GameDetails> gameDetailses;
    ListView listView;
    Button finish;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_similar_games);

        Button finish = (Button) findViewById(R.id.finish);
        ArrayList<String> similarIds = new ArrayList<String>();
        similarIds = getIntent().getStringArrayListExtra("SIMILAR_IDS");
        TextView title  = (TextView)findViewById(R.id.textView);
        title.setText(getIntent().getStringExtra("TITLE"));
        listView = (ListView) findViewById(R.id.listViewSimilar);
        pb = (ProgressBar) findViewById(R.id.progressBar3);
        pb.setVisibility(View.VISIBLE);
        finish = (Button) findViewById(R.id.finish);

        if(similarIds.size() == 0){
            Toast.makeText(getApplicationContext(),"No Similar Games",Toast.LENGTH_LONG).show();
        }
        for (int i = 0; i < similarIds.size(); i++) {
            String sb = "http://thegamesdb.net/api/GetGame.php?id=" +
                    similarIds.get(i);
            new GetGameDetailsAsync(this).execute(sb);
            Log.d("SIMILAR: ", similarIds.get(i));
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
        if(gameDetails != null) {
            if(adapter == null){
                pb.setVisibility(View.INVISIBLE);
                gameDetailses = new ArrayList<>();
                gameDetailses.add(gameDetails);
                adapter = new ArrayAdapter<GameDetails>(this,android.R.layout.simple_list_item_1,gameDetailses);
                adapter.setNotifyOnChange(true);
                listView.setAdapter(adapter);
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Intent intent=new Intent(SimilarGamesActivity.this,GameDetailsActivity.class);
                        intent.putExtra("IFSIMILAR", false);
                        intent.putExtra("TITLE",adapter.getItem(position).getTitle());
                        intent.putExtra("IMAGEURL",adapter.getItem(position).getImageURL());
                        intent.putExtra("OVERVIEW", adapter.getItem(position).getOverview());
                        intent.putExtra("GENRE", adapter.getItem(position).getGenre());
                        intent.putExtra("PUBLISHER", adapter.getItem(position).getPublisher());
                        startActivity(intent);
                    }
                });
            }
            else{
                adapter.add(gameDetails);
            }
        }
        else{
            Toast.makeText(getApplicationContext(), "Failed to get XML file 502 Error:: Retrying to load", Toast.LENGTH_LONG).show();
        }
    }
}