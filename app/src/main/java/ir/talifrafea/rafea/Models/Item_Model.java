package ir.talifrafea.rafea.Models;

import java.util.List;

public class Item_Model {
    private String Title;
    private String Url;
    private List<Season_Modal> mySeasons;


    public Item_Model(String title) {
        this.Title = title;
    }

    public Item_Model(String title, String url){
        this.Title = title;
        this.Url = url;
    }

    public String getTitle(){
        return Title;
    }

    public String getUrl(){
        return Url;
    }

    public void setTitle(String title){
        this.Title = title;
    }

    public void setUrl(String url){
        this.Url = url;
    }

    public void setMySeasons(List<Season_Modal> mySeason){
        mySeasons = mySeason;
    }
    public List<Season_Modal> getMySeasons(){
        return mySeasons;
    }

}
