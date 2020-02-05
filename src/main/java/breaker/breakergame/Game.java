package breaker.breakergame;

import java.util.List;
import java.util.ArrayList;

import breaker.breakergame.Paddle;
import breaker.breakergame.Ball;
import breaker.breakergame.Objs;
import breaker.breakergame.Powerup;

import java.io.*;
import javax.sound.sampled.*;
import javax.swing.*;

public class Game {

    public boolean inPlay;
    public String nextLevel;

    public Game() {
        this.inPlay = false;
    }

    public void soundPaddle() {
        //javax.sound https://www3.ntu.edu.sg/home/ehchua/programming/java/J8c_PlayingSound.html
        try {
            // Open an audio input stream.
            File soundFile = new File("sound.wav"); //http://soundbible.com/102-Putter-Golf-Ball.html
            AudioInputStream audioIn = AudioSystem.getAudioInputStream(soundFile);
            // Get a sound clip resource.
            Clip clip = AudioSystem.getClip();
            // Open audio clip and load samples from the audio input stream.
            clip.open(audioIn);
            clip.start();
         } catch (UnsupportedAudioFileException e) {
            e.printStackTrace();
         } catch (IOException e) {
            e.printStackTrace();
         } catch (LineUnavailableException e) {
            e.printStackTrace();
         }
    }

    public void soundBrick() {
        try {
            // Open an audio input stream.
            File soundFile = new File("sound2.wav"); //http://soundbible.com/1765-Glass-Break.html
            AudioInputStream audioIn = AudioSystem.getAudioInputStream(soundFile);
            // Get a sound clip resource.
            Clip clip = AudioSystem.getClip();
            // Open audio clip and load samples from the audio input stream.
            clip.open(audioIn);
            clip.start();
         } catch (UnsupportedAudioFileException e) {
            e.printStackTrace();
         } catch (IOException e) {
            e.printStackTrace();
         } catch (LineUnavailableException e) {
            e.printStackTrace();
         }
    }

    public void soundWin() {
        try {
            // Open an audio input stream.
            File soundFile = new File("sound3.wav"); //http://soundbible.com/2103-1-Person-Cheering.html
            AudioInputStream audioIn = AudioSystem.getAudioInputStream(soundFile);
            // Get a sound clip resource.
            Clip clip = AudioSystem.getClip();
            // Open audio clip and load samples from the audio input stream.
            clip.open(audioIn);
            clip.start();
         } catch (UnsupportedAudioFileException e) {
            e.printStackTrace();
         } catch (IOException e) {
            e.printStackTrace();
         } catch (LineUnavailableException e) {
            e.printStackTrace();
         }
    }

    public void soundLose() {
        try {
            // Open an audio input stream.
            File soundFile = new File("sound4.wav"); //https://soundbible.com/1830-Sad-Trombone.html
            AudioInputStream audioIn = AudioSystem.getAudioInputStream(soundFile);
            // Get a sound clip resource.
            Clip clip = AudioSystem.getClip();
            // Open audio clip and load samples from the audio input stream.
            clip.open(audioIn);
            clip.start();
         } catch (UnsupportedAudioFileException e) {
            e.printStackTrace();
         } catch (IOException e) {
            e.printStackTrace();
         } catch (LineUnavailableException e) {
            e.printStackTrace();
         }
    }

    public void paddleCollision(Paddle paddle, Ball ball) {
            //BALL TOUCHES PADDLE (PLAYER 1)
        if (ball.touch(paddle) && ball.getDirectionY() > 0) {
            //BALL TOUCHES MIDDLE (1)
            if (ball.touchmid(paddle) && ball.getDirectionY() > 0) {
                ball.setDirectionY(-1);
                ball.addSpeedY(3);
                ball.addSpeedX(-1);
            } else {
                // IF PADDLE IS MOVING CHANGE DIRECTION ACCORDING TO PADDLE
                if (!ball.touchmid(paddle) && ball.getDirectionX() < 0 && paddle.isMovingRight) {
                    ball.addSpeedX(1);
                    ball.setDirectionX(-1);
                } else if (!ball.touchmid(paddle) && ball.getDirectionX() > 0 && paddle.isMovingLeft) {
                    ball.addSpeedX(1);
                    ball.setDirectionX(-1);
                }
                // (1) IF NOT JUST CHANGE DIRECTION
                ball.setDirectionY(-1);
            }
            soundPaddle();
        }
    }

    public void brickCollision(List<Brick> bricks, Ball ball) {
        if (ball.hit(bricks)) {
            ball.setDirectionY(-1);
            ball.addSpeedX(-1);
            ball.addSpeedY(-1);
            soundBrick();
            bricks.get(ball.getBrickHit()).minusHp();
            if (bricks.get(ball.getBrickHit()).getHp() == 0) {
                bricks.remove(ball.getBrickHit());
            }
        }
    }

    public boolean getInPlay() {
        return this.inPlay;
    }

}
