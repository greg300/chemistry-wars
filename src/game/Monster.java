package game;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gregorygiovannini on 5/16/16.
 */
public class Monster extends Character
{
    public enum MonsterNames
    {
        HAIRNAMI, HAIRQUAKE, HAIRSPLOSION, HAIRNOVA, HAIRPULSAR, HAIRQUASAR
    }

    // ******************* INSTANCE VARIABLES *******************
    private int monsterID;


    // ******************* CONSTRUCTOR(S) *******************
    public Monster()
    {

    }

    public Monster(int playerLevel)
    {
        super();
        monsterID = generateMonsterID(playerLevel);
        setName(generateMonsterName());
        setInventory(generateMonsterInventory(playerLevel));
        setCoins(generateMonsterCoins(playerLevel));
        setMaxHealth(generateMonsterMaxHealth(playerLevel));
        setCurrentHealth(getMaxHealth());
        setLevel(generateMonsterLevel(playerLevel));
        setExperience(generateMonsterExperience());
        setAttackStrength(generateMonsterAttackStrength(playerLevel));
    }




    // ******************* METHODS *******************
    @Override
    public String characterStats()
    {
        return (getName() + "\n" + "Level " + getLevel() + "\n\n" +
                "Health: " + getCurrentHealth() + " / " + getMaxHealth() + "\n" +
                "Strength: " + getAttackStrength());
    }

    public int generateMonsterID(int playerLevel) // Generates an ID less than or equal to the player's level
    {
        int id;
        id = fixZero((int) (Math.random() * playerLevel + 1));
        return id;
    }

    public String generateMonsterName() // Picks the corresponding ID of the monster's ID
    {
        MonsterNames[] names = MonsterNames.values();
        return names[monsterID -1].toString();
    }

    public int generateMonsterLevel(int playerLevel) // Generates a level for the monster less than or equal to the player's level
    {
        return monsterID;
    }

    public Inventory generateMonsterInventory(int playerLevel) // Generates an inventory with a maximum of one potion and one weapon/armor
    {
        List<Item> items = new ArrayList<>();
        int itemType = (int) (Math.random() * 5); // 0 to 4
        if(itemType == 0) // One potion
        {
            items.add(0, new Potion(this));
        }

        else if(itemType == 1) // One weapon
        {
            items.add(0, new Weapon(this));
        }

        else if(itemType == 2) // One armor
        {
            items.add(0, generateRandomArmorType());
        }

        else if(itemType == 3) // One potion and one armor
        {
            items.add(0, new Potion(this));
            items.add(1, generateRandomArmorType());
        }

        else if(itemType == 4) // One potion and one weapon
        {
            items.add(0, new Potion(this));
            items.add(1, new Weapon(this));
        }

        Inventory inventory = new Inventory(items, this);
        return inventory;
        //return new Inventory(List<Item> items, this);
    }

    public Item generateRandomArmorType()
    {
        int armorType = (int) (Math.random() * 5); // 0 to 4
        Item armor = null;
        if(armorType == 0) // Boots
        {
            armor = new Boots(this);
        }

        else if(armorType == 1) // Leggings
        {
            armor = new Leggings(this);
        }

        else if(armorType == 2) // Gauntlets
        {
            armor = new Gauntlets(this);
        }

        else if(armorType == 3) // Chestplate
        {
            armor = new Chestplate(this);
        }

        else if(armorType == 4) // Helmet
        {
            armor = new Helmet(this);
        }
        return armor;
    }

    public int generateMonsterCoins(int playerLevel) // Generates a coin amount that is less than or equal to 25 times the player's level
    {
        int coins;
        coins = fixZero((int) (Math.random() * playerLevel * 25));
        return coins;
    }

    public int generateMonsterExperience() // Generates an xp amount that is 50 times the monster's level
    {
        int xp;
        xp = 50 * getLevel();
        return xp;
    }

    public int generateMonsterMaxHealth(int playerLevel) // Generates a maximum health for the monster by adding the default 100 to a random number that is less than or equal to 10 times the player's level
    {
        int maxHealth;
        maxHealth = (int) (Math.random() * playerLevel * 10) + 100;
        return maxHealth;
    }

    public int generateMonsterAttackStrength(int playerLevel) // Generates a base strength for the monster that is less than or equal to twice the player's level
    {
        int strength;
        strength = fixZero((int) (Math.random() * playerLevel * 3) + 5);
        return strength;
    }

    public int generateRandomHitDamage() // Generates the power of a hit by adding the base strength to a random number less than or equal to the monster's level
    {
        int damage;
        damage = (int) (Math.random() * getLevel()) + getAttackStrength();
        return damage;
    }

    public int fixZero(int number)
    {
        if(number == 0)
            number++;
        return number;
    }




    // ******************* GETTERS/SETTERS *******************
    public int getMonsterID()
    {
        return monsterID;
    }

    public void setMonsterID(int monsterID)
    {
        this.monsterID = monsterID;
    }
}
