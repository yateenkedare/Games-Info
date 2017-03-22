package com.example.yatee.hw05;
/*
InClass 06
GameDetailsActivity
Yateen Kedare
 */
import android.content.Context;
import android.os.AsyncTask;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import org.xml.sax.SAXException;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by yatee on 2/20/2017.
 */

public class CustomAdapter extends ArrayAdapter {
    private Context context;

    private ArrayList<Game> gameArrayList;
    public CustomAdapter(Context context, ArrayList<Game> objects) {
        super(context, R.layout.my_list, objects);
        this.context = context;

        this.gameArrayList = objects;
    }

    @NonNull
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {


        ViewHolder holder;
        if(convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.my_list, parent, false);
            holder = new ViewHolder();
            holder.textView = (TextView) convertView.findViewById(R.id.textView3);
            holder.imageView = (ImageView) convertView.findViewById(R.id.imageView2);
            StringBuilder sb=new StringBuilder();
            sb.append("http://thegamesdb.net/api/GetGame.php?id=");
            sb.append(gameArrayList.get(position).getId());
            holder.imageURL = sb.toString();
            holder.position = position;
            convertView.setTag(holder);
        }

        holder = (ViewHolder) convertView.getTag();
        TextView title = holder.textView;
        final ImageView imageView = holder.imageView;

        title.setText(gameArrayList.get(position).getTitle()+" "+gameArrayList.get(position).getReleaseDate()+" "+gameArrayList.get(position).getPlatform());

        new AsyncTask<ViewHolder, Void, GameDetails>() {
            private ViewHolder v;

            @Override
            protected GameDetails doInBackground(ViewHolder... params) {
                v = params[0];
                URL url= null;
                try {
                    url = new URL(v.imageURL);
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
            protected void onPostExecute(GameDetails result) {
                super.onPostExecute(result);
                if (v.position == position && result != null) {
                    Picasso.with(context)
                            .load(result.getImageURL())
                            .into(imageView);
                }
            }
        }.execute(holder);


        return convertView;
    }


    static class ViewHolder{
        TextView textView;
        ImageView imageView;
        String imageURL;
        int position;
    }
}
