package game;

/**
 * Created by gregorygiovannini on 6/1/16.
 */
public class Leggings extends Armor
{

    // ******************* INSTANCE VARIABLES *******************




    // ******************* CONSTRUCTOR(S) *******************

    public Leggings(Character owner)
    {
        super("Leggings", "Armor", owner);
    }



    // ******************* METHODS *******************
    @Override
    public void equipArmor()
    {
        if(((Player)getOwner()).getEquippedLeggings() != null)
        {
            ((Player)getOwner()).getEquippedLeggings().setEquipped(false);
        }
        this.setEquipped(true);
        ((Player)getOwner()).setEquippedLeggings(this);
        getOwner().setArmorRating(((Player) getOwner()).calculateArmorRating());
    }

    @Override
    public void unequipArmor()
    {
        if(((Player)getOwner()).getEquippedLeggings().equals(this))
        {
            this.setEquipped(false);
            ((Player)getOwner()).setEquippedLeggings(null);
            getOwner().setArmorRating(((Player) getOwner()).calculateArmorRating());
        }

    }



    // ******************* GETTERS/SETTERS *******************
}
