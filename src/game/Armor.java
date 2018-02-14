package game;

import java.lang.*;

/**
 * Created by gregorygiovannini on 5/17/16.
 */
public abstract class Armor extends Item
{
    private enum ArmorMaterials
    {
        LEATHER, CHAINMAIL, PLATEMAIL
    }

    // ******************* INSTANCE VARIABLES *******************
    private int baseRating;
    private int armorID;
    private String material;



    // ******************* CONSTRUCTOR(S) *******************

    public Armor(String name, String type, Character owner)
    {
        super(name, type, 0, owner);
        armorID = generateArmorID();
        baseRating = generateBaseRating();
        material = generateArmorMaterial();
        setName(fixCapitalization(material) + " " + name);
        setValue(generateArmorCoinValue());
    }




    // ******************* METHODS *******************
    public abstract void equipArmor();

    public abstract void unequipArmor();

    public String generateArmorMaterial() // Picks the corresponding ID of the armor ID
    {
        ArmorMaterials[] names = ArmorMaterials.values();
        return names[getArmorID() -1].toString();
    }

    public int generateArmorID() // Generates an ID for the armor piece dependent on player level; this will be used for determining the armor material
    {
        int id = 0;
        if(MainMenuGUI.player.getLevel() < 3)
        {
            id = 1;
        }
        else if(MainMenuGUI.player.getLevel() < 5)
        {
            id = 2;
        }
        else if(MainMenuGUI.player.getLevel() >= 5)
        {
            id = 3;
        }

        return id;
    }

    public int generateBaseRating() // Generates the base rating for the armor based on its material
    {
        return armorID;
    }

    public int generateArmorCoinValue() // Generates a coin value for the armor based on its material
    {
        int value = 0;
        if(armorID == 1)
        {
            value = 20;
        }
        else if(armorID == 2)
        {
            value = 100;
        }
        else if(armorID == 3)
        {
            value = 500;
        }
        return value;
    }




    // ******************* GETTERS/SETTERS *******************
    public int getBaseRating()
    {
        return baseRating;
    }

    public void setBaseRating(int baseRating)
    {
        this.baseRating = baseRating;
    }

    public int getArmorID()
    {
        return armorID;
    }

    public void setArmorID(int armorID)
    {
        this.armorID = armorID;
    }

}
