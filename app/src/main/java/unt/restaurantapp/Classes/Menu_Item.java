package unt.restaurantapp.Classes;

/**
 * Created by Michael on 4/15/2016.
 */
public class Menu_Item
{

    private String name;
    private String description;
    private double cost;
    private int numberSold;
    private Ingredient ingredientsList[];

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public double getCost()
    {
        return cost;
    }

    public void setCost(double cost)
    {
        this.cost = cost;
    }

    public int getNumberSold()
    {
        return numberSold;
    }

    public void setNumberSold(int numberSold)
    {
        this.numberSold = numberSold;
    }

    public Ingredient[] getIngredientsList()
    {
        return ingredientsList;
    }

    public void setIngredientsList(Ingredient[] ingredientsList)
    {
        this.ingredientsList = ingredientsList;
    }
}
