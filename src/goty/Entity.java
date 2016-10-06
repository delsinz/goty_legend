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

    protected double x, y;
    private Image image;
    protected String name;

    protected Image getImage(){
        return this.image;
    }

    protected void setImage(Image image){
        this.image = image;
    }

//    protected void render(Graphics g, Camera camera) throws SlickException{
//        this.image.drawCentered((float)(x - camera.getX()), (float)(y - camera.getY()));
//    }

    public abstract void render(Graphics g, Camera camera) throws SlickException;

    protected double distTo(Entity entity){
        return Math.sqrt(Math.pow((this.x - entity.x), 2) + Math.pow((this.y - entity.y), 2));
    }

    public double getX(){
        return this.x;
    }

    public double getY(){
        return this.y;
    }

}
