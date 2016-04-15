package unt.restaurantapp.Classes;

/**
 * Created by Michael on 4/15/2016.
 */
public class Table
{
    private int tableNumber;
    private Customer atTable;
    private Waiter myWaiter;

    public int getTableNumber()
    {
        return tableNumber;
    }

    public void setTableNumber(int tableNumber)
    {
        this.tableNumber = tableNumber;
    }

    public Customer getAtTable()
    {
        return atTable;
    }

    public void setAtTable(Customer atTable)
    {
        this.atTable = atTable;
    }

    public Waiter getMyWaiter()
    {
        return myWaiter;
    }

    public void setMyWaiter(Waiter myWaiter)
    {
        this.myWaiter = myWaiter;
    }
}
