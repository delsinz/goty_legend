package goty.unit;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

/**
 * Author: Delsin Zhang
 * Created on 10/06/2016.
 */
public final class Zombie extends AggressiveNpc {
    private static final float SCALE = 1;
    private static final double SPEED = 0.25;
    private static final double HP = 60;
    private static final double DAMAGE = 10;
    private static final int COOLDOWN = 800;
    private static final String IMAGE_PATH = ASSET_PATH + "zombie.png";

    public Zombie(double x, double y) throws SlickException{
        super(COOLDOWN);

        this.x = x;
        this.y = y;
        this.rightImg = new Image(IMAGE_PATH).getScaledCopy(SCALE);
        this.leftImg = rightImg.getFlippedCopy(true, false);
        this.setImage(rightImg);
        this.name = "Zombie";

        this.stats = new Stats(HP, DAMAGE, COOLDOWN, SPEED, 0, 0);
    }
}
