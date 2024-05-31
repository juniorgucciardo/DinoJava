
import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;

public class Cloud {
    private int x, y;
    private final int width, height;
    private final Random random;

    public Cloud(int startX, int startY, int width, int height) {
        this.x = startX;
        this.y = startY;
        this.width = width;
        this.height = height;
        this.random = new Random();
    }

    public void update() {
        x -= 2; // Velocidade das nuvens
        if (x < -width) {
            x = 800 + random.nextInt(200);
            y = random.nextInt(150);
        }
    }

    public void draw(Graphics g) {
        g.setColor(Color.DARK_GRAY);
        g.fillRect(x, y, width, height);
    }
}