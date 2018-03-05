package ir.talifrafea.rafea.Models;

public class Season_Modal {
    private String Title;
    private String Url;

    public Season_Modal(String title, String url){
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
}
