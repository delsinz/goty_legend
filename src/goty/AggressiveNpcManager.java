package goty;

import goty.unit.*;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

import java.util.ArrayList;

/**
 * Author: Delsin Zhang
 * Created on 10/06/2016.
 */
public final class AggressiveNpcManager {
    private static final String DATA_PATH = "data/aggressive.csv";
    private ArrayList<AggressiveNpc> aggressiveNpcs = new ArrayList<>();

    protected AggressiveNpcManager() throws SlickException {
        ArrayList<String[]> passiveNpcData = CSVReader.read(DATA_PATH);
        for(String[] data : passiveNpcData){
            switch (data[0]){
                case "zombie":
                    aggressiveNpcs.add(new Zombie(Double.parseDouble(data[1]), Double.parseDouble(data[2])));
                    break;
                case "bandit":
                    aggressiveNpcs.add(new Bandit(Double.parseDouble(data[1]), Double.parseDouble(data[2])));
                    break;
                case "skeleton":
                    aggressiveNpcs.add(new Skeleton(Double.parseDouble(data[1]), Double.parseDouble(data[2])));
                    break;
                case "draelic":
                    aggressiveNpcs.add(new Draelic(Double.parseDouble(data[1]), Double.parseDouble(data[2])));
                    break;
                default:
                    break;
            }
        }
    }

    protected void update(int attack, World world, int delta) throws SlickException{
        for(AggressiveNpc npc : aggressiveNpcs){
            npc.update(attack, world, delta);
        }
    }

    protected void render(Graphics g, Camera camera) throws SlickException{
        for(AggressiveNpc npc : aggressiveNpcs){
            npc.render(g, camera);
        }
    }
}
