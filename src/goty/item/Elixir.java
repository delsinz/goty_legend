package goty.item;

import goty.ItemManager;
import goty.unit.Player;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

/**
 * Author: Delsin Zhang
 * Created on 10/05/2016.
 */
public class Elixir extends Item {
    private static final String IMAGE_PATH = "assets/items/elixir.png";
    private static final float SCALE = 1;

    public Elixir(double x, double y) throws SlickException{
        this.x = 700;
        this.y = 590;
        this.setImage(new Image(IMAGE_PATH).getScaledCopy(SCALE));
        name = "Elixir of Life";
        isPickedUp = false;
        id = ItemManager.ELIXIR_ID;
    }

    @Override
    protected void takeEffect(Player player){
        // No effect on player
    }
}
