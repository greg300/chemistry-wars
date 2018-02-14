package game;

import com.ethanzeigler.jgamegui.element.CollidableImageElement;

import javax.swing.*;

/**
 * Created by gregorygiovannini on 6/7/16.
 */
public class CharacterSprite extends CollidableImageElement
{
    // ******************* INSTANCE VARIABLES *******************
    private double xVelocity;
    private double yVelocity;

    private boolean isMovingForward;
    private boolean isMovingBackward;
    private boolean isAttacking;
    private boolean canBeAttacked = true;



    // ******************* CONSTRUCTOR(S) *******************
    public CharacterSprite(String resPath, double xOrig, double yOrig, int priority, double height, double width, double xVelocity, double yVelocity)
    {
        super(resPath, xOrig, yOrig, priority, height, width);
        this.xVelocity = xVelocity;
        this.yVelocity = yVelocity;
    }

    public CharacterSprite(ImageIcon icon, double xOrig, double yOrig, double height, double width, int priority, double xVelocity, double yVelocity)
    {
        super(icon, xOrig, yOrig, height, width, priority);
        this.xVelocity = xVelocity;
        this.yVelocity = yVelocity;
    }

    // ******************* METHODS *******************



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

    public boolean canBeAttacked() {
        return canBeAttacked;
    }

    public void setCanBeAttacked(boolean canBeAttacked) {
        this.canBeAttacked = canBeAttacked;
    }
}
