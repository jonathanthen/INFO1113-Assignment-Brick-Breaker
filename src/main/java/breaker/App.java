package breaker;

import java.util.List;
import java.util.ArrayList;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.Iterator;

import breaker.breakergame.Paddle;
import breaker.breakergame.Ball;
import breaker.breakergame.Brick;
import breaker.breakergame.Objs;
import breaker.breakergame.Powerup;
import breaker.breakergame.Game;
import processing.core.PApplet;
import processing.core.PImage;
import processing.core.PFont;

public class App extends PApplet {

    List<Brick> bricks;
    String levelName;
    String nextLevel;
    Paddle paddle;
    Paddle paddle2;
    Ball ball;
    Game game;
    PImage loseImg;
    PImage winImg;
    PFont font;

    public App() {
        nextLevel = "Enter File"; }
    public void settings() { size(520, 400); }
    public void parseLevel() {
        //ADDING BRICKS FROM JSON FILE
        JSONParser parser = new JSONParser();
        if (nextLevel.equals("Enter File")) {
            nextLevel = "level1"; // REPLACE FIRST FILENAME HERE WITHOUT .JSON
        }
        try (Reader reader = new FileReader(nextLevel + ".json")) {
            JSONObject jsonObject = (JSONObject) parser.parse(reader);
            nextLevel = (String) jsonObject.get("next_level");
            levelName = (String) jsonObject.get("name");
            // LOOP JSONARRAY
            JSONArray brickArray = (JSONArray) jsonObject.get("bricks");
            for (Object o : brickArray) {
                JSONObject bri = (JSONObject) o;
                int x = (int) (long) bri.get("x");
                int y = (int) (long) bri.get("y");
                String id = (String) bri.get("id");
                int hp = (int) (long) bri.get("hp");
                String pu = (String) bri.get("powerup");
                if (pu == null) {
                    bricks.add(new Brick(loadImage(id + ".png"), x, y, 20, 10, hp, null));
                } else if (pu.equals("multiball")) {
                    bricks.add(new Brick(loadImage(id + ".png"), x, y, 20, 10, hp, new Powerup(loadImage("ball_powerup.png"), x, y, 16, 16, new int[] {0, 1})));
                } else if (pu.equals("paddleup")) {
                    bricks.add(new Brick(loadImage(id + ".png"), x, y, 20, 10, hp, new Powerup(loadImage("paddle_powerup.png"), x, y, 16, 16, new int[] {0, 1})));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
    public void setup() {
        bricks = new ArrayList<Brick>();
        game = new Game();
        frameRate(60);
        loseImg = loadImage("lose.jpg"); // https://www.shutterstock.com/video/clip-1015733875-game-over-glitch-text-animation-3-versions
        winImg = loadImage("win.jpg"); // https://www.shutterstock.com/fr/video/clip-34233733-videogame-you-win-text-on-old-computer
        font = createFont("PressStart2P-Regular.ttf", 20);
        //ADDING PADDLE AND BALL
        paddle = new Paddle(loadImage("paddle2.png"), 40, 10, new int[] {4, 0});
        paddle2 = new Paddle(loadImage("paddle3.png"), 40, 10, new int[] {4, 0});
        ball = new Ball(loadImage("ball2.png"), 5, 5, new int[] {1, 1});
        parseLevel();
    }
    public void draw() {
        background(0); stroke(255);
        //DRAW BRICKS
        for(Brick b : bricks) {
            if (b.getPowerup() == null) {
                b.draw(this);
            } else {
                b.draw(this);
                b.getPowerup().draw(this);
            }
        }
        //DRAW PADDLES & BALL
        paddle.draw(this); paddle2.draw(this); ball.draw(this);
        //UPDATE & CHECK EDGES
        if (game.inPlay) {
            paddle.update(); paddle.checkEdges();
            paddle2.update(); paddle2.checkEdges();
            ball.update(); ball.checkEdges();
            //PADDLES COLLISION
            game.paddleCollision(paddle, ball); game.paddleCollision(paddle2, ball);
            //BRICK COLLISION
            game.brickCollision(bricks, ball);
            //WIN OR LOSE CONDITIONS
            if (ball.lose()) {
                background(loseImg); textSize(16); text("Press R to Restart.", 120, 320); game.soundLose(); delay(4000); }
            //WIN CONDITION (BRICK + BALL + ALL LEVELS)
            if (bricks.size() == 0 && ball.win() && nextLevel == null) {
                background(winImg); textSize(16); text("Press R to Play Again!", 90, 280); game.soundWin(); delay(4000);
            } else if (bricks.size() == 0 && ball.win()) {
                game.inPlay = false;
                parseLevel(); }
        } else {
            gameTransition();
        }
    }

    public void keyPressed() {
        //STARTS BALL
        if (key == 32) { game.inPlay = true; }
        if (game.inPlay) {
            //MOVING PADDLE (PLAYER 1)
            if (key == 'a' || key == 'A') {
            paddle.isMovingLeft = true;
            } else if (key == 'd' || key == 'D') {
            paddle.isMovingRight = true;
            //MOVING PADDLE (PLAYER 2)
            } else if (key == 'j' || key == 'J') {
                paddle2.isMovingLeft = true;
            } else if (key == 'l' || key == 'L') {
                paddle2.isMovingRight = true;
            }
        }
        if (key == 'r' || key == 'R') { reset(); }
    }
    public void keyReleased() {
        if (key == 'a' || key == 'A') {
            paddle.isMovingLeft = false;
            } else if (key == 'd' || key == 'D') {
            paddle.isMovingRight = false;
            //MOVING PADDLE (PLAYER 2)
            } else if (key == 'j' || key == 'J') {
                paddle2.isMovingLeft = false;
            } else if (key == 'l' || key == 'L') {
                paddle2.isMovingRight = false;
            }
        }

    public void gameTransition() {
        textFont(font); textSize(24); text(levelName, 190, 200); textSize(20); text("Press [Space]", 140, 240); } 
    
    public void reset() {
        nextLevel = "Enter File"; settings(); setup(); draw(); }
    public static void main(String[] args) {
        PApplet.main("breaker.App"); }
}