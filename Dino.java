package com.utils.Dino;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

public class Dino {
    private final int GROUND;
    private int y;
    private int speedY;
    private boolean jumping;
    private int animationFrame;

    public Dino(int ground) {
        this.GROUND = ground;
        this.y = ground;
        this.speedY = 0;
        this.jumping = false;
        this.animationFrame = 0;
    }

    public void jump() {
        if (!jumping) {
            jumping = true;
            speedY = -15;
        }
    }

    public void update() {
        if (jumping) {
            y += speedY;
            speedY += 1;
            if (y >= GROUND) {
                y = GROUND;
                jumping = false;
                speedY = 0;
            }
        } else {
            // Small animation effect to make it look like the dino is running
            animationFrame++;
            if (animationFrame > 10) {
                y = GROUND - 5;
            } else {
                y = GROUND;
            }
            if (animationFrame > 20) {
                animationFrame = 0;
            }
        }
    }

    public void draw(Graphics g) {
        g.setColor(Color.WHITE);
        g.fillRect(50, y, 20, 40);
    }

    public Rectangle getBounds() {
        return new Rectangle(50, y, 20, 40);
    }
}