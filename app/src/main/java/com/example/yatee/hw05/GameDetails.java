package com.example.yatee.hw05;
/*
InClass 06
GameDetailsActivity
Yateen Kedare
 */
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Created by yatee on 2/19/2017.
 */

public class GameDetails {
    private String title;
    private String genre;
    private String publisher;
    private String overview;
    private String imageURL;
    private Date releaseDate2;
    private String releaseDate;
    private String platform;
    private String videoURL;
    private ArrayList<String> similarGames;

    public ArrayList<String> getSimilarGames() {
        return similarGames;
    }

    public void setSimilarGames(String similarGame) {
        this.similarGames.add(similarGame);
    }

    public GameDetails() {
        similarGames = new ArrayList<>();
    }

    public String getBaseURL() {
        return baseURL;
    }

    public void setBaseURL(String baseURL) {
        this.baseURL = baseURL;
    }

    private String baseURL;
    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public String getReleaseDate() {
        return releaseDate;
    }


    public void setReleaseDate(String releaseDate) {
        SimpleDateFormat fmt=new SimpleDateFormat("MM/dd/yyyy");
        try {
            if(releaseDate.equals("")!=true){
                Date date=fmt.parse(releaseDate);
                if(date!=null){
                    Calendar myCal = new GregorianCalendar();
                    myCal.setTime(date);
                    releaseDate2=date;
                }
            }
            else
                releaseDate2=null;

        } catch (ParseException e) {
            e.printStackTrace();
        }

        this.releaseDate = releaseDate;

    }

    public String getYear(){
        if(releaseDate2!=null ){
            Calendar myCal = new GregorianCalendar();
            myCal.setTime(releaseDate2);
            return String.valueOf(myCal.get(Calendar.YEAR));
        }
        else
            return "No Date Found";
    }


    public String getVideoURL() {
        return videoURL;
    }

    public void setVideoURL(String videoURL) {
        this.videoURL = videoURL;
    }


    @Override
    public String toString() {
        return title+" Released in:" +releaseDate+ " Platform: " + platform;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getGenre() {
        return "Genre: "+genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getPublisher() {
        return "Publisher: "+publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getOverview() {
        return "Overview:\n"+overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getImageURL() {
        return getBaseURL()+imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }
}
