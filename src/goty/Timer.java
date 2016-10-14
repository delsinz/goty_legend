package goty;

/**
 * Author: Delsin Zhang
 * Created on 10/04/2016.
 */
public final class Timer {
    private int limit;
    private int currentTime;
    private boolean triggered;
    private boolean isZero;

    public Timer(int limit){
        this.limit = limit;
        this.currentTime = limit;
        this.triggered = false;
        this.isZero = false;
    }

    /** Start timer with limit */
    public void start(){
        this.triggered = true;
    }

    /** Update timer
     * @param delta time passed in current update
     */
    public void update(int delta){
        if(triggered){
            currentTime -= delta;
        }
        if(currentTime <= 0){
            this.isZero = true;
        }
    }

    /** Reset timer to limit */
    public void reset(){
        this.currentTime = this.limit;
        this.triggered = false;
        this.isZero = false;
    }

    /** Reset timer with limit
     * @param limit in milliseconds
     */
    public void reset(int limit){
        this.limit = limit;
        this.currentTime = this.limit;
        this.triggered = false;
        this.isZero = false;
    }


    /** Return if timer reaches zero */
    public boolean isZero(){
        return this.isZero;
    }

    /** Return if timer is started */
    public boolean isTriggered(){
        return this.triggered;
    }

    /** Return limit of timer in milliseconds */
    protected int getLimit(){
        return this.limit;
    }

}
