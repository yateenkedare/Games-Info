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
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import org.xml.sax.SAXException;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements GetGamesAsync.IData{
    ListView listView;
    ProgressBar PB;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = (ListView) findViewById(R.id.listViewGames);
        final EditText ET = (EditText) findViewById(R.id.searchBar);
        Button Search = (Button) findViewById(R.id.search);
        PB = (ProgressBar) findViewById(R.id.progressBar);
        PB.setVisibility(View.INVISIBLE);

        Search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PB.setVisibility(View.VISIBLE);
                StringBuilder sb = new StringBuilder("http://thegamesdb.net/api/GetGamesList.php?name=");
                sb.append(ET.getText().toString());
                Log.d("Game Search", sb.toString());
                new GetGamesAsync(MainActivity.this).execute(sb.toString());
            }
        });

    }

    @Override
    public void returnedValues(ArrayList<Game> val) throws IOException, XmlPullParserException, SAXException {
        displayGames(val);
    }

    public void displayGames(final ArrayList<Game> games) {
        PB.setVisibility(View.GONE);
        if(games != null) {
            CustomAdapter adapter = new CustomAdapter(this, games);

            listView.setAdapter(adapter);

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> parent, View view,
                                        int position, long id) {

                    Intent intent=new Intent(MainActivity.this,GameDetailsActivity.class);
                    StringBuilder sb=new StringBuilder();
                    sb.append("http://thegamesdb.net/api/GetGame.php?id=");
                    sb.append(games.get(position).getId());
                    Log.d("URLGAMEDETAILS: ", sb.toString());
                    intent.putExtra("GAME_DETAILS_ID",sb.toString());
                    startActivity(intent);
                }
            });
        }
        else{
            Toast.makeText(getApplicationContext(),"Unable to retrive the XML file::502 Error", Toast.LENGTH_LONG).show();
        }
    }
}
