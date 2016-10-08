package goty.item;

import goty.ItemManager;
import goty.unit.Player;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

/**
 * Author: Delsin Zhang
 * Created on 10/05/2016.
 */
public final class Tome extends Item {
    private static final String IMAGE_PATH = "assets/items/tome.png";
    private static final float SCALE = 1;
    private static final int MODIFIER = -300;

    public Tome(double x, double y) throws SlickException{
        super(ItemManager.TOME_ID);
        this.x = 780;
        this.y = 590;
        this.setImage(new Image(IMAGE_PATH).getScaledCopy(SCALE));
        name = "Tome of Agility";
    }

    @Override
    protected void takeEffect(Player player){
        player.modifyAgility(MODIFIER);
    }
}
