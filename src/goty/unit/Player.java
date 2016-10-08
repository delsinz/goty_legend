/* SWEN20003 Object Oriented Software Development
 * goty.RPG Game Engine
 * Author: Mingyang Zhang (Delsin)
 * Login: mingyangz
 */

package goty.unit;

import goty.World;
import goty.Camera;
import org.newdawn.slick.*;

import java.util.ArrayList;


public final class Player extends Unit
{
    private static final double START_X = 756;
    private static final double START_Y = 684;
    private static final float SCALE = 0.06f;
    private static final double SPEED_X = 0.25;
    private static final double SPEED_Y= 0.25;
    private static final double HP = 100;
    private static final double DAMAGE = 26;
    private static final int COOLDOWN = 600;
    private static final String PANEL_PATH = "assets/panel.png";

    private Image panel = new Image(PANEL_PATH);
    private ArrayList<Integer> inventory;
    private Image spriteSheet;
    private Animation animation;

    public Player() throws SlickException{
        this.x = START_X;
        this.y = START_Y;
        this.rightImg = new Image(ASSET_PATH + "link-8-bit.png").getScaledCopy(SCALE);
        this.leftImg = rightImg.getFlippedCopy(true, false);
        this.setImage(this.rightImg);
        this.stats = new Stats(HP,DAMAGE, COOLDOWN, SPEED_X, SPEED_Y);
        inventory = new ArrayList<>();

        spriteSheet = new Image("assets/units/delda.png");
        animation = new Animation(false);

        // TODO: remove debugging code
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
        if(this.stats.getHp()<=0){
            this.praiseTheSun();
        }
        /* Update player's (x, y) pos */
        this.move(nextX(dirX, world, delta), nextY(dirY, world, delta));
    }

    private Animation getAnimation(Image img){
        Animation animation = new Animation(false);
        for(int x = 0; x<8; x++){
            animation.addFrame(img.getSubImage(x*120, 4*130, 120, 130), 50);
        }
        return animation;
    }

    @Override
    public void render(Graphics g, Camera camera) throws SlickException{
        //this.getImage().drawCentered((float)(x - camera.getX()), (float)(y - camera.getY()));
        this.animation.draw((float)(x - camera.getX()), (float)(y - camera.getY()));
    }

    protected void heal(){
        stats.setHp(stats.getMaxHp());
    }

    protected boolean isFullHealth(){
        return stats.getHp() == stats.getMaxHp();
    }

    public void modifyVitality(double amount){
        this.stats.modifyMaxHp(amount);
        this.stats.modifyHp(amount);
    }

    public void modifyStrength(double amount){
        this.stats.modifyDamage(amount);
    }

    public void modifyAgility(int amount){
        this.stats.modifyCooldown(amount);
    }

    private void praiseTheSun(){
        this.x = START_X;
        this.y = START_Y;
        this.heal();
    }
}
