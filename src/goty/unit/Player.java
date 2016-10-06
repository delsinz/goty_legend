/* SWEN20003 Object Oriented Software Development
 * goty.RPG Game Engine
 * Author: Mingyang Zhang (Delsin)
 * Login: mingyangz
 */

package goty.unit;

import goty.World;
import goty.Camera;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Image;
import org.newdawn.slick.Graphics;

import java.util.ArrayList;


public final class Player extends Unit
{
    private static final double START_X = 756;
    private static final double START_Y = 684;
    private static final float SCALE = 0.07f;
    private static final double SPEED_X = 0.25;
    private static final double SPEED_Y= 0.25;
    private static final double HP = 100;
    private static final double DAMAGE = 26;
    private static final int COOLDOWN = 600;
    private static final String IMAGE_PATH = "assets/units/link-8-bit.png";
    private static final String PANEL_PATH = "assets/panel.png";

    private Image panel = new Image(PANEL_PATH);
    private ArrayList<Integer> inventory;

    public Player() throws SlickException{
        this.x = START_X;
        this.y = START_Y;
        this.rightImg = new Image(IMAGE_PATH).getScaledCopy(SCALE);
        this.leftImg = rightImg.getFlippedCopy(true, false);
        this.setImage(this.rightImg);
        this.stats = new Stats(HP,DAMAGE, COOLDOWN, SPEED_X, SPEED_Y);
        inventory = new ArrayList<>();

        /* for debugging */
        this.stats.setHp(50);
    }

    public Image getPanel(){
        return this.panel;
    }

    public Stats getStats(){
        return this.stats;
    }

    public ArrayList<Integer> getInventory(){
        return this.inventory;
    }

    public void update(int dirX, int dirY, World world, int delta) throws SlickException
    {
        /* Update player's (x, y) pos */
        this.move(nextX(dirX, world, delta), nextY(dirY, world, delta));
    }

    @Override
    public void render(Graphics g, Camera camera) throws SlickException{
        this.getImage().drawCentered((float)(x - camera.getX()), (float)(y - camera.getY()));
    }

    protected void heal(){
        stats.setHp(stats.getMaxHp());
    }

    protected boolean isFullHealth(){
        return stats.getHp() == stats.getMaxHp();
    }

    public void modifyHp(double amount){
        this.stats.modifyMaxHp(amount);
        this.stats.modifyHp(amount);
    }

    public void modifyDamage(double amount){
        this.stats.modifyDamage(amount);
    }

    public void modifyCooldown(int amount){
        this.stats.modifyCooldown(amount);
    }
}
