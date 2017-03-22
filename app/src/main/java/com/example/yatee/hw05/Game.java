package com.example.yatee.hw05;
/*
InClass 06
GameDetailsActivity
Yateen Kedare
 */
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Created by yatee on 2/19/2017.
 */

public class Game {
    private String id;
    private String title;
    private String releaseDate;
    private Date releaseDate2;
    private String platform;

    public String getReleaseDate() {
        return releaseDate;
    }


    public void setReleaseDate(String releaseDate) {

        SimpleDateFormat fmt=new SimpleDateFormat("MM/dd/yyyy");
        try {
            if(!releaseDate.equals("")){
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




    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return "Game{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", releaseDate='" + releaseDate + '\'' +
                ", platform='" + platform + '\'' +
                '}';
    }

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }
}
