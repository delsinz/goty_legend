package goty.unit;


import goty.Camera;
import goty.Entity;
import goty.World;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

/**
 * Author: Delsin Zhang
 * Created on 09/24/2016.
 */
public abstract class Unit extends Entity{
    private static final double MAX_BAR_LENGTH = 70;
    protected static final double BAR_HEIGHT_OFFSET = 2.5;
    protected static final Color BAR_BLACK = new Color(0.0f, 0.0f, 0.0f, 0.8f);
    protected static final Color BAR_RED = new Color(0.8f, 0.0f, 0.0f, 0.8f);

    protected Image leftImg, rightImg;
    //private double speedX, speedY;
    protected Stats stats;



    private int frictionX(int dirX, World world, int delta) throws SlickException{
        double displaceX = dirX * stats.speedX * delta;
        double nextX = super.x + displaceX;
        return world.xFrictionAt(this, nextX);
    }


    private int frictionY(int dirY, World world, int delta) throws SlickException{
        double displaceY = dirY * stats.speedY * delta;
        double nextY = super.y + displaceY;
        return world.yFrictionAt(this, nextY);
    }


    protected double nextX(int dirX, World world, int delta) throws SlickException{
        return (Math.abs(dirX) - frictionX(dirX, world, delta)) * dirX * stats.speedX * delta;
    }


    protected double nextY(int dirY, World world, int delta) throws SlickException{
        return (Math.abs(dirY) - frictionY(dirY, world, delta)) * dirY * stats.speedY * delta;
    }

    protected void move(double displaceX, double displaceY){
        if(displaceX > 0){
            super.setImage(rightImg);
        } else if(displaceX < 0){
            super.setImage(leftImg);
        }
        super.x += displaceX;
        super.y += displaceY;
    }

    protected void renderHealthBar(Graphics g, Camera camera) throws SlickException{
        double screenCoordX = x - camera.getX();
        double screenCoordY = y - camera.getY();

        double textX = screenCoordX - g.getFont().getWidth(name)/2d;
        double textY = screenCoordY - this.getImage().getHeight()/2d - g.getFont().getHeight(name);

        double barWidth = Math.max(g.getFont().getWidth(name) + 6, MAX_BAR_LENGTH);
        double barHeight = g.getFont().getHeight(name);
        double hpBarWidth = barWidth * stats.getHp()/stats.getMaxHp();
        double barX = screenCoordX - barWidth/2d;
        double barY = screenCoordY - this.getImage().getHeight()/2d - barHeight + BAR_HEIGHT_OFFSET;

        g.setColor(BAR_BLACK);
        g.fillRect((float)barX, (float)barY, (float)barWidth, (float)barHeight);
        g.setColor(BAR_RED);
        g.fillRect((float)barX, (float)barY, (float)hpBarWidth, (float)barHeight);
        g.setColor(Color.white);
        g.drawString(name, (float)textX, (float)textY);
    }

}
