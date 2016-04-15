package unt.restaurantapp.Classes;

/**
 * Created by Michael on 4/15/2016.
 */
public class Order
{
    private Menu_Item currentOrder[];
    private Table myTable;
    private double total;
    private int orderNumber;

    public Menu_Item[] getCurrentOrder()
    {
        return currentOrder;
    }

    public void setCurrentOrder(Menu_Item[] currentOrder)
    {
        this.currentOrder = currentOrder;
    }

    public Table getMyTable()
    {
        return myTable;
    }

    public void setMyTable(Table myTable)
    {
        this.myTable = myTable;
    }

    public double getTotal()
    {
        return total;
    }

    public void setTotal(double total)
    {
        this.total = total;
    }

    public int getOrderNumber()
    {
        return orderNumber;
    }

    public void setOrderNumber(int orderNumber)
    {
        this.orderNumber = orderNumber;
    }
}
