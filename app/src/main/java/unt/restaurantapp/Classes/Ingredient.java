package unt.restaurantapp.Classes;

/**
 * Created by Michael on 4/15/2016.
 */
public class Ingredient
{
    String name;
    int numInStock;
    boolean isInStock;

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public int getNumInStock()
    {
        return numInStock;
    }

    public void setNumInStock(int numInStock)
    {
        this.numInStock = numInStock;
    }

    public boolean getIsInStock()
    {
        checkInStock();
        return isInStock;
    }

    public void checkInStock()
    {
        if(numInStock > 0)
            isInStock = true;
        else
            isInStock = false;
    }
}
