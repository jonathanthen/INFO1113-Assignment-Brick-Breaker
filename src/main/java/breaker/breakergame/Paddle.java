package breaker.breakergame;

import processing.core.PApplet;
import processing.core.PImage;

public class Paddle extends Objs {

    private PImage img;
    public boolean isMovingLeft;
    public boolean isMovingRight;

    public Paddle(PImage img, int width, int height, int[] speed) {
        super(width, height, speed);
        this.img = img;
        this.x = this.x - (this.width/2);
        this.isMovingLeft = false;
        this.isMovingRight = false;
    }
    
    public void moveRight() {
        this.x += speed[0];
    }

    public void moveLeft() {
        this.x -= speed[0];
    }

    public void update() {
        if (this.isMovingRight) {
            moveRight();
        } else if (this.isMovingLeft) {
            moveLeft();
        }
    }

    public void checkEdges() {
        if (this.x < 0) {
            this.x = 0;
        } else if (this.x > 520 - this.width) {
            this.x = 520 - this.width;
        }
    }
    
    public void draw(PApplet app) {
        app.image(img, x, y, width, height);
    }

}