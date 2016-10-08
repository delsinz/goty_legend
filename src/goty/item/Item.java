package goty.item;

import goty.Camera;
import goty.Entity;
import goty.World;
import goty.unit.Player;
import goty.unit.Unit;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

/**
 * Author: Delsin Zhang
 * Created on 09/24/2016.
 */
public abstract class Item extends Entity{
    private boolean isPickedUp;
    private int id;

    protected Item(int id){
        isPickedUp = false;
        this.id = id;
    }

    public void render(Graphics g, Camera camera) throws SlickException{
        if(!isPickedUp){
            this.getImage().drawCentered((float)(x - camera.getX()), (float)(y - camera.getY()));
        }
    }

    public void update(World world, int delta){
        if(!isPickedUp && this.distTo(world.getPlayer())<=COLLISION_DIST){
            isPickedUp = true;
            world.getPlayer().getInventory().add(this.id);
            this.takeEffect(world.getPlayer());
        }
    }

    protected abstract void takeEffect(Player player);


}
