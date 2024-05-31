
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.Random;

public class Obstacle {
    private int x;
    private final int y;
    private final int width;
    private final int height;
    private final Random random;
    private int speed;

    public Obstacle(int startX, int ground, int width, int height) {
        this.x = startX;
        this.y = ground;
        this.width = width;
        this.height = height;
        this.random = new Random();
        this.speed = 5;
    }

    public void update() {
        x -= speed;
    }

    public void reset(int startX) {
        this.x = startX + random.nextInt(300);
        this.speed = 5 + random.nextInt(3);
    }

    public void draw(Graphics g) {
        g.setColor(Color.GRAY);
        g.fillRect(x, y, width, height);
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, width, height);
    }

    public boolean isOffScreen() {
        return x < 0;
    }
}
