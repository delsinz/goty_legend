/* SWEN20003 Object Oriented Software Development
 * game.RPG Game Engine
 * Author: Mingyang Zhang (Delsin)
 * Login: mingyangz
 */

package characters;

import org.newdawn.slick.SlickException;
import org.newdawn.slick.Image;
import org.newdawn.slick.Graphics;

import world.*;

public abstract class Character {
    protected double x, y; // characters.Character's position relative to the map, in pixels.
    protected double speed;
    protected Image model, rightImg, leftImg;


    public abstract double getX() throws SlickException;
    public abstract double getY() throws SlickException;
    public abstract Image getModel() throws SlickException;
    public abstract void update(int dirX, int dirY, World world, int delta) throws SlickException;
    public abstract void render(Graphics g, Camera camera) throws SlickException;


    /* Helper methods used internally */
    protected abstract void move(double displaceX, double displaceY, int delta) throws SlickException;
    protected abstract int frictionX(int dirX, World world) throws SlickException;
    protected abstract int frictionY(int dirY, World world) throws SlickException;
    protected abstract double nextX(int dirX, World world) throws SlickException;
    protected abstract double nextY(int dirY, World world) throws SlickException;
}