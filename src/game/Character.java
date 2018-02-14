package game;

/**
 * Created by gregorygiovannini on 5/13/16.
 */
public abstract class Character
{
    // ******************* INSTANCE VARIABLES *******************
    private String name;
    private Inventory inventory;
    private int coins;
    private int level;
    private int experience;
    private int currentHealth;
    private int maxHealth;
    private int attackStrength;
    private int armorRating;



    // ******************* CONSTRUCTOR(S) *******************
    public Character()
    {

    }

    public Character(String name, Inventory inventory, int coins, int level, int experience, int currentHealth, int maxHealth, int attackStrength, int armorRating)
    {
        this.name = name;
        this.inventory = inventory;
        this.coins = coins;
        this.level = level;
        this.experience = experience;
        this.currentHealth = currentHealth;
        this.maxHealth = maxHealth;
        this.attackStrength = attackStrength;
        this.armorRating = armorRating;
    }



    // ******************* METHODS *******************
    public abstract String characterStats();




    // ******************* GETTERS/SETTERS *******************
    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public Inventory getInventory()
    {
        return inventory;
    }

    public void setInventory(Inventory inventory)
    {
        this.inventory = inventory;
    }

    public int getCoins()
    {
        return coins;
    }

    public void setCoins(int coins)
    {
        this.coins = coins;
    }

    public int getLevel()
    {
        return level;
    }

    public void setLevel(int level)
    {
        this.level = level;
    }

    public int getExperience()
    {
        return experience;
    }

    public void setExperience(int experience)
    {
        this.experience = experience;
    }

    public int getCurrentHealth()
    {
        return currentHealth;
    }

    public void setCurrentHealth(int currentHealth)
    {
        this.currentHealth = currentHealth;
    }

    public int getMaxHealth()
    {
        return maxHealth;
    }

    public void setMaxHealth(int maxHealth)
    {
        this.maxHealth = maxHealth;
    }

    public int getAttackStrength()
    {
        return attackStrength;
    }

    public void setAttackStrength(int attackStrength)
    {
        this.attackStrength = attackStrength;
    }

    public int getArmorRating()
    {
        return armorRating;
    }

    public void setArmorRating(int armorRating)
    {
        this.armorRating = armorRating;
    }
}
