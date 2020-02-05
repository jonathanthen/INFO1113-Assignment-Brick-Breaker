package breaker.breakergame;

import java.util.List;

import java.lang.Math;
import processing.core.PApplet;
import processing.core.PImage;

public class Ball extends Objs {

    private PImage img;
    private int[] direction;
    public int brickhit;

    public Ball(PImage img, int width, int height, int[] speed) {
        super(width, height, speed);
        this.img = img;
        this.direction = new int[] {1, 1};
        this.y = 379;
    }

    public void update() {
        this.x += this.direction[0] * (this.speed[0] + interpolation(this.x));
        this.y += this.direction[1] * (this.speed[1] + interpolation(this.y));
    }

    public void checkEdges() {
        if (this.y < 0 && this.direction[1] < 0) {
            this.direction[1] *= -1;
            addSpeedY(-1);
        } else if (this.x < 0 && this.direction[0] < 0) {
            this.direction[0] *= -1;
        } else if (this.x > 520 && this.direction[0] > 0) {
            this.direction[0] *= -1;
        }
    }

    //BALL TOUCHES PADDLE
    public boolean touch(Paddle p) {
        if (this.x + this.width > p.x && this.x < p.x + p.width
        && this.y + p.height > p.y && this.y < p.y + p.height) {
            return true;
        }
        return false;
    }
    //BALL TOUCHES MIDDLE OF PADDLE
    public boolean touchmid(Paddle p) {
        if (this.x + this.width > p.x + 14 && this.x < p.x + p.width - 14
        && this.y + p.height > p.y && this.y < p.y + p.height) {
            return true;
        }
        return false;
    }

    //BALL HIT BRICK
    public boolean hit(List<Brick> ba) {
        for (int i = 0; i < ba.size(); i++ ) {
            if (this.x +this.width > ba.get(i).x && this.x < ba.get(i).x + ba.get(i).width
            && this.y + ba.get(i).height > ba.get(i).y && this.y < ba.get(i).y + ba.get(i).height) {
                brickhit = i;
                return true;
            }
        }
        return false;
    }

    //INTERPOLATION
    public double interpolation(int val) {
        int p1 = val;
        int p2 = p1 + 4;
        int diff = Math.abs(p1 - p2);
        double d = 0.4;
        double new_point = (diff * d);
        return new_point;
    }

    // REQUIRED GETTERS
    public int getBrickHit() {
        return this.brickhit;
    }

    public int getDirectionX() {
        return this.direction[0];
    }

    public int getDirectionY() {
        return this.direction[1]; 
    }

    // REQUIRED SETTERS
    public void setDirectionX(int num) {
        this.direction[0] *= num;
    }

    public void setDirectionY(int num) {
        this.direction[1] *= num;
    }

    public void addSpeedX(int num) {
        this.speed[0] += num;
        if (this.speed[0] < 1) {
            this.speed[0] = 1;
        }
    }

    public void addSpeedY(int num) {
        this.speed[1] += num;
        if (this.speed[1] < 1) {
            this.speed[1] = 1;
        }
    }

    //LOSE CONDITION
    public boolean lose() {
        if (this.y > 399 && this.direction[1] > 0) {
            return true;
        }
        return false;
    }
    //WIN CONDITION (BALL)
    public boolean win() {
        if (this.y > 250) {
            return true;
        }
        return false;
    }
    
    public void draw(PApplet app) {
        app.image(img, x, y, width, height);
    }
    
}