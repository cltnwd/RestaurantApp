package unt.restaurantapp;

/**
 * Created by coltonwood on 5/1/16.
 */
public class Table {

    String status;
    int id;

    public void setId(int id) {
        this.id = id;
    }
    public void setStatus(String status) {
        this.status = status;
    }

    public int getId() {
        return id;
    }
    public String getStatus() {
        return status;
    }
}
