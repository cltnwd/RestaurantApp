package unt.restaurantapp.Classes;

/**
 * Created by Michael on 4/15/2016.
 */
public class Manager extends User
{
    private Table tablesInUse[];

    public Table[] getTablesInUse()
    {
        return tablesInUse;
    }

    public void setTablesInUse(Table[] tablesInUse)
    {
        this.tablesInUse = tablesInUse;
    }
}
