package goty.item;

import goty.ItemManager;
import goty.unit.Player;
import goty.unit.Unit;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

/**
 * Author: Delsin Zhang
 * Created on 10/05/2016.
 */
public class Amulet extends Item {
    private static final String IMAGE_PATH = "assets/items/amulet.png";
    private static final float SCALE = 1;
    private static final double MODIFIER = 80;

    public Amulet(double x, double y) throws SlickException{
        this.x = 738;
        this.y = 590;
        this.setImage(new Image(IMAGE_PATH).getScaledCopy(SCALE));
        name = "Amulet of Vitality";
        isPickedUp = false;
        id = ItemManager.AMULET_ID;
    }

    @Override
    protected void takeEffect(Player player){
        player.modifyHp(MODIFIER);
    }
}