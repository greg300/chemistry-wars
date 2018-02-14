package game;

/**
 * Created by gregorygiovannini on 6/15/16.
 */
public class NPC extends Character
{
    // ******************* INSTANCE VARIABLES *******************




    // ******************* CONSTRUCTOR(S) *******************

    public NPC(String name)
    {
        super(name, null, 0, 1, 0, 100, 100, 5, 0);
        setLevel(GameGUI.player.getLevel());
    }



    // ******************* METHODS *******************
    @Override
    public String characterStats()
    {
        return ("" + getName() + "\n" + "Level " + getLevel() + "\n\n");

    }


    // ******************* GETTERS/SETTERS *******************
}
