package goty;

import goty.unit.Bat;
import goty.unit.PassiveNpc;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

import java.util.ArrayList;

/**
 * Author: Delsin Zhang
 * Created on 10/04/2016.
 */
public final class PassiveNpcManager {
    private static final String FILE_PATH = "data/passive.csv";
    private ArrayList<PassiveNpc> passiveNpcs = new ArrayList<>();

    protected PassiveNpcManager() throws SlickException{
        ArrayList<String[]> passiveNpcData = CSVReader.read(FILE_PATH);
        for(String[] data : passiveNpcData){
            switch (data[0]){
                case "bat":
                    passiveNpcs.add(new Bat(Double.parseDouble(data[1]), Double.parseDouble(data[2])));
                    break;
                default:
                    break;
            }
        }
    }

    protected void update(int attack, World world, int delta) throws SlickException{
        for(PassiveNpc npc : passiveNpcs){
            npc.update(attack, world, delta);
        }
    }

    protected void render(Graphics g, Camera camera) throws SlickException{
        for(PassiveNpc npc : passiveNpcs){
            npc.render(g, camera);
        }
    }

}
