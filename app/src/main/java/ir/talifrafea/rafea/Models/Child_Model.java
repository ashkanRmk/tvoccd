package ir.talifrafea.rafea.Models;

import java.util.List;

public class Child_Model {
    private String childTitle;
    private List<Item_Model> myItems;
    private List<Middle_CHild> middle_cHildList;

    public Child_Model(String childTitle){ this.childTitle = childTitle; }

    public void setMyItems(List<Item_Model> myItem){
        myItems = myItem;
    }
    public void setMiddle_cHildList(List<Middle_CHild> myItem){
        middle_cHildList = myItem;
    }

    public String getChildTitle(){
        return childTitle;
    }

    public List<Item_Model> getMyItems(){
        return myItems;
    }

    public List<Middle_CHild> getMiddle_cHildList(){
        return middle_cHildList;
    }
}
