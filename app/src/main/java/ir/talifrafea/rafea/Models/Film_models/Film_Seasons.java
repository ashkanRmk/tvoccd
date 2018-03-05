package ir.talifrafea.rafea.Models.Film_models;

import java.util.List;

/**
 * Created by smn on 12/23/17.
 */

public class Film_Seasons {

    private String season_name;
    private List<Film_SoftName> softNames;

    public Film_Seasons(String name){ this.season_name = name; }

    public void setSoftNames (List<Film_SoftName> softNameList){ softNames = softNameList; }

    public List<Film_SoftName> getSoftNames () { return softNames; }

    public String getSeason_name() {return season_name;}
}
