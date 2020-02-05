package breaker.breakergame;

import processing.core.PApplet;
import processing.core.PImage;

public abstract class Objs {

    protected int x;
    protected int y;
    protected int width;
    protected int height;
    protected int[] speed;

    public Objs(int width, int height, int[] speed) { 
        this.x = 260;
        this.y = 380;
        this.width = width;
        this.height = height;
        this.speed = speed;
    }

    public abstract void draw(PApplet app);

}