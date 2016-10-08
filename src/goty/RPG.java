/* 433-294 Object Oriented Software Development
 * goty.RPG Game Engine
 * Author: Matt Giuca <mgiuca>
 * Modified by: Mingyang Zhang (Delsin)
 */

package goty;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Font;

import java.util.Random;

/** Main class for the Role-Playing Game engine.
 * Handles initialisation, input and rendering.
 */
public final class RPG extends BasicGame
{
    private static final String FONT_PATH = "assets/DejaVuSans-Bold.ttf";
    private static final float FONT_SIZE = 15;

    public static final Random RNG = new Random();
    /** Screen width, in pixels. */
    public static final int SCREEN_WIDTH = 800;
    /** Screen height, in pixels. */
    public static final int SCREEN_HEIGHT = 600;

    public static final int PANEL_HEIGHT = 70;

    private World world;
    private Timer attackTimer;
    private boolean attackEnabled;
    private Font font;
    
    /** Create a new goty.RPG object. */
    public RPG()
    {
        super("goty.RPG Game Engine");
    }

    /** Initialise the game state.
     * @param gc The Slick game container object.
     */
    @Override
    public void init(GameContainer gc)
    throws SlickException
    {
        world = new World();
        attackEnabled = true;
        attackTimer = new Timer(world.getPlayer().getStats().getCooldown());
        attackTimer.reset();
        font = FontLoader.loadFont(FONT_PATH, FONT_SIZE);
    }

    /** Update the game state for a frame.
     * @param gc The Slick game container object.
     * @param delta Time passed since last frame (milliseconds).
     */
    @Override
    public void update(GameContainer gc, int delta)
    throws SlickException
    {
        this.updateAttackController();
        // Get data about the current input (keyboard state).
        Input input = gc.getInput();

        // Update the player's movement direction based on keyboard presses.
        int dirX = 0;
        int dirY = 0;
        int attack = 0;
        int talk = 0;
        if (input.isKeyDown(Input.KEY_DOWN))
            dirY = 1;
        if (input.isKeyDown(Input.KEY_UP))
            dirY = -1;
        if (input.isKeyDown(Input.KEY_LEFT))
            dirX = -1;
        if (input.isKeyDown(Input.KEY_RIGHT))
            dirX = 1;
        if(attackEnabled && input.isKeyDown(Input.KEY_A)) {
            attack = 1;
            attackEnabled = false;
            attackTimer.reset();
            attackTimer.start();
        }
        if(input.isKeyPressed(Input.KEY_T))
            talk = 1;
        if(input.isKeyPressed(Input.KEY_ESCAPE))
            gc.exit();

        // Let goty.World.update decide what to do with this data.
        world.update(dirX, dirY, attack, talk, delta);
        this.attackTimer.update(delta);
    }

    /** Render the entire screen, so it reflects the current game state.
     * @param gc The Slick game container object.
     * @param g The Slick graphics object, used for drawing.
     */
    public void render(GameContainer gc, Graphics g)
    throws SlickException
    {
        g.setFont(font);
        world.render(g);
    }

    /** Start-up method. Creates the game and runs it.
     * @param args Command-line arguments (ignored).
     */
    public static void main(String[] args)
    throws SlickException
    {
        AppGameContainer app = new AppGameContainer(new RPG());
        // setShowFPS(true), to show frames-per-second.
        app.setShowFPS(true);
        app.setDisplayMode(SCREEN_WIDTH, SCREEN_HEIGHT, false);
        app.start();
    }

    private void updateAttackController(){
        if(world.getPlayer().getStats().getCooldown() != attackTimer.getLimit()){
            attackTimer.reset(world.getPlayer().getStats().getCooldown());
            attackEnabled = true;
        }

        if(attackTimer.isZero()){
            attackEnabled = true;
            attackTimer.reset();
        }
    }
}
