package goty.item;

import goty.ItemManager;
import goty.unit.Player;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

/**
 * Author: Delsin Zhang
 * Created on 10/05/2016.
 */
public final class Sword extends Item {
    private static final String IMAGE_PATH = "assets/items/sword.png";
    private static final float SCALE = 1;
    private static final double MODIFIER = 30;

    public Sword(double x, double y) throws SlickException{
        super(ItemManager.SWORD_ID);
        this.x = x;
        this.y = y;
        this.setImage(new Image(IMAGE_PATH).getScaledCopy(SCALE));
        name = "Sword of Strength";
    }

    @Override
    protected void takeEffect(Player player){
        player.modifyStrength(MODIFIER);
    }
}
