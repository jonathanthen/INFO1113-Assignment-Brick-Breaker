package breaker.breakergame;

import processing.core.PApplet;
import processing.core.PImage;

public class Powerup {

    private PImage img;
    private int x;
    private int y;
    private int width;
    private int height;
    private int[] speed;

    public Powerup(PImage img, int x, int y, int width, int height, int[] speed) {
        this.img = img; 
    }

    public void update() {
        this.y += speed[1];
    }

    public void draw(PApplet app) {
        app.image(img, x, y, width, height);
    }

}