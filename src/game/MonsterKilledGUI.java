package game;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by GregG on 6/2/16.
 */
public class MonsterKilledGUI
{
    private static JFrame frame;
    private JPanel rewardPopup;
    private JTextArea rewards;
    private JButton backButton;


    public MonsterKilledGUI()
    {
        backButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                frame.dispose();
                GameGUI.startGame();
            }
        });

        rewards.setText("YOU DEFEATED THE LEVEL " + GameGUI.monster.getLevel() + " " + GameGUI.monster.getName() + "!\n" +
                "REWARDS: " + "\n\n" +
                "Experience: " + GameGUI.monster.getExperience() + "\n" +
                "Coins: " + GameGUI.monster.getCoins() + "\n");
        if(GameGUI.monster.getInventory() != null)
        {
            if(GameGUI.monster.getInventory().getItems().size() > 0)
            {
                rewards.append(GameGUI.monster.getInventory().listAllItems());
            }
        }
    }



    public static void openRewards()
    {
        frame = new JFrame("MonsterKilledGUI");
        frame.setContentPane(new MonsterKilledGUI().rewardPopup);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
