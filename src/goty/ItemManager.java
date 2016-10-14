package goty;

import goty.item.*;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import java.util.ArrayList;

/**
 * Author: Delsin Zhang
 * Created on 10/05/2016.
 */
public final class ItemManager {
    public static final int AMULET_ID = 0;
    public static final int SWORD_ID = 1;
    public static final int TOME_ID = 2;
    public static final int ELIXIR_ID = 3;
    private static final String IMAGE_PATH = "assets/items/";
    private static final String FILE_PATH = "data/item.csv";

    private ArrayList<Item> items = new ArrayList<>();

    protected ItemManager() throws SlickException{
        ArrayList<String[]> itemData = CSVReader.read(FILE_PATH);
        for(String[] data : itemData){
            switch(data[0]){
                case "amulet":
                    items.add(new Amulet(Double.parseDouble(data[1]), Double.parseDouble(data[2])));
                    break;
                case "sword":
                    items.add(new Sword(Double.parseDouble(data[1]), Double.parseDouble(data[2])));
                    break;
                case "tome":
                    items.add(new Tome(Double.parseDouble(data[1]), Double.parseDouble(data[2])));
                    break;
                case "elixir":
                    items.add(new Elixir(Double.parseDouble(data[1]), Double.parseDouble(data[2])));
                default:
                    break;
            }
        }
    }


    protected void update(World world,int delta) throws SlickException {
        for(Item item : items){
            item.update(world, delta);
        }
    }

    protected void render(Graphics g, Camera camera) throws SlickException{
        for(Item npc : items){
            npc.render(g, camera);
        }
    }

    /**
     * Get image of an item, given item id
     */
    protected Image getItemImage(int itemId) throws SlickException{
        Image itemImage = null;
        switch (itemId){
            case AMULET_ID:
                itemImage =  new Image(IMAGE_PATH + "amulet.png");
                break;
            case SWORD_ID:
                itemImage =  new Image(IMAGE_PATH + "sword.png");
                break;
            case TOME_ID:
                itemImage =  new Image(IMAGE_PATH + "tome.png");
                break;
            case ELIXIR_ID:
                itemImage =  new Image(IMAGE_PATH + "elixir.png");
            default:
                 break;
        }
        return itemImage;
    }
}
