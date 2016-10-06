package goty.unit;

import goty.ItemManager;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

/**
 * Author: Delsin Zhang
 * Created on 10/03/2016.
 */
public final class Prince extends FriendlyNpc {
    private static final float SCALE = 1;
    private static final double SPEED_X = 0;
    private static final double SPEED_Y = 0;
    private static final double HP = 1;
    private static final double DAMAGE = 0;
    private static final int COOLDOWN = 0;
    private static final String IMAGE_PATH = "assets/units/prince.png";

    public Prince(double x, double y) throws SlickException {
        this.x = x;
        this.y = y;
        this.rightImg = new Image(IMAGE_PATH).getScaledCopy(SCALE);
        this.leftImg = rightImg.getFlippedCopy(true, false);
        this.setImage(rightImg);
        stats = new Stats(HP,DAMAGE, COOLDOWN, SPEED_X, SPEED_Y);
        name = "Prince Aldric";
    }

    @Override
    public void onInteraction(Player player){
        pickDialogue(player);
        dialogueTimer.reset();
        dialogueTimer.start();
    }


    @Override
    protected void pickDialogue(Player player){
        if(!player.getInventory().contains(ItemManager.ELIXIR_ID)){
            this.activeDialogue = "Go get that elixir for me, you beautiful SOB!";
        }else{
            this.activeDialogue = "Ah, the elixir! Now go, I've got no money for you.";
        }

    }
}
