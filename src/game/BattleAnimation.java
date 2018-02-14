package game;

import com.ethanzeigler.jgamegui.JGameGUI;
import com.ethanzeigler.jgamegui.element.*;
import com.ethanzeigler.jgamegui.sound.AudioClip;
import com.ethanzeigler.jgamegui.window.Window;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by gregorygiovannini on 6/6/16.
 */
public class BattleAnimation extends JGameGUI
{
    public static final int WIDTH = 1200;
    public static final int HEIGHT = 600;
    private boolean monsterTimerScheduled = false;
    private boolean playerTimerScheduled = false;
    private boolean monsterWillAttackTimerScheduled = false;
    private boolean monsterChangeDirectionScheduled = false;
    private boolean playerCanTakeDamageFromCollisionWithMonsterScheduled = true;

    private Window window;
    private Timer timer;
    private TimerTask monsterAttackable;
    private TimerTask playerAttackable;
    private TimerTask monsterWillAttack;
    private TimerTask monsterChangeDirection;
    private TimerTask playerCanTakeDamageFromCollisionWithMonster;
    private TextElement playerHealth;
    private TextElement monsterHealth;
    private AudioClip clip;
    private ImageElement background;
    private CharacterSprite playerSprite;
    private CharacterSprite monsterSprite;
    private CollidableImageElement floor;
    private WeaponSprite playerWeapon;
    private WeaponSprite monsterWeapon;
    private ButtonImageElement fleeButton;

    private CollidableImageElement trumpHeadHurt;
    private CollidableImageElement trumpHead;
    private CollidableImageElement harrisonHead;
    private CollidableImageElement harrisonHeadHurt;

    public static Player player;
    public static Monster monster;


    // put variables here

    /**
     * Called before the window is displayed
     *
     * @param g
     */
    @Override
    protected void onStart(JGameGUI g)
    {
        window = new Window();

        background = new ImageElement("game/resources/FlagBackground.png", 0, 0, 0);
        floor = new CollidableImageElement("game/resources/Floor.png", 0, 0, 0, 200, 200);

        playerSprite = new CharacterSprite("game/resources/HarrisonHead.png", 50, 120, 1, 200, 200, 0, 0);
        monsterSprite = new CharacterSprite("game/resources/TrumpHead.png", 800, 120, 1, 200, 200, 0, 0);
        playerWeapon = new WeaponSprite("game/resources/greenLightsaberPhases/Lightsaber0.png", 0, 0, 0, 30, 200, 0, 0);
        monsterWeapon = new WeaponSprite("game/resources/redLightsaberPhases/Lightsaber0.png", 0, 0, 0, 30, 250, 0, 0);

        trumpHead = new CollidableImageElement("game/resources/TrumpHead.png", 800, 120, 1, 200, 200);
        trumpHeadHurt = new CollidableImageElement("game/resources/TrumpHeadHurt.png", 800, 120, 1, 200, 200);
        harrisonHead = new CollidableImageElement("game/resources/HarrisonHead.png", 800, 120, 1, 200, 200);
        harrisonHeadHurt = new CollidableImageElement("game/resources/HarrisonHeadHurt.png", 800, 120, 1, 200, 200);

        fleeButton = new ButtonImageElement("game/resources/FleeButton.png", 450, 420, 1, 280, 150);

        playerHealth = new TextElement(150, 500, 2, "");
        playerHealth.setText(player.getName() + ": " + player.getCurrentHealth() + " / " + player.getMaxHealth());
        playerHealth.setColor(Color.GREEN);


        monsterHealth = new TextElement(750, 500, 2, "");
        monsterHealth.setText(monster.getName() + ": " + monster.getCurrentHealth() + " / " + monster.getMaxHealth());
        monsterHealth.setColor(Color.RED);

        addPlayerWeaponPhasesToArray();
        addMonsterWeaponPhasesToArray();


        fleeButton.addButtonClickListener(new ButtonClickListener()
        {
            @Override
            public void onClick()
            {
                stop();
            }
        });


        window.addElement(background);
        window.addElement(floor);
        window.addElement(playerSprite);
        window.addElement(monsterSprite);
        window.addElement(fleeButton);
        window.addElement(playerHealth);
        window.addElement(monsterHealth);


        g.setWindow(window);

    }

    /**
     * <p>Called before the GUI is updated each frame and can be used to update AbstractElement positions.
     * This is invoked <i><s>before</s></i>
     * any animations defined in AbstractElement</p>
     *
     * @param gui The JGameGUI instance that is updating
     */
    @Override
    protected void onScreenUpdate(JGameGUI gui)
    {
        // ******************* PLAYER MOVEMENT *******************
        if(playerSprite.isMovingForward() && playerSprite.getxVelocity() < 0) // Player is holding right while moving left
        {
            playerSprite.setxVelocity(playerSprite.getxVelocity() + 2);
        }
        else if(playerSprite.isMovingForward()) // Player is holding right
        {
            playerSprite.setxVelocity(playerSprite.getxVelocity() + 0.5);
        }

        if(playerSprite.isMovingBackward() && playerSprite.getxVelocity() > 0) // Player is holding left while moving right
        {
            playerSprite.setxVelocity(playerSprite.getxVelocity() - 2);
        }
        else if(playerSprite.isMovingBackward()) // Player is holding left
        {
            playerSprite.setxVelocity(playerSprite.getxVelocity() - 0.5);
        }


        if(!playerSprite.isMovingBackward() && !playerSprite.isMovingForward() && playerSprite.getxVelocity() >= 1) // Player is not holding keys, and their velocity is high and right
        {
            playerSprite.setxVelocity(playerSprite.getxVelocity() - 1);
        }
        else if(!playerSprite.isMovingBackward() && !playerSprite.isMovingForward() && playerSprite.getxVelocity() <= -1) // Player is not holding keys, and their velocity is high and left
        {
            playerSprite.setxVelocity(playerSprite.getxVelocity() + 1);
        }
        else if(!playerSprite.isMovingBackward() && !playerSprite.isMovingForward() && (playerSprite.getxVelocity() < 1 && playerSprite.getxVelocity() > -1)) // Player is not moving, and their velocity low (stops drifting)
        {
            playerSprite.setxVelocity(0);
        }




        // ******************* PLAYER ATTACKING *******************
        if(playerSprite.isAttacking()) // Weapon is swinging
        {
            if(playerWeapon.getCurrentPhase() < playerWeapon.getPhases().length) // Weapon is still swinging through phases
            {
                playerWeapon.setImg(playerWeapon.getPhases()[playerWeapon.getCurrentPhase()].getImg());
                playerWeapon.setCurrentPhase(playerWeapon.getCurrentPhase() + 1);
                playerWeapon.setOriginY(playerWeapon.getOriginY() + 12);
                playerWeapon.setOriginX(playerWeapon.getOriginX() + playerSprite.getxVelocity());

                if(playerWeapon.isCollidingWith(monsterSprite)) // Weapon is touching monster
                {
                    //System.out.println("Collision");
                    monsterSprite.setCanBeAttacked(false); // Cannot be attacked until after cooldown - this boolean will be referenced for damaging
                }
            }
            else // Weapon is done swinging; remove from screen
            {
                window.removeElement(playerWeapon);
                playerSprite.setAttacking(false);
                //monsterSprite.setCanBeAttacked(true);
            }
        }

        timer = new Timer();
        monsterAttackable = new TimerTask() // Cooldown timer for monster to be attackable
        {
            @Override
            public void run() // Returns monster to normal state; can be attacked again
            {
                monsterSprite.setImg(trumpHead.getImg());
                monsterSprite.setCanBeAttacked(true);
                monsterTimerScheduled = false;

            }
        };

        if(!monsterSprite.canBeAttacked() && !monsterTimerScheduled) // Monster cannot be attacked (weapon has collided with monster) and cooldown timer not yet scheduled
        {
            monsterTimerScheduled = true;
            System.out.println("Monster Attacked!");
            timer.schedule(monsterAttackable, 1000);
            monsterSprite.setImg(trumpHeadHurt.getImg());

            monster.setCurrentHealth(monster.getCurrentHealth() - (player).getRealAttackStrength());
            monsterHealth.setText(monster.getName() + ": " + monster.getCurrentHealth() + " / " + monster.getMaxHealth());
            //GameGUI.updateStatsInGameGUI();


            if(monster.getCurrentHealth() <= 0) // Check if monster is dead after every attack
            {
                //GameGUI.monsterDied();
                stop();
            }
        }



        // ******************* MONSTER ATTACKING *******************
        if(monsterSprite.isAttacking()) // Monster weapon is swinging
        {
            if(monsterWeapon.getCurrentPhase() < monsterWeapon.getPhases().length) // Monster weapon is still swinging through phases
            {
                monsterWeapon.setImg(monsterWeapon.getPhases()[monsterWeapon.getCurrentPhase()].getImg());
                monsterWeapon.setCurrentPhase(monsterWeapon.getCurrentPhase() + 1);
                monsterWeapon.setOriginY(monsterWeapon.getOriginY() + 12);
                monsterWeapon.setOriginX(monsterWeapon.getOriginX() + monsterSprite.getxVelocity() -15);

                if(monsterWeapon.isCollidingWith(playerSprite)) // Monster weapon is touching player
                {
                    //System.out.println("Collision");
                    playerSprite.setCanBeAttacked(false); // Cannot be attacked until after cooldown - this boolean will be referenced for damaging
                }
            }
            else // Weapon is done swinging; remove from screen
            {
                window.removeElement(monsterWeapon);
                monsterSprite.setAttacking(false);
                //monsterSprite.setCanBeAttacked(true);
            }
        }

        playerAttackable = new TimerTask() // Cooldown timer for player to be attackable
        {
            @Override
            public void run() // Returns player to normal state; can be attacked again
            {
                playerSprite.setImg(harrisonHead.getImg());
                playerSprite.setCanBeAttacked(true);
                playerTimerScheduled = false;
            }
        };

        if(!playerSprite.canBeAttacked() && !playerTimerScheduled) // Player cannot be attacked (weapon has collided with player) and cooldown timer not yet scheduled
        {
            playerTimerScheduled = true;
            System.out.println("Player Attacked!");
            timer.schedule(playerAttackable, 1000);
            playerSprite.setImg(harrisonHeadHurt.getImg());

            monsterAttacksPlayer();
            playerHealth.setText(player.getName() + ": " + player.getCurrentHealth() + " / " + player.getMaxHealth());
            //GameGUI.updateStatsInGameGUI();

            if(player.getCurrentHealth() <= 0) // Check if player is dead after every attack
            {
                //GameGUI.playerDied();
                stop();
            }
        }

        // ******************* MONSTER ATTACK AI *******************
        monsterWillAttack = new TimerTask() // Random cooldown timer for monster to attack next
        {
            @Override
            public void run() // Attacks
            {
                monsterSprite.setAttacking(true);
                monsterWeapon.setCurrentPhase(0);
                monsterWeapon.setOriginX(monsterSprite.getOriginX() + 50);
                monsterWeapon.setOriginY(monsterSprite.getOriginY() - 100);
                monsterWillAttackTimerScheduled = false;

                window.addElement(monsterWeapon);
            }
        };

        if(!monsterSprite.isAttacking() && !monsterWillAttackTimerScheduled)
        {
            monsterWillAttackTimerScheduled = true;
            System.out.println("Monster Attacking!");
            timer.schedule(monsterWillAttack, generateMonsterAttackWaitDurationInSeconds() * 1000);
        }

        // ******************* MONSTER MOVING AI *******************


        monsterChangeDirection = new TimerTask() // Random cooldown timer for monster to change direction
        {
            @Override
            public void run() // Changes direction
            {
                monsterSprite.setxVelocity(generateMonsterVelocity());
                monsterChangeDirectionScheduled = false;
            }
        };

        if(!monsterChangeDirectionScheduled) // If monster is ready to change direction
        {
            monsterChangeDirectionScheduled = true;
            timer.schedule(monsterChangeDirection, generateMonsterAttackWaitDurationInSeconds() * 1000);
        }

        playerCanTakeDamageFromCollisionWithMonster = new TimerTask() // Specific cooldown timer for player to take damage from monster while touching
        {
            @Override
            public void run() // Countdown
            {
                playerCanTakeDamageFromCollisionWithMonsterScheduled = true;
            }
        };

        if(monsterSprite.isCollidingWith(playerSprite) && playerCanTakeDamageFromCollisionWithMonsterScheduled) // If player runs into monster or vice versa
        {
            playerCanTakeDamageFromCollisionWithMonsterScheduled = false;
            playerSprite.setImg(harrisonHeadHurt.getImg());
            player.setCurrentHealth(player.getCurrentHealth() - 5);
            playerHealth.setText(player.getName() + ": " + player.getCurrentHealth() + " / " + player.getMaxHealth());
            if(player.getCurrentHealth() <= 0) // Check if player is dead
            {
                //GameGUI.playerDied();
                stop();
            }
            timer.schedule(playerCanTakeDamageFromCollisionWithMonster, 1000);


        }

        else if(playerSprite.canBeAttacked() && !monsterSprite.isCollidingWith(playerSprite)) // If player stops touching monster and is not being attacked
        {
            playerSprite.setImg(harrisonHead.getImg());
        }




        // ******************* BARRIERS *******************
        if(playerSprite.getOriginX() <= -50 && playerSprite.getxVelocity() < 0) // Creates boundary on left
        {
            playerSprite.setxVelocity(0);
        }
        if(playerSprite.getOriginX() >= monsterSprite.getOriginX() - 50 && playerSprite.getxVelocity() > 0) // Creates boundary on right with monster when player is moving
        {
            playerSprite.setxVelocity(0);
        }
        if(playerSprite.getOriginX() >= monsterSprite.getOriginX() - 50 && playerSprite.getxVelocity() == 0) // Creates boundary on right with monster when player is still
        {
            if(monsterSprite.getxVelocity() < 0) // If monster is moving toward player side
            {
                playerSprite.setOriginX(monsterSprite.getOriginX() - 50);
            }
        }


        if(monsterSprite.getOriginX() <= 300 && monsterSprite.getxVelocity() < 0) // Creates boundary on left
        {
            monsterSprite.setxVelocity(0);
        }
        if(monsterSprite.getOriginX() >= 750 && monsterSprite.getxVelocity() > 0) // Creates boundary on right
        {
            monsterSprite.setxVelocity(0);
        }


        // ******************* POSITION UPDATE *******************
        playerSprite.setOriginX(playerSprite.getOriginX() + playerSprite.getxVelocity());
        monsterSprite.setOriginX(monsterSprite.getOriginX() + monsterSprite.getxVelocity());


    }

    /**
     * Called when the VM is being disabled. This method can be used to dispose of any variables or save data.
     */
    @Override
    protected void onStop()
    {
        //GameGUI.updateStatsInGameGUI();


        if(monster.getCurrentHealth() <= 0)
        {
            MonsterKilledGUI.openRewards();
            GameGUI.monsterDied();
        }
        else if(player.getCurrentHealth() <= 0)
        {
            GameGUI.playerDied();
        }
        else GameGUI.startGame();

    }

    /**
     * Invoked when a mouse button has been pressed on a component.
     *
     * @param e the MouseEvent
     */
    @Override
    public void mousePressed(MouseEvent e) {
        super.mousePressed(e);
    }

    /**
     * Invoked when the mouse enters a component.
     *
     * @param e the MouseEvent
     */
    @Override
    public void mouseEntered(MouseEvent e) {
        super.mouseEntered(e);
    }

    /**
     * Invoked when a mouse button has been released on a component.
     *
     * @param e the MouseEvent
     */
    @Override
    public void mouseReleased(MouseEvent e) {
        super.mouseReleased(e);
    }

    /**
     * Invoked when the mouse exits a component.
     *
     * @param e the MouseEvent
     */
    @Override
    public void mouseExited(MouseEvent e) {
        super.mouseExited(e);
    }

    /**
     * Invoked when a mouse button is pressed on a component and then
     * dragged.  <code>MOUSE_DRAGGED</code> events will continue to be
     * delivered to the component where the drag originated until the
     * mouse button is released (regardless of whether the mouse position
     * is within the bounds of the component).
     * <p>
     * Due to platform-dependent Drag&amp;Drop implementations,
     * <code>MOUSE_DRAGGED</code> events may not be delivered during a native
     * Drag&amp;Drop operation.
     *
     * @param e the MouseEvent
     */
    @Override
    public void mouseDragged(MouseEvent e) {
        super.mouseDragged(e);
    }

    /**
     * Invoked when the mouse cursor has been moved onto a component
     * but no buttons have been pushed.
     *
     * @param e the MouseEvent
     */
    @Override
    public void mouseMoved(MouseEvent e) {
        super.mouseMoved(e);
    }

    /**
     * Invoked when a key has been typed.
     * See the class description for {@link KeyEvent} for a definition of
     * a key typed event.
     *
     * @param e the KeyEvent
     */
    @Override
    public void keyTyped(KeyEvent e) {
        if(e.getKeyChar() == 'd')
        {
            //playerSprite.setxVelocity(playerSprite.getxVelocity() + 3);
            playerSprite.setMovingBackward(false);
            playerSprite.setMovingForward(true);

        }
        if(e.getKeyChar() == 'a')
        {
            //playerSprite.setxVelocity(playerSprite.getxVelocity() - 3);
            playerSprite.setMovingForward(false);
            playerSprite.setMovingBackward(true);
        }
        if(e.getKeyChar() == ' ' && !playerSprite.isAttacking())
        {
            playerSprite.setAttacking(true);
            playerWeapon.setCurrentPhase(0);
            playerWeapon.setOriginX(playerSprite.getOriginX() + 220);
            playerWeapon.setOriginY(playerSprite.getOriginY() - 100);
            //playerWeapon = new WeaponSprite("game/resources/lightsaberPhases/Lightsaber0.png", playerSprite.getOriginX() + 230, playerSprite.getOriginY() - 50, 1, 200, 200, playerSprite.getxVelocity(), playerSprite.getyVelocity());

            window.addElement(playerWeapon);
        }
        /*if(e.getKeyChar() == 'e' && !monsterSprite.isAttacking())
        {
            monsterSprite.setAttacking(true);
            monsterWeapon.setCurrentPhase(0);
            monsterWeapon.setOriginX(monsterSprite.getOriginX() + 50);
            monsterWeapon.setOriginY(monsterSprite.getOriginY() - 100);

            window.addElement(monsterWeapon);
        }*/
    }

    /**
     * Invoked when a key has been pressed.
     * See the class description for {@link KeyEvent} for a definition of
     * a key pressed event.
     *
     * @param e the KeyEvent
     */
    @Override
    public void keyPressed(KeyEvent e) {
        super.keyPressed(e);
    }

    /**
     * Invoked when a key has been released.
     * See the class description for {@link KeyEvent} for a definition of
     * a key released event.
     *
     * @param e the KeyEvent
     */
    @Override
    public void keyReleased(KeyEvent e)
    {
        super.keyReleased(e);
        if(e.getKeyChar() == 'd') // Stop accelerating right
        {
            //playerSprite.setxVelocity(playerSprite.getxVelocity() - 3);
            playerSprite.setMovingForward(false);

        }
        if(e.getKeyChar() == 'a') // Stop accelerating left
        {
            //playerSprite.setxVelocity(playerSprite.getxVelocity() + 3);
            playerSprite.setMovingBackward(false);
        }
    }

    /**
     * Creates a new JGameGUI and immediately begins to start the frame.
     */
    public BattleAnimation() {
        super(WIDTH, HEIGHT);
    }

    public void addPlayerWeaponPhasesToArray()
    {
        playerWeapon.addImageToPhases(new CollidableImageElement("game/resources/greenLightsaberPhases/Lightsaber0.png", 0, 0, 0, 0, 0), 0);
        playerWeapon.addImageToPhases(new CollidableImageElement("game/resources/greenLightsaberPhases/Lightsaber5.png", 0, 0, 0, 0, 0), 1);
        playerWeapon.addImageToPhases(new CollidableImageElement("game/resources/greenLightsaberPhases/Lightsaber10.png", 0, 0, 0, 0, 0), 2);
        playerWeapon.addImageToPhases(new CollidableImageElement("game/resources/greenLightsaberPhases/Lightsaber15.png", 0, 0, 0, 0, 0), 3);
        playerWeapon.addImageToPhases(new CollidableImageElement("game/resources/greenLightsaberPhases/Lightsaber20.png", 0, 0, 0, 0, 0), 4);
        playerWeapon.addImageToPhases(new CollidableImageElement("game/resources/greenLightsaberPhases/Lightsaber25.png", 0, 0, 0, 0, 0), 5);
        playerWeapon.addImageToPhases(new CollidableImageElement("game/resources/greenLightsaberPhases/Lightsaber30.png", 0, 0, 0, 0, 0), 6);
        playerWeapon.addImageToPhases(new CollidableImageElement("game/resources/greenLightsaberPhases/Lightsaber35.png", 0, 0, 0, 0, 0), 7);
        playerWeapon.addImageToPhases(new CollidableImageElement("game/resources/greenLightsaberPhases/Lightsaber40.png", 0, 0, 0, 0, 0), 8);
        playerWeapon.addImageToPhases(new CollidableImageElement("game/resources/greenLightsaberPhases/Lightsaber45.png", 0, 0, 0, 0, 0), 9);
        playerWeapon.addImageToPhases(new CollidableImageElement("game/resources/greenLightsaberPhases/Lightsaber50.png", 0, 0, 0, 0, 0), 10);
        playerWeapon.addImageToPhases(new CollidableImageElement("game/resources/greenLightsaberPhases/Lightsaber55.png", 0, 0, 0, 0, 0), 11);
        playerWeapon.addImageToPhases(new CollidableImageElement("game/resources/greenLightsaberPhases/Lightsaber60.png", 0, 0, 0, 0, 0), 12);
        playerWeapon.addImageToPhases(new CollidableImageElement("game/resources/greenLightsaberPhases/Lightsaber65.png", 0, 0, 0, 0, 0), 13);
        playerWeapon.addImageToPhases(new CollidableImageElement("game/resources/greenLightsaberPhases/Lightsaber70.png", 0, 0, 0, 0, 0), 14);
        playerWeapon.addImageToPhases(new CollidableImageElement("game/resources/greenLightsaberPhases/Lightsaber75.png", 0, 0, 0, 0, 0), 15);
        playerWeapon.addImageToPhases(new CollidableImageElement("game/resources/greenLightsaberPhases/Lightsaber80.png", 0, 0, 0, 0, 0), 16);
        playerWeapon.addImageToPhases(new CollidableImageElement("game/resources/greenLightsaberPhases/Lightsaber85.png", 0, 0, 0, 0, 0), 17);
        playerWeapon.addImageToPhases(new CollidableImageElement("game/resources/greenLightsaberPhases/Lightsaber90.png", 0, 0, 0, 0, 0), 18);
    }

    public void addMonsterWeaponPhasesToArray()
    {
        monsterWeapon.addImageToPhases(new CollidableImageElement("game/resources/redLightsaberPhases/Lightsaber0.png", 0, 0, 0, 0, 0), 0);
        monsterWeapon.addImageToPhases(new CollidableImageElement("game/resources/redLightsaberPhases/Lightsaber5.png", 0, 0, 0, 0, 0), 1);
        monsterWeapon.addImageToPhases(new CollidableImageElement("game/resources/redLightsaberPhases/Lightsaber10.png", 0, 0, 0, 0, 0), 2);
        monsterWeapon.addImageToPhases(new CollidableImageElement("game/resources/redLightsaberPhases/Lightsaber15.png", 0, 0, 0, 0, 0), 3);
        monsterWeapon.addImageToPhases(new CollidableImageElement("game/resources/redLightsaberPhases/Lightsaber20.png", 0, 0, 0, 0, 0), 4);
        monsterWeapon.addImageToPhases(new CollidableImageElement("game/resources/redLightsaberPhases/Lightsaber25.png", 0, 0, 0, 0, 0), 5);
        monsterWeapon.addImageToPhases(new CollidableImageElement("game/resources/redLightsaberPhases/Lightsaber30.png", 0, 0, 0, 0, 0), 6);
        monsterWeapon.addImageToPhases(new CollidableImageElement("game/resources/redLightsaberPhases/Lightsaber35.png", 0, 0, 0, 0, 0), 7);
        monsterWeapon.addImageToPhases(new CollidableImageElement("game/resources/redLightsaberPhases/Lightsaber40.png", 0, 0, 0, 0, 0), 8);
        monsterWeapon.addImageToPhases(new CollidableImageElement("game/resources/redLightsaberPhases/Lightsaber45.png", 0, 0, 0, 0, 0), 9);
        monsterWeapon.addImageToPhases(new CollidableImageElement("game/resources/redLightsaberPhases/Lightsaber50.png", 0, 0, 0, 0, 0), 10);
        monsterWeapon.addImageToPhases(new CollidableImageElement("game/resources/redLightsaberPhases/Lightsaber55.png", 0, 0, 0, 0, 0), 11);
        monsterWeapon.addImageToPhases(new CollidableImageElement("game/resources/redLightsaberPhases/Lightsaber60.png", 0, 0, 0, 0, 0), 12);
        monsterWeapon.addImageToPhases(new CollidableImageElement("game/resources/redLightsaberPhases/Lightsaber65.png", 0, 0, 0, 0, 0), 13);
        monsterWeapon.addImageToPhases(new CollidableImageElement("game/resources/redLightsaberPhases/Lightsaber70.png", 0, 0, 0, 0, 0), 14);
        monsterWeapon.addImageToPhases(new CollidableImageElement("game/resources/redLightsaberPhases/Lightsaber75.png", 0, 0, 0, 0, 0), 15);
        monsterWeapon.addImageToPhases(new CollidableImageElement("game/resources/redLightsaberPhases/Lightsaber80.png", 0, 0, 0, 0, 0), 16);
        monsterWeapon.addImageToPhases(new CollidableImageElement("game/resources/redLightsaberPhases/Lightsaber85.png", 0, 0, 0, 0, 0), 17);
        monsterWeapon.addImageToPhases(new CollidableImageElement("game/resources/redLightsaberPhases/Lightsaber90.png", 0, 0, 0, 0, 0), 18);

    }

    public static int generateMonsterAttackWaitDurationInSeconds()
    {
        int duration;
        duration = (int) (Math.random() * 3);
        return duration;
    }

    public static int generateMonsterVelocity()
    {
        int velocity;
        velocity = (int) (Math.random() * 5);
        int negativeDeterminer = (int) (Math.random() * 2);
        if(negativeDeterminer == 1)
            return velocity * -1;
        else return velocity;
    }

    public void monsterAttacksPlayer()
    {
        int currentPlayerHealth = player.getCurrentHealth();
        int playerArmorRating = player.getArmorRating();
        int monsterHitDamage = monster.generateRandomHitDamage();

        monsterHitDamage = monsterHitDamage - playerArmorRating;
        if(monsterHitDamage < 0)
            monsterHitDamage = 0;

        player.setCurrentHealth(currentPlayerHealth - monsterHitDamage);
    }

    public static void startBattle(Player player, Monster monster)
    {
        BattleAnimation.player = player;
        BattleAnimation.monster = monster;
        new BattleAnimation();
    }



    /*public static void main(String[] args) {
        new BattleAnimation();
    }*/
}
