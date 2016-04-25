package unt.restaurantapp.Classes;

/**
 * Created by Michael on 4/15/2016.
 */
public class Menu
{
    private Menu_Item menuList[];
    private Menu_Item mostPopular[];

    public Menu_Item[] getMenuList()
    {
        return menuList;
    }

    public Menu_Item[] getPopular()
    {
        return this.mostPopular;
    }
}
