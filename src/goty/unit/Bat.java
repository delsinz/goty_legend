package goty.unit;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

/**
 * Author: Delsin Zhang
 * Created on 10/04/2016.
 */
public final class Bat extends PassiveNpc {
    private static final float SCALE = 1;
    private static final double SPEED = 0.2;
    private static final double HP = 40;
    private static final double DAMAGE = 0;
    private static final int COOLDOWN = 0;
    private static final String IMAGE_PATH = ASSET_PATH + "dreadbat.png";
    private static final int DIRECTION_TIME = 3000;
    private static final int FLEE_TIME = 5000;

    public Bat(double x, double y) throws SlickException {
        super(DIRECTION_TIME, FLEE_TIME);

        this.x = x;
        this.y = y;
        this.rightImg = new Image(IMAGE_PATH).getScaledCopy(SCALE);
        this.leftImg = rightImg.getFlippedCopy(true, false);
        this.setImage(rightImg);
        name = "Giant Bat";

        double speedX = getRandomSpeed(SPEED);
        double speedY = getCounterPartSpeed(SPEED, speedX);
        stats = new Stats(HP,DAMAGE, COOLDOWN, SPEED, speedX, speedY);
    }

}
