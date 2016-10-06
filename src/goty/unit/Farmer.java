package goty.unit;

import goty.ItemManager;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

/**
 * Author: Delsin Zhang
 * Created on 10/03/2016.
 */
public final class Farmer extends FriendlyNpc{
    private static final float SCALE = 1;
    private static final double SPEED_X = 0;
    private static final double SPEED_Y = 0;
    private static final double HP = 1;
    private static final double DAMAGE = 0;
    private static final int COOLDOWN = 0;
    private static final String IMAGE_PATH = "assets/units/peasant.png";

    public Farmer(double x, double y) throws SlickException {
        this.x = x;
        this.y = y;
        this.rightImg = new Image(IMAGE_PATH).getScaledCopy(SCALE);
        this.leftImg = rightImg.getFlippedCopy(true, false);
        this.setImage(rightImg);
        stats = new Stats(HP,DAMAGE, COOLDOWN, SPEED_X, SPEED_Y);
        name = "Garth";
    }

    @Override
    public void onInteraction(Player player){
        pickDialogue(player);
        dialogueTimer.reset();
        dialogueTimer.start();
    }

    @Override
    protected void pickDialogue(Player player){
        if(!player.getInventory().contains(ItemManager.AMULET_ID)){
            this.activeDialogue = "Across the river to the west, you will find the Amulet of Vitality.";
        } else if(!player.getInventory().contains(ItemManager.SWORD_ID)){
            this.activeDialogue = "Across the river and back, on the east, you will find a sword.";
        } else if(!player.getInventory().contains(ItemManager.TOME_ID)){
            this.activeDialogue = "Go get the tome of whatever in the land of blahblahblah.";
        } else{
            this.activeDialogue = "Yup, you've got all the gears. Now go kick their asses.";
        }
    }
}
