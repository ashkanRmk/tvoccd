package ir.talifrafea.rafea.Models.Film_models;

import java.util.List;


public class Film_main {
    private String main_name;
    private List<Film_Seasons> Seasons;

    public Film_main(String name){ this.main_name = name; }

    public void set_seasons (List<Film_Seasons> seasonsList){ Seasons = seasonsList; }

    public List<Film_Seasons> getSeasons () { return Seasons; }

    public String getMain_name() {return main_name;}
}
