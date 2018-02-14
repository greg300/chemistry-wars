package game;

/**
 * Created by gregorygiovannini on 6/1/16.
 */
public class Gauntlets extends Armor
{
    // ******************* INSTANCE VARIABLES *******************




    // ******************* CONSTRUCTOR(S) *******************

    public Gauntlets(Character owner)
    {
        super("Gauntlets", "Armor", owner);
    }



    // ******************* METHODS *******************
    @Override
    public void equipArmor()
    {
        if(((Player)getOwner()).getEquippedGauntlets() != null)
        {
            ((Player)getOwner()).getEquippedGauntlets().setEquipped(false);
        }
        this.setEquipped(true);
        ((Player)getOwner()).setEquippedGauntlets(this);
        getOwner().setArmorRating(((Player) getOwner()).calculateArmorRating());
    }

    @Override
    public void unequipArmor()
    {
        if(((Player)getOwner()).getEquippedGauntlets().equals(this))
        {
            this.setEquipped(false);
            ((Player)getOwner()).setEquippedGauntlets(null);
            getOwner().setArmorRating(((Player) getOwner()).calculateArmorRating());
        }

    }



    // ******************* GETTERS/SETTERS *******************
}
