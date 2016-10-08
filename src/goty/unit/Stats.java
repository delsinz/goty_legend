package goty.unit;

/**
 * Author: Delsin Zhang
 * Created on 09/24/2016.
 */
public final class Stats {
    private double maxHp, hp;
    private double damage;
    private int cooldown;
    private double speedLimit;
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

    public double getSpeedLimit(){
        return this.speedLimit;
    }

    protected void setHp(double hp){
        this.hp = hp;
    }


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
