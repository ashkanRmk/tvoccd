package ir.talifrafea.rafea.Models;


import java.util.List;

public class Middle_CHild {

    private String childTitle;
    private List<Item_Model> myItems;

    public Middle_CHild(String childTitle){ this.childTitle = childTitle; }

    public void setMyItems(List<Item_Model> myItem){
        myItems = myItem;
    }

    public String getChildTitle(){
        return childTitle;
    }

    public List<Item_Model> getMyItems(){
        return myItems;
    }
}
