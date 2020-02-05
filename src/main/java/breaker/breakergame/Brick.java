package breaker.breakergame;

import breaker.breakergame.Powerup;
import processing.core.PApplet;
import processing.core.PImage;

public class Brick {

    private PImage img;
    public int x;
    public int y;
    public int width;
    public int height;
    public int hp;
    public Powerup p;

    public Brick(PImage img, int x, int y, int width, int height, int hp, Powerup p) {
        this.img = img;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.hp = hp;
        this.p = p;
    }

    public int getHp() {
        return this.hp;
    }
    
    public void minusHp() {
        this.hp -= 1;
    }

    public Powerup getPowerup() {
        return this.p;
    }

    public void draw(PApplet app) {
        app.image(img, x, y, width, height);
    }
    
}