package com.example.yatee.hw05;
/*
InClass 06
GameDetailsActivity
Yateen Kedare
 */
import android.os.AsyncTask;
import android.util.Log;

import org.xml.sax.SAXException;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by yatee on 2/19/2017.
 */

public class GetGameDetailsAsync extends AsyncTask<String, Void, GameDetails>{
    IDataDetails activity;

    GetGameDetailsAsync (GetGameDetailsAsync.IDataDetails activity){
        this.activity=activity;
    }



    @Override
    protected GameDetails doInBackground(String... params) {
        URL url= null;
        try {
            url = new URL(params[0]);
            HttpURLConnection con= (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            Log.d("Stream", con.getInputStream().toString());
            GameDetailsParser.GameDetailsSAXParser.parseGames(con.getInputStream());
            return GameDetailsParser.GameDetailsSAXParser.getGame();

        } catch (IOException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void onPostExecute(GameDetails gameDetails) {
        super.onPostExecute(gameDetails);
        activity.returnedDetailsValues(gameDetails);
    }


    public static interface IDataDetails{
        public void returnedDetailsValues(GameDetails gameDetails);
    }
}
