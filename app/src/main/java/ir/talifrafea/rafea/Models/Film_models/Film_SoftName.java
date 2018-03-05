package ir.talifrafea.rafea.Models.Film_models;


public class Film_SoftName {
    private String Title;
    private String Url;

    public Film_SoftName(String title, String url){
        this.Title = title;
        this.Url = url;
    }

    public String getTitle() {return Title;}

    public String getUrl() {return Url;}

    public void setTitle(String title) { this.Title = title;}

    public void setUrl(String url) {this.Url = url;}
}
