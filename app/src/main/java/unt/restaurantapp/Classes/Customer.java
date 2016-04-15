package unt.restaurantapp.Classes;

/**
 * Created by Michael on 4/15/2016.
 */
public class Customer extends User
{
    private Order myOrder;
    private int myRewardPoints;
    private int myCoupons;

    public Order getMyOrder()
    {
        return myOrder;
    }

    public void setMyOrder(Order myOrder)
    {
        this.myOrder = myOrder;
    }

    public int getMyRewardPoints()
    {
        return myRewardPoints;
    }

    public void addPoint()
    {
        this.myRewardPoints = myRewardPoints + 1;
    }

    public int getMyCoupons()
    {
        return myCoupons;
    }

    public void setMyCoupons(int myCoupons)
    {
        this.myCoupons = myCoupons;
    }
}
