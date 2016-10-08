package goty.unit;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

/**
 * Author: Delsin Zhang
 * Created on 10/07/2016.
 */
public final class Bandit extends AggressiveNpc {
    private static final float SCALE = 1;
    private static final double SPEED = 0.25;
    private static final double HP = 40;
    private static final double DAMAGE = 8;
    private static final int COOLDOWN = 200;
    private static final String IMAGE_PATH = ASSET_PATH + "bandit.png";

    public Bandit(double x, double y) throws SlickException{
        super(COOLDOWN);
        this.x = x;
        this.y = y;
        this.rightImg = new Image(IMAGE_PATH).getScaledCopy(SCALE);
        this.leftImg = rightImg.getFlippedCopy(true, false);
        this.setImage(rightImg);
        this.name = "Bandit";
        this.stats = new Stats(HP, DAMAGE, COOLDOWN, SPEED, 0, 0);
    }
}
