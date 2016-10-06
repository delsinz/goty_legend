package goty.unit;

import goty.Timer;
import goty.World;
import goty.Camera;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import java.util.Random;

/**
 * Author: Delsin Zhang
 * Created on 10/04/2016.
 */
public abstract class PassiveNpc extends Unit{
    protected Timer dirTimer, fleeTimer;
    protected int dirX, dirY;
    protected boolean underAttack;

    public void update(int attack, World world, int delta) throws SlickException{
        this.updateAi(attack, world);
        this.updateVelocity(world.getPlayer());
        this.move(nextX(dirX, world, delta), nextY(dirY, world, delta));
        this.dirTimer.update(delta);
        this.fleeTimer.update(delta);
    }


    @Override
    public void render(Graphics g, Camera camera) throws SlickException{
        this.getImage().drawCentered((float)(x - camera.getX()), (float)(y - camera.getY()));
        this.renderHealthBar(g, camera);
    }


    protected int getRandomDir(){
        int[] dirArray = new int[]{-1, 1};
        int rand = new Random().nextInt(dirArray.length);
        return dirArray[rand];
    }


    protected double getRandomSpeed(double limit){
        return new Random().nextDouble() * limit;
    }

    protected double getCounterPartSpeed(double limit, double speed){
        return Math.sqrt(limit * limit - speed * speed);
    }

    private void updateVelocity(Player player){
        if(!underAttack && dirTimer.isTriggered() && dirTimer.isZero()){
            this.wander();
        }
        if(underAttack){
            this.fleeFrom(player);
        }
    }

    private void wander(){
        dirX = getRandomDir();
        dirY = getRandomDir();
        this.stats.speedX = getRandomSpeed(this.stats.getSpeedLimit());
        this.stats.speedY = getCounterPartSpeed(this.stats.getSpeedLimit(), this.stats.speedX);
        name = underAttack + "";
        dirTimer.reset();
        dirTimer.start();
        name = underAttack+", "+fleeTimer.isZero();
    }

    private void fleeFrom(Unit unit){
        double speedLimit = this.stats.getSpeedLimit();
        double gapX = this.x - unit.getX();
        double gapY = this.y - unit.getY();
        dirX = (gapX > 0) ? 1 : -1;
        dirY = (gapY > 0) ? 1 : -1;
        this.stats.speedX = speedLimit * Math.abs(gapX)/Math.sqrt(gapX*gapX + gapY*gapY);
        this.stats.speedY = speedLimit * Math.abs(gapY)/Math.sqrt(gapX*gapX + gapY*gapY);
        name = underAttack+", "+fleeTimer.isZero();
    }

    private void onBeingAttackedBy(Unit unit){
        underAttack = true;
        dirTimer.reset();
        fleeTimer.reset();
        fleeTimer.start();
        this.stats.modifyHp(new Random().nextDouble() * -unit.stats.getDamage());
    }

    private void onFleeTimeout(){
        underAttack = false;
        fleeTimer.reset();
        dirTimer.reset();
        dirTimer.start();
    }

    private void updateAi(int attack, World world){
        if(distTo(world.getPlayer())<=COLLISION_DIST && attack == 1){
            this.onBeingAttackedBy(world.getPlayer());
        }
        if(fleeTimer.isZero()){
            this.onFleeTimeout();
        }
    }
}
