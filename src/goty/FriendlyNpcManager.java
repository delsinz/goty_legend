package goty;

import goty.unit.*;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

import java.util.ArrayList;

/**
 * Author: Delsin Zhang
 * Created on 10/03/2016.
 */
public final class FriendlyNpcManager {

    private static final String FILE_PATH = "data/friendly.csv";
    private ArrayList<FriendlyNpc> friendlyNpcs = new ArrayList<>();

    protected FriendlyNpcManager() throws SlickException{
        ArrayList<String[]> friendlyNpcData = CSVReader.read(FILE_PATH);
        for(String[] data : friendlyNpcData){
            switch(data[0]){
                case "prince":
                    friendlyNpcs.add(new Prince(Double.parseDouble(data[1]), Double.parseDouble(data[2])));
                    break;
                case "healer":
                    friendlyNpcs.add(new Healer(Double.parseDouble(data[1]), Double.parseDouble(data[2])));
                    break;
                case "farmer":
                    friendlyNpcs.add(new Farmer(Double.parseDouble(data[1]), Double.parseDouble(data[2])));
                    break;
                default:
                    break;
            }
        }
    }

    protected void update(int talk, World world,int delta) throws SlickException{
        for(FriendlyNpc npc : friendlyNpcs){
            npc.update(talk, world, delta);
        }
    }

    protected void render(Graphics g, Camera camera) throws SlickException{
        for(FriendlyNpc npc : friendlyNpcs){
            npc.render(g, camera);
        }
    }

}
