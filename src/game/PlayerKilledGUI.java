package game;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by GregG on 6/2/16.
 */
public class PlayerKilledGUI
{
    private static JFrame frame;
    private JPanel death;
    private JTextArea deathMessage;
    private JButton quitButton;

    public PlayerKilledGUI()
    {
        quitButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                System.exit(0);
            }
        });

        deathMessage.setText("\n    YOU\n    HAVE\n    DIED!");
    }


    public static void openDeathGUI()
    {
        frame = new JFrame("PlayerKilledGUI");
        frame.setContentPane(new PlayerKilledGUI().death);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);

    }
}
