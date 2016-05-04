package unt.restaurantapp;

import android.graphics.Bitmap;

/**
 * Created by coltonwood on 4/16/16.
 */
public class MenuItem {
    public String name;
    public String description;
    public Bitmap photo;
    public double price;
    public int calories;
    int itemid;
    boolean isAvailable;

    String getName() {
        return name;
    }
    String getDescription() {
        return description;
    }
    Bitmap getPhoto() {
        return photo;
    }
    int getItemid() {
        return itemid;
    }
    double getPrice() {
        return price;
    }
    int getCalories() {
        return calories;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    void setName(String s) {
        name = s;
    }
    void setDescription(String s) {
        description = s;
    }
    void setPhoto(Bitmap b) {
        photo = b;
    }
    void setPrice(double d) {
        price = d;
    }
    void setCalories(int i) {
        calories = i;
    }
    void setItemid(int i) {
        itemid = i;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }
}
