package game;

/**
 * Created by gregorygiovannini on 6/1/16.
 */
public class Chestplate extends Armor
{

    // ******************* INSTANCE VARIABLES *******************




    // ******************* CONSTRUCTOR(S) *******************

    public Chestplate(Character owner)
    {
        super("Chestplate", "Armor", owner);
    }



    // ******************* METHODS *******************

    @Override
    public void equipArmor()
    {
        if(((Player)getOwner()).getEquippedChestplate() != null)
        {
            ((Player)getOwner()).getEquippedChestplate().setEquipped(false);
        }
        this.setEquipped(true);
        ((Player)getOwner()).setEquippedChestplate(this);
        getOwner().setArmorRating(((Player) getOwner()).calculateArmorRating());
    }

    @Override
    public void unequipArmor()
    {
        if(((Player)getOwner()).getEquippedChestplate().equals(this))
        {
            this.setEquipped(false);
            ((Player)getOwner()).setEquippedChestplate(null);
            getOwner().setArmorRating(((Player) getOwner()).calculateArmorRating());
        }

    }



    // ******************* GETTERS/SETTERS *******************

}
