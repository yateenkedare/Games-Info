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
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by yatee on 2/19/2017.
 */

public class GetGamesAsync extends AsyncTask<String,Void,ArrayList<Game>> {
    IData activity;
    GetGamesAsync(IData activity){
        this.activity=activity;
    }


    @Override
    protected ArrayList<Game> doInBackground(String... params) {
        URL url= null;
        try {
            url = new URL(params[0]);
            HttpURLConnection con= (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            Log.d("Stream", con.getInputStream().toString());
            GameVersionParser.GameVersionsSAXParser.parseGamesList(con.getInputStream());
            return GameVersionParser.GameVersionsSAXParser.getGames();

        } catch (IOException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void onPostExecute(ArrayList<Game> games) {
        super.onPostExecute(games);
        try {
            activity.returnedValues(games);
        } catch (IOException | XmlPullParserException | SAXException e) {
            e.printStackTrace();
        }

    }


    public static interface IData{
        public void returnedValues(ArrayList<Game> val) throws IOException, XmlPullParserException, SAXException;
    }
}
