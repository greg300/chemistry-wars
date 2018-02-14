package game;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gregorygiovannini on 5/13/16.
 */
public class Player extends Character
{
    // ******************* INSTANCE VARIABLES *******************
    private final int LEVEL_ONE_CAP = 100; // At this amount of xp, the next level is reached (i.e. at 100 xp, player is level 2)
    private final int LEVEL_TWO_CAP = 500;
    private final int LEVEL_THREE_CAP = 1000;
    private final int LEVEL_FOUR_CAP = 2000;
    private final int LEVEL_FIVE_CAP = 5000;

    private final String[] STORY_LINES = new String[20];

    private int monstersKilled;
    private int realAttackStrength; // Base strength + weapon's base damage
    private Weapon equippedWeapon;
    private Helmet equippedHelmet;
    private Chestplate equippedChestplate;
    private Leggings equippedLeggings;
    private Gauntlets equippedGauntlets;
    private Boots equippedBoots;



    // ******************* CONSTRUCTOR(S) *******************
    public Player(String name) // Creates a new level one player
    {
        super(name, null, 0, 1, 0, 100, 100, 5, 0);
        setInventory(new Inventory(new ArrayList<>(), this));
        monstersKilled = 0;
        realAttackStrength = calculateAttackStrength();
        initializeStoryLines();
    }



    // ******************* METHODS *******************
    @Override
    public String characterStats() // Returns player stats in String form for GameGUI
    {
        return ("" + getName() + "\n" + "Level " + getLevel() + "\n\n" +
                "Health: " + getCurrentHealth() + " / " + getMaxHealth() + "\n" +
                "Armor: " + getArmorRating() + "\n" +
                "Damage: " + calculateAttackStrength() + "\n" +
                "Coins: " + getCoins() + "\n" +
                "Experience: " + getExperience());
    }

    public String detailedPlayerStats() // Returns player stats in String form for InventoryGUI
    {
        return ("Name: " + getName() + "\n" +
                "Level: " + getLevel() + "\n" +
                "Health: " + getCurrentHealth() + " / " + getMaxHealth() + "\n" +
                "Armor: " + getArmorRating() + "\n" +
                "Base Strength: " + getAttackStrength() + "\n" +
                "Total Damage: " + calculateAttackStrength() + "\n" +
                "Coins: " + getCoins() + "\n" +
                "Experience: " + getExperience() + "\n" +
                "Monsters Killed: " + monstersKilled);
    }

    public void transferMonsterDataToPlayer(Monster m) // Called after monster is killed; gives player coins, xp, and items
    {
        monstersKilled++;
        setCoins(getCoins() + m.getCoins());
        setExperience(getExperience() + m.getExperience());
        transferMonsterInventoryToPlayer(m);

        checkForLevelUp();

    }

    public void transferMonsterInventoryToPlayer(Monster m) // Transfers monster inventory to player item by item
    {
        Inventory monsterInventory = m.getInventory();
        if(monsterInventory != null)
        {
            List<Item> monsterItems = monsterInventory.getItems();

            if(monsterItems != null)
            {
                for(Item i: monsterItems)
                {
                    getInventory().addItemToInventory(i);
                }
            }
        }


    }

    public void checkForLevelUp() // Compares player experience with constants needed to level up
    {
        if(getLevel() == 1)
        {
            if(getExperience() >= LEVEL_ONE_CAP)
            {
                levelUp();
            }
        }
        else if(getLevel() == 2)
        {
            if(getExperience() >= LEVEL_TWO_CAP)
            {
                levelUp();
            }
        }
        else if(getLevel() == 3)
        {
            if(getExperience() >= LEVEL_THREE_CAP)
            {
                levelUp();
            }
        }
        else if(getLevel() == 4)
        {
            if(getExperience() >= LEVEL_FOUR_CAP)
            {
                levelUp();
            }
        }
        else if(getLevel() == 5)
        {
            if(getExperience() >= LEVEL_FIVE_CAP)
            {
                levelUp();
            }
        }
    }

    public void levelUp() // With each level-up, increases attack strength and max health
    {
        setLevel(getLevel() + 1);

        setAttackStrength(getAttackStrength() + 2);
        setMaxHealth(getMaxHealth() + 10);
        setCurrentHealth(getMaxHealth()); // Refill health

    }


    public int calculateArmorRating() // Adds up individual armor ratings of equipped pieces of armor
    {
        int armor = 0;
        if(equippedHelmet != null)
        {
            armor += equippedHelmet.getBaseRating();
        }
        if(equippedChestplate != null)
        {
            armor += equippedChestplate.getBaseRating();
        }
        if(equippedGauntlets != null)
        {
            armor += equippedGauntlets.getBaseRating();
        }
        if(equippedLeggings != null)
        {
            armor += equippedLeggings.getBaseRating();
        }
        if(equippedBoots != null)
        {
            armor += equippedBoots.getBaseRating();
        }

        return armor;
    }

    public int calculateAttackStrength() // Adds base damage of weapon to base attack strength
    {
        int fullStrength;
        if(equippedWeapon != null)
        {
            fullStrength = equippedWeapon.getBaseDamage() + getAttackStrength();
        }
        else fullStrength = getAttackStrength();
        return fullStrength;
    }

    public String currentStoryLine()
    {
        if(monstersKilled < 20)
        {
            return STORY_LINES[monstersKilled];
        }
        else return STORY_LINES[19];

    }

    public void initializeStoryLines()
    {
        STORY_LINES[0] = "Welcome to Chemists vs Republicans, a role-playing game about minimal science through\n" +
                "maximum violence!\n" +
                "The world is in grave danger: Donald Trump has been cloned, and the resulting abominations\n" +
                "are threatening the future of chemistry!\n" +
                "It's up to you to stop them, young chemist. Your story begins here, in your hometown of Electron.\n" +
                "Press 'Attack' to start your first fight!\n" +
                "(TIP: use the 'A' and 'D' keys to move left and right, and the spacebar to attack.)";
        STORY_LINES[1] = "Well done, young chemist! You are on your way to spreading the glory of the Periodic Table.\n" +
                "As you trump your first hair-abomination, you feel yourself growing stronger.\n" +
                "(TIP: check out the 'Inventory' button to see if you can equip or use any of the items you got\n" +
                "from your first battle.)";
        STORY_LINES[2] = "Level-up! As your experience in neutralizing the base hair-abominations with your\n" +
                "acidic ruthlessness grows, so does your strength.\n" +
                "(TIP: notice that your strength and maximum health have increased, in addition to your health\n" +
                "automatically refilling. This will happen with every level you gain.";
        STORY_LINES[3] = "Another victory for chemistry! But the apocalypse still rages strong.\n" +
                "Don't give up now; science is counting on you!\n" +
                "(TIP: check out the 'Shop' button to see if you can sell any of the extra items\n" +
                "you've acquired from the last few battles, or if you can afford any new ones.)";
        STORY_LINES[4] = "Wow, they're getting tough! You didn't accidentally say anything glorifying physics, right?\n" +
                "That makes the chemistry gods very upset. There can be only one science.\n" +
                "Remember that, young chemist.";
        STORY_LINES[5] = "Are you SURE you didn't compliment physics, like, at all?\n" +
                "Nothing about forces, or that Newton criminal, or Galileo the Heretic?\n" +
                "Okay, good. Just making sure. ...What do you mean, 'lack of trust?' Of course I trust you!";
        STORY_LINES[6] = "You're slicing through these guys like gallium metal. Well done!\n" +
                "You begin to make your way out of your hometown and start heading towards the next\n" +
                "city over, Positron. But wait... something smells off...";
        STORY_LINES[7] = "You walk tentatively into the city, but everything seems very strange.\n" +
                "Why is everything happening backwards?\n" +
                "It's almost like this city is from the future...\n" +
                "Of course! You've entered the realm of antimatter! You have to get out of here, quickly!";
        STORY_LINES[8] = "It's pretty difficult to have fights that already happened...\n" +
                "You really need to find a way out of Positron.\n" +
                "Chemistry works great when the laws of physics apply, but when they don't... it's pretty useless.\n" +
                "What do you mean, I mentioned... oh, great, I said the p-word.";
        STORY_LINES[9] = "Is that a way out? It looks like a gate on the other side of the road!\n" +
                "Just watch out for the reverse-combustion reactions that are happening all around\n" +
                "- you don't want to be caught in one of those anti-explosions!";
        STORY_LINES[10] = "You finally reach the gate, and... you're out!\n" +
                "Now that you're on the road again (copyright Willie Nelson), you can continue your journey.\n" +
                "The next town is called Gamma Ray. Tread carefully; it's inhabited by the reclusive Photons,\n" +
                "and you can never know their exact position and velocity at the same time.\n";
        STORY_LINES[11] = "Ah, Gamma Ray, such a fast-moving town!\n" +
                "No, really, it's moving at almost 300,000,000 meters per second. The G-force is to die for.\n" +
                "Darn it! I said force! I'm sorry, chemistry gods!\n" +
                "...What do you mean, you're going to reverse my entropy? We're already out of Positron!";
        STORY_LINES[12] = "So, while you're busy slashing your way through this lovely little settlement,\n" +
                "here's a fun fact I probably should have mentioned...\n" +
                "Time's going to run kind of oddly since you're moving so fast.\n" +
                "In fact, it pretty much won't move at all for you.\n" +
                "Now, everyone else who's NOT moving at the speed you are,\n" +
                "is experiencing a different time. Standing still, we get 'normal time.'\n" +
                "Of course, there's really no such thing as a normal standard of time,\n" +
                "because time is relative and all that.\n" +
                "Sorry, am I confusing you with this stuff? Grow up, it's only Einstein's Theory of Special Relativity.\n" +
                "I could've gone with the fourth dimension; THAT'S confusing!";
        STORY_LINES[13] = "You almost out of Gamma Ray? Oh wait, I'm supposed to tell you that.\n" +
                "So! You at last make your way out of Gamma Ray, having slain numerous hair-abominations.\n" +
                "You don't even notice as time resumes its normal flow - because, to you,\n" +
                "it's been running perfectly normally.\n" +
                "Eh, let's stop with that.\n" +
                "Next destination: Neutron!";
        STORY_LINES[14] = "You see the nucleus ahead of you; you're getting close to the heart of the problem.\n" +
                "The hair-abominations grow increasingly difficult,\n" +
                "knowing that you are nearing their lair and need to be stopped.\n" +
                "Once you get to Neutron... it's a straight shot to Proton, where all this will end! Stay strong!";
        STORY_LINES[15] = "As you begin to approach the small cluster of buildings that is Neutron,\n" +
                "you start to feel a faint tugging sensation.\n" +
                "Gravity! The only significant mass for miles, the nucleus is creating a pull on you... what?\n" +
                "Oh, right, gravity is strictly physics.\n" +
                "Sorry, can't talk about this.\n" +
                "So... YOU PASS OUT AND WAKE UP IN NEUTRON! Whew, these censoring freaks are making my job -";
        STORY_LINES[16] = "The hair-abominations are making their last stand as you carve your way through Neutron.\n" +
                "Beyond the horizon, you can see it looming off in the distance:\n" +
                "Proton, the core of the atom, radiating such a positive atmosphere that you feel instantly\n" +
                "attracted.";
        STORY_LINES[17] = "It's directly in front of you.\n" +
                "As you stare apprehensively up at the looming gate leading from Neutron to Proton,\n" +
                "you feel a tingle go down your spine.\n" +
                "That's the positive charge. People don't get tingles down their spines from nerves;\n" +
                "they just poop their pants. Don't do that, though.";
        STORY_LINES[18] = "Proton: small, dense, but heavily guarded. It will be a difficult fight from here on out.\n" +
                "Suddenly you see him: the original clone, the one that started this whole mess.\n" +
                "It's time to finish this!";
        STORY_LINES[19] = "You did it! You've saved the world of Atom from the terror that was Donald Trump's clones.\n" +
                "But there is still more work to be done!\n" +
                "The chemistry gods have cursed the Quantum Universe with stray Trump Clones.\n" +
                "Find them all and destroy them!\n" +
                "You'll know you're done when you've reached level 6.";
    }



    // ******************* GETTERS/SETTERS *******************
    public int getMonstersKilled()
    {
        return monstersKilled;
    }

    public void setMonstersKilled(int monstersKilled)
    {
        this.monstersKilled = monstersKilled;
    }

    public Weapon getEquippedWeapon()
    {
        return equippedWeapon;
    }

    public void setEquippedWeapon(Weapon equippedWeapon)
    {
        this.equippedWeapon = equippedWeapon;
    }

    public Helmet getEquippedHelmet()
    {
        return equippedHelmet;
    }

    public void setEquippedHelmet(Helmet equippedHelmet)
    {
        this.equippedHelmet = equippedHelmet;
    }

    public Chestplate getEquippedChestplate()
    {
        return equippedChestplate;
    }

    public void setEquippedChestplate(Chestplate equippedChestplate)
    {
        this.equippedChestplate = equippedChestplate;
    }

    public Leggings getEquippedLeggings()
    {
        return equippedLeggings;
    }

    public void setEquippedLeggings(Leggings equippedLeggings)
    {
        this.equippedLeggings = equippedLeggings;
    }

    public Gauntlets getEquippedGauntlets()
    {
        return equippedGauntlets;
    }

    public void setEquippedGauntlets(Gauntlets equippedGauntlets)
    {
        this.equippedGauntlets = equippedGauntlets;
    }

    public Boots getEquippedBoots()
    {
        return equippedBoots;
    }

    public void setEquippedBoots(Boots equippedBoots)
    {
        this.equippedBoots = equippedBoots;
    }

    public int getRealAttackStrength() {
        return realAttackStrength;
    }

    public void setRealAttackStrength(int realAttackStrength) {
        this.realAttackStrength = realAttackStrength;
    }

    public String[] getSTORY_LINES()
    {
        return STORY_LINES;
    }
}
