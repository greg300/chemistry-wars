package game;

/**
 * Created by gregorygiovannini on 5/17/16.
 */
public class Weapon extends Item
{
    private enum WeaponMaterials
    {
        WOOD, IRON, DIAMOND
    }
    // ******************* INSTANCE VARIABLES *******************
    private int weaponID;
    private int baseDamage;



    // ******************* CONSTRUCTOR(S) *******************

    public Weapon(Character owner)
    {
        super("Sword", "Weapon", 0, owner);
        weaponID = generateWeaponID();
        baseDamage = generateBaseDamage();
        setName(fixCapitalization(generateWeaponMaterial()) + " Sword");
        setValue(generateWeaponCoinValue());
    }



    // ******************* METHODS *******************
    public void equipWeapon()
    {
        if(((Player)getOwner()).getEquippedWeapon() != null)
        {
            ((Player)getOwner()).getEquippedWeapon().setEquipped(false);
        }
        this.setEquipped(true);
        ((Player)getOwner()).setEquippedWeapon(this);
        ((Player)getOwner()).setRealAttackStrength(((Player)getOwner()).calculateAttackStrength());
    }

    public void unequipWeapon()
    {
        if(((Player)getOwner()).getEquippedWeapon().equals(this))
        {
            this.setEquipped(false);
            ((Player)getOwner()).setEquippedWeapon(null);
            ((Player)getOwner()).setRealAttackStrength(((Player)getOwner()).calculateAttackStrength());
        }

    }

    public String generateWeaponMaterial() // Picks the corresponding material of the weapon
    {
        WeaponMaterials[] materials = WeaponMaterials.values();
        return materials[weaponID -1].toString();
    }

    public int generateWeaponID() // Generates an ID for the weapon dependent on player level; this will be used for determining the damage and material
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

    public int generateBaseDamage() // Generates an amount of damage for the weapon dependent on weapon ID
    {
        return 3 * weaponID;
    }

    public int generateWeaponCoinValue() // Generates a coin value for the weapon based on its material
    {
        int value = 0;
        if(weaponID == 1)
        {
            value = 20;
        }
        else if(weaponID == 2)
        {
            value = 100;
        }
        else if(weaponID == 3)
        {
            value = 500;
        }
        return value;
    }


    // ******************* GETTERS/SETTERS *******************
    public int getBaseDamage()
    {
        return baseDamage;
    }

    public void setBaseDamage(int baseDamage)
    {
        this.baseDamage = baseDamage;
    }

    public int getWeaponID() {
        return weaponID;
    }

    public void setWeaponID(int weaponID) {
        this.weaponID = weaponID;
    }
}
