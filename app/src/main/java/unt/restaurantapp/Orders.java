package unt.restaurantapp;

/**
 * Created by coltonwood on 5/2/16.
 */
public class Orders {

    String orderstring, status;
    int tableid, realid;

    public String getStatus() {
        return status;
    }

    public int getTableid() {
        return tableid;
    }

    public String getOrderstring() {
        return orderstring;
    }

    public int getRealid() {
        return realid;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setOrderstring(String orderstring) {
        this.orderstring = orderstring;
    }

    public void setTableid(int tableid) {
        this.tableid = tableid;
    }

    public void setRealid(int realid) {
        this.realid = realid;
    }
}

