package unt.restaurantapp.Classes;

/**
 * Created by Michael on 4/15/2016.
 */
public class Waiter extends User
{
    private Table myTables;

    public Table getMyTables()
    {
        return myTables;
    }

    public void setMyTables(Table myTables)
    {
        this.myTables = myTables;
    }
}
