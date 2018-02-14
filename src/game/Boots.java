package game;

/**
 * Created by gregorygiovannini on 6/1/16.
 */
public class Boots extends Armor
{

    // ******************* INSTANCE VARIABLES *******************




    // ******************* CONSTRUCTOR(S) *******************

    public Boots(Character owner)
    {
        super("Boots", "Armor", owner);

    }



    // ******************* METHODS *******************
    @Override
    public void equipArmor()
    {
        if(((Player)getOwner()).getEquippedBoots() != null)
        {
            ((Player)getOwner()).getEquippedBoots().setEquipped(false);
        }
        this.setEquipped(true);
        ((Player)getOwner()).setEquippedBoots(this);
        getOwner().setArmorRating(((Player) getOwner()).calculateArmorRating());
    }

    @Override
    public void unequipArmor()
    {
        if(((Player)getOwner()).getEquippedChestplate().equals(this))
        {
            this.setEquipped(false);
            ((Player)getOwner()).setEquippedBoots(null);
            getOwner().setArmorRating(((Player) getOwner()).calculateArmorRating());
        }
    }

    // ******************* GETTERS/SETTERS *******************
}
