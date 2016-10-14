package goty.unit;

/**
 * Author: Delsin Zhang
 * Created on 09/24/2016.
 */
public final class Stats {
    /* Max hp and current hp*/
    private double maxHp, hp;
    /* Max damage unit can deal */
    private double damage;
    /* Attack cooldown */
    private int cooldown;
    /* Unit's speed limit, not applied for Player object */
    private double speedLimit;
    /* Current speed in x, y direction */
    protected double speedX, speedY;

    protected Stats(double maxHp, double damage, int cooldown, double speedX, double speedY){
        this.maxHp = maxHp;
        this.hp = maxHp;
        this.damage = damage;
        this.cooldown = cooldown;
        this.speedX = speedX;
        this.speedY = speedY;
    }

    protected Stats(double maxHp, double damage, int cooldown, double speedLimit, double speedX, double speedY){
        this.maxHp = maxHp;
        this.hp = maxHp;
        this.damage = damage;
        this.cooldown = cooldown;
        this.speedX = speedX;
        this.speedY = speedY;
        this.speedLimit = speedLimit;
    }

    public double getMaxHp(){
        return this.maxHp;
    }

    public double getHp(){
        return this.hp;
    }

    public double getDamage(){
        return this.damage;
    }

    public int getCooldown(){
        return this.cooldown;
    }

    protected double getSpeedLimit(){
        return this.speedLimit;
    }

    protected void setHp(double hp){
        this.hp = hp;
    }



    /* Modify the corresponding stat by given amount */

    protected void modifyMaxHp(double amount){
        this.maxHp += amount;
        this.maxHp = Math.max(maxHp, 0);
    }

    protected void modifyDamage(double amount){
        this.damage += amount;
    }

    protected void modifyCooldown(int amount){
        this.cooldown += amount;
    }

    protected void modifyHp(double amount){
        this.hp += amount;
        this.hp = Math.max(this.hp, 0);
        this.hp = Math.min(this.hp, this.maxHp);
    }
}
