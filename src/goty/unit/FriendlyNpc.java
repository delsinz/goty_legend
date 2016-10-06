package goty.unit;

import goty.Timer;
import goty.World;
import goty.Camera;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

/**
 * Author: Delsin Zhang
 * Created on 10/03/2016.
 */
public abstract class FriendlyNpc extends Unit {

    private static final int DIALOGUE_TIME = 4000;

    protected Timer dialogueTimer = new Timer(DIALOGUE_TIME);

    protected String activeDialogue;

    public void update(int talk, World world, int delta){
        if(distTo(world.getPlayer())<=COLLISION_DIST && talk == 1){
            onInteraction(world.getPlayer());
        }
        dialogueTimer.update(delta);
    }


    @Override
    public void render(Graphics g, Camera camera) throws SlickException{
        this.getImage().drawCentered((float)(x - camera.getX()), (float)(y - camera.getY()));
        renderHealthBar(g, camera);
        renderDialogue(g, camera);
    }

    protected void renderDialogue(Graphics g, Camera camera) throws SlickException{
        if(dialogueTimer.isTriggered() && !dialogueTimer.isZero()) {
            double screenCoordX = x - camera.getX();
            double screenCoordY = y - camera.getY();

            double textX = screenCoordX - g.getFont().getWidth(activeDialogue) / 2d;
            double textY = screenCoordY - this.getImage().getHeight() / 2d - g.getFont().getHeight(activeDialogue);

            double barWidth = g.getFont().getWidth(activeDialogue);
            double barHeight = g.getFont().getHeight(activeDialogue);
            double barX = screenCoordX - barWidth / 2d;
            double barY = screenCoordY - this.getImage().getHeight() / 2d - barHeight + BAR_HEIGHT_OFFSET;

            g.setColor(BAR_BLACK);
            g.fillRect((float) barX, (float) barY, (float) barWidth, (float) barHeight);
            g.setColor(Color.white);
            g.drawString(activeDialogue, (float) textX, (float) textY);
        }
    }

    protected abstract void onInteraction(Player player);
    protected abstract void pickDialogue(Player player);
}
