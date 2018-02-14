package game;

import com.ethanzeigler.jgamegui.element.CollidableImageElement;

import javax.swing.*;
import java.awt.*;

/**
 * Created by gregorygiovannini on 6/8/16.
 */
public class WeaponSprite extends CollidableImageElement
{
    // ******************* INSTANCE VARIABLES *******************
    private double xVelocity;
    private double yVelocity;

    private boolean isMovingForward;
    private boolean isMovingBackward;
    private boolean isAttacking;

    private int currentPhase;

    private CollidableImageElement[] phases = new CollidableImageElement[19];


    // ******************* CONSTRUCTOR(S) *******************
    public WeaponSprite(String resPath, double xOrig, double yOrig, int priority, double height, double width, double xVelocity, double yVelocity)
    {
        super(resPath, xOrig, yOrig, priority, height, width);
        this.xVelocity = xVelocity;
        this.yVelocity = yVelocity;
    }

    public WeaponSprite(ImageIcon icon, double xOrig, double yOrig, double height, double width, int priority, double xVelocity, double yVelocity)
    {
        super(icon, xOrig, yOrig, height, width, priority);
        this.xVelocity = xVelocity;
        this.yVelocity = yVelocity;
    }

    // ******************* METHODS *******************
    public void addImageToPhases(CollidableImageElement e, int i)
    {
        phases[i] = e;
    }


    // ******************* GETTERS/SETTERS *******************
    public double getxVelocity() {
        return xVelocity;
    }

    public void setxVelocity(double xVelocity) {
        this.xVelocity = xVelocity;
    }

    public double getyVelocity() {
        return yVelocity;
    }

    public void setyVelocity(double yVelocity) {
        this.yVelocity = yVelocity;
    }

    public boolean isMovingForward() {
        return isMovingForward;
    }

    public void setMovingForward(boolean movingForward) {
        isMovingForward = movingForward;
    }

    public boolean isMovingBackward() {
        return isMovingBackward;
    }

    public void setMovingBackward(boolean movingBackward) {
        isMovingBackward = movingBackward;
    }

    public boolean isAttacking()
    {
        return isAttacking;
    }

    public void setAttacking(boolean attacking)
    {
        isAttacking = attacking;
    }


    public int getCurrentPhase() {
        return currentPhase;
    }

    public void setCurrentPhase(int currentPhase) {
        this.currentPhase = currentPhase;
    }

    public CollidableImageElement[] getPhases() {
        return phases;
    }

    public void setPhases(CollidableImageElement[] phases) {
        this.phases = phases;
    }
}
