package game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputMethodEvent;
import java.awt.event.InputMethodListener;
import java.awt.image.BufferedImage;

/**
 * Created by gregorygiovannini on 5/20/16.
 */
public class MainMenuGUI
{
    static class MyPainter implements Painter<JProgressBar> {

        private final Color color;

        public MyPainter(Color c1) {
            this.color = c1;
        }
        @Override
        public void paint(Graphics2D gd, JProgressBar t, int width, int height) {
            gd.setColor(color);
            gd.fillRect(0, 0, width, height);
        }
    }

    private JPanel mainMenu;
    private JButton startButton;
    private JTextField playerNameField;
    private JTextArea playerNamePrompt;
    private JTextArea gameTitle;
    private JTextArea tribute;
    private static JFrame frame;
    private ImageIcon trumpDebating;

    static Character player = new Player(null);


    //TODO Attack minigame
    //TODO Items
    //TODO Inventory system
    //TODO Shop system
    //TODO Saving/loading

    public MainMenuGUI()
    {
        playerWeaponTest();
        startButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                frame.dispose();
                player.setName(playerNameField.getText());
                GameGUI.startGame();
            }
        });


        playerNameField.addInputMethodListener(new InputMethodListener()
        {
            @Override
            public void inputMethodTextChanged(InputMethodEvent event)
            {

            }

            @Override
            public void caretPositionChanged(InputMethodEvent event)
            {

            }
        });

        gameTitle.setText("  CHEMISTS\n         VS\nREPUBLICANS");
        tribute.setText("This game is a tribute to Mr. Harrison:\n" +
                "the man, the myth, the legend...\n" +
                "and the greatest chemistry teacher ever.\n" +
                "His influence will never be forgotten.");

        /*trumpDebating = new ImageIcon("game/resources/TrumpDebating.png");
        JLabel label = new JLabel();
        label.setIcon(trumpDebating);
        mainMenu.add(label);*/
    }

    public static void main(String[] args)
    {
        try
        {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels())
            {
                if ("Nimbus".equals(info.getName()))
                {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception e)
        {
            // If Nimbus is not available, you can set the GUI to another look and feel.
        }

        MyPainter painter = new MyPainter(new Color(255,0,0));

        UIManager.getLookAndFeelDefaults().put("nimbusOrange", (new Color(33, 224, 39)));
        UIManager.getLookAndFeelDefaults().put("ProgressBar[Enabled].backgroundPainter",
                new MyPainter(Color.RED));
        //UIManager.getLookAndFeelDefaults().put("ProgressBar[Enabled].selectionForeground",
                //new MyPainter(Color.BLUE));

        frame = new JFrame("MainMenuGUI");
        frame.setContentPane(new MainMenuGUI().mainMenu);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    public void playerWeaponTest()
    {
        Weapon testWeapon = new Weapon(player);
        //testWeapon.setBaseDamage(150);
        testWeapon.equipWeapon();
        //((Player)player).setEquippedWeapon(testWeapon);
        //((Player)player).setRealAttackStrength(((Player)player).calculateAttackStrength());
        player.getInventory().addItemToInventory(testWeapon);
    }
}
