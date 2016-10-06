package goty;

/**
 * Author: Delsin Zhang
 * Created on 10/04/2016.
 */
public class Timer {
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

    public void start(){
        this.triggered = true;
    }

    public void update(int delta){
        if(triggered){
            currentTime -= delta;
        }
        if(currentTime <= 0){
            this.isZero = true;
        }
    }

    public void reset(){
        this.currentTime = limit;
        this.triggered = false;
        this.isZero = false;
    }


    public boolean isZero(){
        return this.isZero;
    }

    public boolean isTriggered(){
        return this.triggered;
    }

}
