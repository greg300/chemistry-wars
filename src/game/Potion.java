package game;

/**
 * Created by gregorygiovannini on 5/17/16.
 */
public class Potion extends Item
{
    private enum PotionSizes
    {
        SMALL, MEDIUM, LARGE
    }
    // ******************* INSTANCE VARIABLES *******************
    private int potency;
    private int healAmount;



    // ******************* CONSTRUCTOR(S) *******************
    public Potion(Character owner)
    {
        super("", "Health Potion", 0, owner);
        potency = generatePotionPotency();
        healAmount = generateHealAmount();
        setName(fixCapitalization(generatePotionSize()) + " Health Potion");
        setValue(generatePotionCoinValue());
    }



    // ******************* METHODS *******************
    public void drinkPotion()
    {
        ((Player)getOwner()).setCurrentHealth(((Player)getOwner()).getCurrentHealth() + healAmount);
        if(getOwner().getCurrentHealth() > getOwner().getMaxHealth())
        {
            getOwner().setCurrentHealth(getOwner().getMaxHealth());
        }
        //TODO make sure this works
        getOwner().getInventory().getItems().remove(this);
    }

    public String generatePotionSize() // Picks the corresponding size of the potion potency
    {
        PotionSizes[] sizes = PotionSizes.values();
        return sizes[getPotency() -1].toString();
    }

    public int generatePotionPotency() // Generates a potency for the potion dependent on player level; this will be used for determining the amount healed
    {
        int potency = 0;
        if(MainMenuGUI.player.getLevel() < 3)
        {
            potency = 1;
        }
        else if(MainMenuGUI.player.getLevel() < 5)
        {
            potency = 2;
        }
        else if(MainMenuGUI.player.getLevel() >= 5)
        {
            potency = 3;
        }

        return potency;
    }

    public int generateHealAmount() // Generates an amount of HP to be restored for the potion dependent on potion potency
    {
        return 50 * potency;
    }

    public int generatePotionCoinValue() // Generates a coin value for the potion based on its potency
    {
        int value = 0;
        if(potency == 1)
        {
            value = 10;
        }
        else if(potency == 2)
        {
            value = 50;
        }
        else if(potency == 3)
        {
            value = 100;
        }
        return value;
    }


    // ******************* GETTERS/SETTERS *******************
    public int getPotency()
    {
        return potency;
    }

    public void setPotency(int potency)
    {
        this.potency = potency;
    }

    public int getHealAmount() {
        return healAmount;
    }

    public void setHealAmount(int healAmount) {
        this.healAmount = healAmount;
    }
}
