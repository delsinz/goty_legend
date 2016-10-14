package goty.unit;

import goty.Camera;
import goty.RPG;
import goty.Timer;
import goty.World;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

/**
 * Author: Delsin Zhang
 * Created on 10/06/2016.
 */
public abstract class AggressiveNpc extends Unit {
    private static final double CHASE_DIST = 150;

    /* Timer controlling npc attacks */
    protected Timer attackTimer;
    /* Npc's current direction of velocity */
    private int dirX, dirY;
    /* If npc is in chase state */
    private boolean inChase;
    /* If attack is enabled for npc */
    private boolean attackEnabled;

    protected AggressiveNpc(int cooldown){
        super();
        inChase = true;
        attackEnabled = true;
        dirX = 0;
        dirY = 0;
        this.attackTimer = new Timer(cooldown);
    }

    public void update(int attack, World world, int delta) throws SlickException{
        if(this.stats.getHp() <= 0){
            this.dropDead();
        }
        if(this.isActive()){
            this.updateAttackController();
            this.updateAi(attack, world);
            this.updateVelocity(world.getPlayer());
            this.move(nextX(dirX, world, delta), nextY(dirY, world, delta));
            this.attackTimer.update(delta);
        }
    }

    @Override
    public void render(Graphics g, Camera camera) throws SlickException {
        if(this.isActive()) {
            this.getImage().drawCentered((float) (x - camera.getX()), (float) (y - camera.getY()));
            this.renderHealthBar(g, camera);
        }
    }

    /** Logic to update npc's speed and direction */
    private void updateVelocity(Unit unit){
        if(inChase){
            this.chase(unit);
        } else{
            this.stop();
        }
    }

    /** Update npc's velocity to enable chase */
    private void chase(Unit unit){
        double speedLimit = this.stats.getSpeedLimit();
        double gapX = this.x - unit.getX();
        double gapY = this.y - unit.getY();
        this.dirX = (gapX > 0)? -1:1;
        this.dirY = (gapY > 0)? -1:1;
        this.stats.speedX = speedLimit * Math.abs(gapX)/Math.sqrt(gapX*gapX + gapY*gapY);
        this.stats.speedY = speedLimit * Math.abs(gapY)/Math.sqrt(gapX*gapX + gapY*gapY);
    }

    /** Stop moving */
    private void stop(){
        this.dirX = 0;
        this.dirY = 0;
        this.stats.speedX = 0;
        this.stats.speedY = 0;
    }

    /** Dictate npc's action: attack, chase or stop */
    private void updateAi(int attack, World world){
        Player player = world.getPlayer();
        if(distTo(player)<=COLLISION_DIST){
            inChase = false;
            this.attack(player);
            if(attack == 1){
                this.stats.modifyHp(RPG.RNG.nextDouble() * -player.stats.getDamage());
            }
        } else if(distTo(player)<=CHASE_DIST){
            inChase = true;
        } else{
            inChase = false;
        }
    }

    /** Initiate attack */
    private void attack(Unit unit){
         if(attackEnabled){
             unit.stats.modifyHp(RPG.RNG.nextDouble() * -this.stats.getDamage());
             attackTimer.reset();
             attackTimer.start();
         }
    }

    /** Update npc's attackEnabled state */
    private void updateAttackController(){
        if(attackTimer.isZero() || !attackTimer.isTriggered()){
            attackEnabled = true;
            attackTimer.reset();
        }else{
            attackEnabled = false;
        }
    }
}
