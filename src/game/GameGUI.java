package game;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by gregorygiovannini on 5/10/16.
 */
public class GameGUI
{

    private GameGUI instance;


    private JPanel mainInterface;
    private JButton attackButton;
    private JButton quitButton;
    private JTextArea monsterStats;
    private JTextArea playerStats;
    private JButton inventoryButton;
    private JProgressBar playerHealthBar;
    private JProgressBar monsterHealthBar;
    private JButton shopButton;
    private JTextArea storyLines;
    private static JFrame frame;
    //Color redColor = new Color(200, 50, 50);
    static Character player = MainMenuGUI.player;
    static Character monster = new Monster(player.getLevel());


    public GameGUI getInstance() {
        return instance;
    }

    public void clearInstance() {
        instance = null;
    }

    public void initInstance() {
        instance = null;
        instance = new GameGUI();
    }

    private GameGUI()
    {

        attackButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                //JGameGUI battleWindow
                //playerAttacksMonster();
                //monsterAttacksPlayer();
                //initInstance();
                frame.dispose();
                BattleAnimation.startBattle((Player)player, (Monster)monster);


                //startGame();

                //clearInstance();

                //test();

                /*monster.setCurrentHealth(monster.getCurrentHealth() - ((Player)player).getRealAttackStrength());
                player.setCurrentHealth(player.getCurrentHealth() - monster.getAttackStrength());*/

                /*if(monster.getCurrentHealth() <= 0) // Check if monster is dead
                {
                    monsterDied();
                }
                if(player.getCurrentHealth() <= 0) // Check if player is dead
                {
                    playerDied();
                }*/
                //updateGameGUIComponents();


                /*monsterStats.setText(monster.characterStats());
                playerStats.setText(player.characterStats());
                playerHealthBar.setValue(player.getCurrentHealth());
                monsterHealthBar.setValue(monster.getCurrentHealth());*/

            }
        });


        quitButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                System.exit(0);
            }
        });


        inventoryButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                frame.dispose();
                InventoryGUI.openInventory();
            }
        });

        shopButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                frame.dispose();
                ShopGUI.openShop();
            }
        });




        monsterStats.setText(monster.characterStats());
        /*monsterStats.append(( "REWARDS: " + "\n\n" +
                "Experience: " + GameGUI.monster.getExperience() + "\n" +
                "Coins: " + GameGUI.monster.getCoins() + "\n"));*/
        playerStats.setText(player.characterStats());


        playerHealthBar.setValue(player.getCurrentHealth());
        monsterHealthBar.setValue(monster.getCurrentHealth());

        storyLines.setText(((Player)player).currentStoryLine());
        if(player.getLevel() == 6)
        {
            storyLines.setText("YOU WIN!");
        }


        //TODO Issue: rewards displays rewards for next monster; actually receive totally different rewards
        //playerHealthBar.paint();

    }

    public static void startGame()
    {
        frame = new JFrame("GameGUI");
        frame.setContentPane(new GameGUI().mainInterface);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    public static void monsterDied()
    {
        //MonsterKilledGUI.openRewards();
        ((Player)player).transferMonsterDataToPlayer((Monster)monster);
        generateNewMonster();
    }

    public static void playerDied()
    {
        frame.dispose();
        PlayerKilledGUI.openDeathGUI();
    }



    public static void generateNewMonster()
    {
        monster = new Monster(player.getLevel());
    }

    /*public static void updateStatsInGameGUI()
    {
        player.setCurrentHealth(BattleAnimation.player.getCurrentHealth());
        monster.setCurrentHealth(BattleAnimation.monster.getCurrentHealth());
    }*/

    public void updateGameGUIComponents()
    {
        monsterStats.setText(monster.characterStats());
        playerStats.setText(player.characterStats());
        playerHealthBar.setValue(player.getCurrentHealth());
        monsterHealthBar.setValue(monster.getCurrentHealth());
    }

    private void createUIComponents()
    {

        playerHealthBar = new JProgressBar();
        monsterHealthBar = new JProgressBar();

    }

    public void test()
    {
        System.out.println("CALLED TEST");
    }



    /*public static void main(String[] args)
    {
        JFrame frame = new JFrame("GameGUI");
        frame.setContentPane(new GameGUI().mainInterface);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }*/
}
