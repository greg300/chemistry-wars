package game;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by gregorygiovannini on 5/13/16.
 */
public class Inventory
{
    // ******************* INSTANCE VARIABLES *******************
    private List<Item> items = new ArrayList<>();
    private Character owner;
    private final int CARRY_CAPACITY = 15;



    // ******************* CONSTRUCTOR(S) *******************
    public Inventory(List<Item> items, Character owner)
    {
        this.items = items;
        this.owner = owner;

        /*for(Item i: items)
        {
            i.setOwner(owner);
        }*/

    }

    // ******************* METHODS *******************
    public void addItemToInventory(Item i) // Adds an item to the inventory
    {
        if(items.size() == CARRY_CAPACITY)
        {
            System.out.println(i.getName() + " NOT TRANSFERRED: INVENTORY FULL!");
        }
        else
        {
            i.setOwner(GameGUI.player);
            items.add(i);
            sortInventoryAlphabetically();
        }
    }

    public String listAllItems() // Returns a list of the names of all items in the inventory in String form
    {
        String itemList = "";
        int itemNumber = GameGUI.player.getInventory().getItems().size();

        for(Item i: items)
        {
            i.setItemNumber(itemNumber + 1);
            itemNumber++;
            itemList += i.getName() + "\n";
        }
        return itemList;
    }

    public void sortInventoryAlphabetically() // Sorts the inventory in alphabetical order by creating a new list of Items and manually adding them in the order their String names would be ordered
    {
        List<String> itemNames = new ArrayList<>();
        List<Item> sortedInventory = new ArrayList<>();

        for(Item i: items)
        {
            itemNames.add(i.getName());
        }
        Collections.sort(itemNames, new Comparator<String>()
        {
            @Override
            public int compare(String s1, String s2)
            {
                return s1.compareToIgnoreCase(s2);
            }
        });


        for(String s: itemNames)
        {
            for(Item i: items)
            {
                if(i.getName().equals(s))
                {
                    sortedInventory.add(i);
                    items.remove(i);
                    break;
                }
            }
        }

        for(int itemNumber = 0; itemNumber < sortedInventory.size(); itemNumber++)
        {
            sortedInventory.get(itemNumber).setItemNumber(itemNumber + 1);
        }

        items = sortedInventory;
    }




    // ******************* GETTERS/SETTERS *******************
    public List<Item> getItems()
    {
        return items;
    }

    public void setItems(List<Item> items)
    {
        this.items = items;
    }

    public Character getOwner()
    {
        return owner;
    }

    public void setOwner(Character owner)
    {
        this.owner = owner;
    }
}
