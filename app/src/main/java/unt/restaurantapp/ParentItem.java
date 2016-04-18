package unt.restaurantapp;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by coltonwood on 4/16/16.
 */
public class ParentItem {

    private List<MenuItem> menuItemList;

    public ParentItem() {
        menuItemList = new ArrayList<MenuItem>();
    }

    public List<MenuItem> getChildItemList() {
        return menuItemList;
    }
}