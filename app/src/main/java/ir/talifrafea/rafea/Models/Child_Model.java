package ir.talifrafea.rafea.Models;

import java.util.List;

public class Child_Model {
    private String childTitle;
    private List<Item_Model> myItems;

    public Child_Model(String childTitle){
        this.childTitle = childTitle;
    }
/*
    public void setMyItem(String title, String url) {
        Item_Model myItem = new Item_Model(title, url);
        this.myItems.add(myItem);
    }
    */
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
