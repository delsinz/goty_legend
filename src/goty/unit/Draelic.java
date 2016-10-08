package goty.unit;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

/**
 * Author: Delsin Zhang
 * Created on 10/07/2016.
 */
public final class Draelic extends AggressiveNpc{
    private static final float SCALE = 1;
    private static final double SPEED = 0.25;
    private static final double HP = 140;
    private static final double DAMAGE = 30;
    private static final int COOLDOWN = 400;
    private static final String IMAGE_PATH = ASSET_PATH + "necromancer.png";

    public Draelic(double x, double y) throws SlickException{
        super(COOLDOWN);
        this.x = x;
        this.y = y;
        this.rightImg = new Image(IMAGE_PATH).getScaledCopy(SCALE);
        this.leftImg = rightImg.getFlippedCopy(true, false);
        this.setImage(rightImg);
        this.name = "Fluffy, the Bad";
        this.stats = new Stats(HP, DAMAGE, COOLDOWN, SPEED, 0, 0);
    }
}
