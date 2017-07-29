package ir.talifrafea.rafea.Models;

import java.util.List;

public class Parent_Model {
    private String parentTitle;
    private List<Child_Model> myChilds;

    public Parent_Model(String parentTitle){
        this.parentTitle = parentTitle;
    }

/*
    public void setMyChild(String childTitle){
        Child_Model myChild = new Child_Model(childTitle);
        myChilds.add(myChild);
    }
*/

    public void setMyChilds(List<Child_Model> myChild) {
        myChilds = myChild;
    }

    public String getParentTitle() {
        return parentTitle;
    }

    public List<Child_Model> getMyChilds() {
        return myChilds;
    }
}
