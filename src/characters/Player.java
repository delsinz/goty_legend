/* SWEN20003 Object Oriented Software Development
 * game.RPG Game Engine
 * Author: Mingyang Zhang (Delsin)
 * Login: mingyangz
 */

package characters;

import org.newdawn.slick.SlickException;
import org.newdawn.slick.Image;
import org.newdawn.slick.Graphics;

import world.*;

public final class Player extends Character
{
    private static final double PLAYER_START_X = 756;
    private static final double PLAYER_START_Y = 684;
    private static final float SCALE = 0.07f;

    public Player()
    throws SlickException
    {
        super();
        this.rightImg = new Image("assets/units/link-8-bit.png").getScaledCopy(SCALE);
        this.leftImg = rightImg.getFlippedCopy(true, false);
        this.model = rightImg;
        this.x = PLAYER_START_X;
        this.y = PLAYER_START_Y;
        this.speed = 1;
    }

    @Override
    public double getX()
    throws SlickException
    {
        return this.x;
    }

    @Override
    public double getY()
    throws SlickException
    {
        return this.y;
    }

    @Override
    public Image getModel()
    throws SlickException
    {
        return this.model;
    }

    @Override
    public void update(int dirX, int dirY, World world, int delta)
    throws SlickException
    {
        /* Update player's (x, y) pos */
        this.move(nextX(dirX, world), nextY(dirY, world), delta);
    }

    @Override
    public void render(Graphics g, Camera camera)
    throws SlickException
    {
        this.model.drawCentered((float)(x - camera.getX()), (float)(y - camera.getY()));
    }

    /** Move player by displacement (displaceX, displaceY) */
    @Override
    protected void move(double displaceX, double displaceY, int delta)
    throws SlickException
    {
        /* Update facing direction */
        if(displaceX > 0) {
            model = rightImg;
        } else if(displaceX < 0) {
            model = leftImg;
        }
        /* Move player model */
        this.x += displaceX;
        this.y += displaceY;
    }

    /** Get friction in player's x direction
     * i.e. check whether player can move in x direction */
    @Override
    protected int frictionX(int dirX, World world)
    throws SlickException {
        double velocityX = dirX * this.speed;
        double playerNextX = this.x + velocityX;
        return world.xFrictionAt(playerNextX);
    }

    /** Get friction in player's y direction
     * i.e. check whether player can move in y direction */
    @Override
    protected int frictionY(int dirY, World world)
    throws SlickException {
        double velocityY = dirY * this.speed;
        double playerNextY = this.y + velocityY;
        return world.yFrictionAt(playerNextY);
    }

    /** Computes player's displacement in x direction */
    @Override
    protected double nextX(int dirX, World world)
    throws SlickException {
        return (Math.abs(dirX) - frictionX(dirX, world)) * dirX * speed;
    }

    /** Computes player's displacement in y direction */
    @Override
    protected double nextY(int dirY, World world)
    throws SlickException {
        return (Math.abs(dirY) - frictionY(dirY, world)) * dirY * speed;
    }

}
