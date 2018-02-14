package game;

import java.lang.*;
import java.lang.Character;

/**
 * Created by gregorygiovannini on 5/13/16.
 */
public abstract class Item
{
    // ******************* INSTANCE VARIABLES *******************
    private String name;
    private String type;
    private int value;
    private String description;
    private game.Character owner;
    private int itemNumber;
    private boolean isEquipped;



    // ******************* CONSTRUCTOR(S) *******************
    public Item(String name, String type, int value, game.Character owner)
    {
        this.name = name;
        this.type = type;
        this.value = value;
        this.owner = owner;
        itemNumber = 1;
        //owner.getInventory().addItemToInventory(this);
        //owner.getInventory().setOwner(owner);
    }



    // ******************* METHODS *******************
    public String fixCapitalization(String name) // Fixes the capitalization of an item name from enum default (all caps) to first letter only
    {
        String newName = "";
        char[] letters = name.toLowerCase().toCharArray();
        for(int count = 1; count < letters.length; count++) // Skip first letter
        {
            newName += letters[count];
        }
        Character firstLetter = letters[0];
        return firstLetter.toUpperCase(firstLetter) + newName;
    }

    public String itemStats() // Returns stats of an item in list form
    {
        if(isEquipped)
        {
            return (itemNumber + "*  " + name + "\n" + "   " + type + "\n" + "   " + value + " Coins");
        }
        else return(name + "\n" + "   " + type + "\n" + "   " + value + " Coins");

    }

    public String itemStatsOnOneLine() // Returns stats of an item on one line, used in invetory GUI
    {
        if(isEquipped)
        {
            return (itemNumber + "*  " + name + "         " + type + "         " + value);
        }
        else return(itemNumber + "   " + name + "         " + type + "         " + value);
    }

    public String makeItemNumberString() // Makes the item number a String so that it can be compared in the inventory table
    {
        String number = "";
        number += itemNumber;
        return number;
    }



    // ******************* GETTERS/SETTERS *******************
    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getType()
    {
        return type;
    }

    public void setType(String type)
    {
        this.type = type;
    }

    public int getValue()
    {
        return value;
    }

    public void setValue(int value)
    {
        this.value = value;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public game.Character getOwner() {
        return owner;
    }

    public void setOwner(game.Character owner) {
        this.owner = owner;
    }

    public int getItemNumber() {
        return itemNumber;
    }

    public void setItemNumber(int itemNumber) {
        this.itemNumber = itemNumber;
    }

    public boolean isEquipped() {
        return isEquipped;
    }

    public void setEquipped(boolean equipped) {
        isEquipped = equipped;
    }
}
