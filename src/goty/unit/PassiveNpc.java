package goty.unit;

import goty.RPG;
import goty.Timer;
import goty.World;
import goty.Camera;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

/**
 * Author: Delsin Zhang
 * Created on 10/04/2016.
 */
public abstract class PassiveNpc extends Unit{
    private Timer dirTimer, fleeTimer;
    private int dirX, dirY;
    private boolean underAttack;
    //private boolean active;

    protected PassiveNpc(int dirTime, int fleeTime){
        underAttack = false;
        //active = true;

        dirX = getRandomDir();
        dirY = getRandomDir();

        this.dirTimer = new Timer(dirTime);
        this.fleeTimer = new Timer(fleeTime);
        dirTimer.reset();
        fleeTimer.reset();
        dirTimer.start();
    }

    public void update(int attack, World world, int delta) throws SlickException{
        if(this.stats.getHp() <= 0){
            this.dropDead();
        }
        if(this.isActive()){
            this.updateAi(attack, world);
            this.updateVelocity(world.getPlayer());
            this.move(nextX(dirX, world, delta), nextY(dirY, world, delta));
            this.dirTimer.update(delta);
            this.fleeTimer.update(delta);
        }
    }


    @Override
    public void render(Graphics g, Camera camera) throws SlickException{
        if(this.isActive()) {
            this.getImage().drawCentered((float) (x - camera.getX()), (float) (y - camera.getY()));
            this.renderHealthBar(g, camera);
        }
    }


    protected int getRandomDir(){
        int[] dirArray = new int[]{-1, 1};
        int rand = RPG.RNG.nextInt(dirArray.length);
        return dirArray[rand];
    }


    protected double getRandomSpeed(double limit){
        return (RPG.RNG.nextDouble()) * limit;
    }

    protected double getCounterPartSpeed(double limit, double speed){
        return Math.sqrt(limit * limit - speed * speed);
    }

    private void updateVelocity(Unit unit){
        if(!underAttack && dirTimer.isTriggered() && dirTimer.isZero()){
            this.wander();
        }
        if(underAttack){
            this.fleeFrom(unit);
        }
    }

    private void wander(){
        dirX = getRandomDir();
        dirY = getRandomDir();
        this.stats.speedX = getRandomSpeed(this.stats.getSpeedLimit());
        this.stats.speedY = getCounterPartSpeed(this.stats.getSpeedLimit(), this.stats.speedX);
        dirTimer.reset();
        dirTimer.start();
    }

    private void fleeFrom(Unit unit){
        double speedLimit = this.stats.getSpeedLimit();
        double gapX = this.x - unit.getX();
        double gapY = this.y - unit.getY();
        dirX = (gapX > 0)? 1:-1;
        dirY = (gapY > 0)? 1:-1;
        this.stats.speedX = speedLimit * Math.abs(gapX)/Math.sqrt(gapX*gapX + gapY*gapY);
        this.stats.speedY = speedLimit * Math.abs(gapY)/Math.sqrt(gapX*gapX + gapY*gapY);
    }

    private void onBeingAttackedBy(Unit unit){
        underAttack = true;
        dirTimer.reset();
        fleeTimer.reset();
        fleeTimer.start();
        this.stats.modifyHp(RPG.RNG.nextDouble() * -unit.stats.getDamage());
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
