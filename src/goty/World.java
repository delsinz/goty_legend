/* SWEN20003 Object Oriented Software Development
 * goty.RPG Game Engine
 * Author: Mingyang Zhang (Delsin)
 * Login: mingyangz
 */

package goty;

import goty.unit.*;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.tiled.TiledMap;
import org.newdawn.slick.Color;

/**
 * Represents the entire game world.
 * (Designed to be instantiated just once for the whole game).
 */
public final class World {
    private TiledMap map;

    private Player player = new Player();

    private FriendlyNpcManager friendlyNpcManager = new FriendlyNpcManager();

    private PassiveNpcManager passiveNpcManager = new PassiveNpcManager();

    private AggressiveNpcManager aggressiveNpcManager = new AggressiveNpcManager();

    private ItemManager itemManager = new ItemManager();

    private Camera camera = new Camera(player.x, player.y, RPG.SCREEN_WIDTH, RPG.SCREEN_HEIGHT - RPG.PANEL_HEIGHT);

    /**
     * Create a new goty.World object.
     */
    public World()
    throws SlickException
    {
        map = new TiledMap("assets/map.tmx", "assets/");
        /* Set map width/height for camera here because can't get them before map is initialized */
        this.camera.setMapWidth(map.getWidth() * map.getTileWidth());
        this.camera.setMapHeight(map.getHeight() * map.getTileHeight());
    }

    public Player getPlayer(){
        return this.player;
    }

    /**
     * Update the game state for a frame
     */
    public void update(int dirX, int dirY, int attack, int talk, int delta)
    throws SlickException
    {
        player.update(dirX, dirY, this, delta);
        friendlyNpcManager.update(talk, this, delta);
        passiveNpcManager.update(attack, this, delta);
        aggressiveNpcManager.update(attack, this, delta);
        itemManager.update(this, delta);
        camera.update(player.x, player.y, delta);
    }

    /**
     * Render the entire screen, so it reflects the current game state.
     *
     * @param g The Slick graphics object, used for drawing.
     */
    public void render(Graphics g)
    throws SlickException
    {
        renderMap(g);
        friendlyNpcManager.render(g, camera);
        passiveNpcManager.render(g, camera);
        aggressiveNpcManager.render(g, camera);
        itemManager.render(g, camera);
        player.render(g, camera);
        renderPanel(g);
        displayDebugInfo(g);
    }

    private void renderMap(Graphics g)
    throws SlickException
    {
        /* Starting position for rendering, relative to map, in tiles */
        int tileX = (int) Math.floor((camera.getX() + 1) / map.getTileWidth());
        int tileY = (int) Math.floor((camera.getY() + 1) / map.getTileHeight());
        /* Starting position for rendering, relative to screen, in pixels */
        double pixelX = camera.getX() - toTileX(camera.getX()) * map.getTileWidth();
        double pixelY = camera.getY() - toTileY(camera.getY()) * map.getTileHeight();
        /* Size of the rendered section, in tiles */
        int renderWidth = (int) Math.floor(camera.getWidth() / map.getTileWidth()) + 2;
        int renderHeight = (int) Math.floor(camera.getHeight() / map.getTileHeight()) + 2;
        map.render((int) -pixelX, (int) -pixelY, tileX, tileY, renderWidth, renderHeight);
    }

    /**
     * Takes x pos in pixels, returns x pos in tiles
     */
    private int toTileX(double pixelX)
    throws SlickException
    {
        return (int) Math.floor((pixelX + 1) / map.getTileWidth());
    }

    /**
     * Takes y pos in pixels, returns y pos in tiles
     */
    private int toTileY(double pixelY)
    throws SlickException
    {
        return (int) Math.floor((pixelY + 1) / map.getTileHeight());
    }

    /**
     * Get friction at (x, playerY)
     * i.e. whether (x, playerY) blocks player
     */
    public int xFrictionAt(Unit unit, double x)
    throws SlickException
    {
        if (x < 0 || x > map.getWidth() * map.getTileWidth() - 2) {
            return 1;
        }

        if (map.getTileProperty(map.getTileId(toTileX(x), toTileY(unit.y), 0), "block", "0").equals("1")) {
            return 1;
        } else {
            return 0;
        }
    }

    /**
     * Get friction at (unitX, y)
     * i.e. whether (unitX, y) blocks unit
     */
    public int yFrictionAt(Unit unit, double y)
    throws SlickException
    {
        if (y < 0 || y > map.getHeight() * map.getTileHeight() - 2) { // Don't know why, but -1 has issues.
            return 1;
        }

        if (map.getTileProperty(map.getTileId(toTileX(unit.x), toTileY(y), 0), "block", "0").equals("1")) {
            return 1;
        } else {
            return 0;
        }
    }

    private void displayDebugInfo(Graphics g)
    throws SlickException
    {
        g.setColor(Color.white);
        g.drawString("blocking: " + map.getTileProperty(map.getTileId(toTileX(player.x), toTileY(player.y), 0), "block", "0"), 50, 50);
        g.drawString("x: " + player.x + ", y: " + player.y, 50, 65);
        g.drawString("tileX: " + toTileX(player.x) + ", tileY: " + toTileY(player.y), 50, 80);
        player.getImage().draw(50, 95);
        String items = "";
        for(Integer itemId : player.getInventory()){
            items += (itemId+", ");
        }
        g.drawString(items, 50, 150);

    }

    /** Renders the player's status panel. Provided code.
     * @param g The current Slick graphics context.
     */
    public void renderPanel(Graphics g) throws SlickException
    {
        // Panel colours
        Color LABEL = new Color(0.9f, 0.9f, 0.4f);          // Gold
        Color VALUE = new Color(1.0f, 1.0f, 1.0f);          // White
        Color BAR_BG = new Color(0.0f, 0.0f, 0.0f, 0.8f);   // Black, transp
        Color BAR = new Color(0.8f, 0.0f, 0.0f, 0.8f);      // Red, transp

        // Variables for layout
        String text;                // Text to display
        int text_x, text_y;         // Coordinates to draw text
        int bar_x, bar_y;           // Coordinates to draw rectangles
        int bar_width, bar_height;  // Size of rectangle to draw
        int hp_bar_width;           // Size of red (HP) rectangle
        int inv_x, inv_y;           // Coordinates to draw inventory item

        float health_percent;       // Player's health, as a percentage

        // Panel background image
        player.getPanel().draw(0, RPG.SCREEN_HEIGHT - RPG.PANEL_HEIGHT);

        // Display the player's health
        text_x = 15;
        text_y = RPG.SCREEN_HEIGHT - RPG.PANEL_HEIGHT + 25;
        g.setColor(LABEL);
        g.drawString("Health:", text_x, text_y);
        text = (int)player.getStats().getHp() + "/" + (int)player.getStats().getMaxHp();

        bar_x = 90;
        bar_y = RPG.SCREEN_HEIGHT - RPG.PANEL_HEIGHT + 20;
        bar_width = 90;
        bar_height = 30;
        health_percent = (float)(player.getStats().getHp()/player.getStats().getMaxHp());
        hp_bar_width = (int) (bar_width * health_percent);
        text_x = bar_x + (bar_width - g.getFont().getWidth(text)) / 2;
        g.setColor(BAR_BG);
        g.fillRect(bar_x, bar_y, bar_width, bar_height);
        g.setColor(BAR);
        g.fillRect(bar_x, bar_y, hp_bar_width, bar_height);
        g.setColor(VALUE);
        g.drawString(text, text_x, text_y);

        // Display the player's damage and cooldown
        text_x = 200;
        g.setColor(LABEL);
        g.drawString("Damage:", text_x, text_y);
        text_x += 80;
        text = (int)(player.getStats().getDamage())+"";
        g.setColor(VALUE);
        g.drawString(text, text_x, text_y);
        text_x += 40;
        g.setColor(LABEL);
        g.drawString("Rate:", text_x, text_y);
        text_x += 55;
        text = player.getStats().getCooldown()+"";
        g.setColor(VALUE);
        g.drawString(text, text_x, text_y);

        // Display the player's inventory
        g.setColor(LABEL);
        g.drawString("Items:", 420, text_y);
        bar_x = 490;
        bar_y = RPG.SCREEN_HEIGHT - RPG.PANEL_HEIGHT + 10;
        bar_width = 288;
        bar_height = bar_height + 20;
        g.setColor(BAR_BG);
        g.fillRect(bar_x, bar_y, bar_width, bar_height);

        inv_x = 490;
        inv_y = RPG.SCREEN_HEIGHT - RPG.PANEL_HEIGHT
                + ((RPG.PANEL_HEIGHT - 72) / 2);
        // for (each item in the player's inventory)
        for(int itemId : player.getInventory()) {
            // Render the item to (inv_x, inv_y)
            itemManager.getItemImage(itemId).draw(inv_x, inv_y);
            inv_x += 72;
        }
    }
}

