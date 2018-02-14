package game;

/**
 * Created by gregorygiovannini on 6/1/16.
 */
public class Helmet extends Armor
{

    // ******************* INSTANCE VARIABLES *******************




    // ******************* CONSTRUCTOR(S) *******************

    public Helmet(Character owner)
    {
        super("Helmet", "Armor", owner);
    }



    // ******************* METHODS *******************

    @Override
    public void equipArmor()
    {
        if(((Player)getOwner()).getEquippedHelmet() != null)
        {
            ((Player)getOwner()).getEquippedHelmet().setEquipped(false);
        }
        this.setEquipped(true);
        ((Player)getOwner()).setEquippedHelmet(this);
        getOwner().setArmorRating(((Player) getOwner()).calculateArmorRating());
    }

    @Override
    public void unequipArmor()
    {
        if(((Player)getOwner()).getEquippedHelmet().equals(this))
        {
            this.setEquipped(false);
            ((Player)getOwner()).setEquippedHelmet(null);
            getOwner().setArmorRating(((Player) getOwner()).calculateArmorRating());
        }
    }



    // ******************* GETTERS/SETTERS *******************

}
