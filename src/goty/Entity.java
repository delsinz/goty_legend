package goty;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

/**
 * Author: Delsin Zhang
 * Created on 09/24/2016.
 */
public abstract class Entity {
    public static final double COLLISION_DIST = 50;

    // World coordinate of entity
    protected double x, y;
    // Image of entity
    private Image image;
    // Display name of entity
    protected String name;

    protected Image getImage(){
        return this.image;
    }

    protected void setImage(Image image){
        this.image = image;
    }

    public double getX(){
        return this.x;
    }

    public double getY(){
        return this.y;
    }

    public abstract void render(Graphics g, Camera camera) throws SlickException;

    /**
     * Compute distance to a given entity
     */
    protected double distTo(Entity entity){
        return Math.sqrt(Math.pow((this.x - entity.x), 2) + Math.pow((this.y - entity.y), 2));
    }

}
