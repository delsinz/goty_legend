package goty.unit;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

/**
 * Author: Delsin Zhang
 * Created on 10/03/2016.
 */
public final class Healer extends FriendlyNpc {
    private static final float SCALE = 1;
    private static final double SPEED_X = 0;
    private static final double SPEED_Y = 0;
    private static final double HP = 1;
    private static final double DAMAGE = 0;
    private static final int COOLDOWN = 0;
    private static final String IMAGE_PATH = "assets/units/shaman.png";

    public Healer(double x, double y) throws SlickException{
        this.x = x;
        this.y = y;
        this.rightImg = new Image(IMAGE_PATH).getScaledCopy(SCALE);
        this.leftImg = rightImg.getFlippedCopy(true, false);
        this.setImage(rightImg);
        stats = new Stats(HP,DAMAGE, COOLDOWN, SPEED_X, SPEED_Y);
        name = "Elvira";
    }

    @Override
    protected void onInteraction(Player player){
        pickDialogue(player);
        dialogueTimer.reset();
        dialogueTimer.start();
        player.heal();
    }

    @Override
    protected void pickDialogue(Player player){
        if(player.isFullHealth()){
            this.activeDialogue = "Go kick some evil ass.";
        } else{
            this.activeDialogue = "Ah, you are much healthier now. That'll be $5. Cash only.";
        }
    }

}
