package com.saproductions.magicpin_hls.model;

/**
 * Created by shivam on 23/2/19.
 */

public class Feeds {


    String username;
    String videoLink;
    String date;
    String feeddata;
    static int count = 1;


    public Feeds(String videoLink) {
        count++;
        setDate(getRandomDate());
        setFeeddata(generateText(count));
        setUsername(generateName(count));
        this.videoLink = videoLink;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getVideoLink() {
        return videoLink;
    }

    public void setVideoLink(String videoLink) {
        this.videoLink = videoLink;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getFeeddata() {
        return feeddata;
    }

    public void setFeeddata(String feeddata) {
        this.feeddata = feeddata;
    }


    private String getRandomDate(){
        int dd = (count + 1);
        int mm = (int) (Math.floor(Math.random())%12 + 1);

        return ((dd<9)?("0"+dd):(dd)) + "-" + ((mm<9)?("0"+mm):(mm)) + "-" + "18";
    }

    private String generateName(int count){

        if(count%3==0 && count%2==0)
            return "ANIL DHAWAN";
        else if(count%2==0)
            return "NEHA SUNEJA";

        return "MAYANK GUPTA";

    }

    private String generateText(int count){

        if(count%3==0 && count%2==0)
            return "Magicpin help me to grow my business at a very large scale.";
        else if(count%3==0)
            return " Indeed, All thanks to magicpin!!";

        return "Yo !! Great Work. Magicpin + me = <3";

    }


}
