package game;

import java.awt.*;
import java.util.Random;

public class ParticleSystem {
    private int x, y;
    private int life = 30;
    private Random rand = new Random();

    public ParticleSystem(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void render(Graphics2D g) {
        if (life <= 0) return;
        for (int i = 0; i < 10; i++) {
            int dx = rand.nextInt(10) - 5;
            int dy = rand.nextInt(10) - 5;
            g.setColor(new Color(rand.nextInt(255), rand.nextInt(255), 0));
            g.fillRect(x + dx, y + dy, 2, 2);
        }
        life--;
    }

    public boolean isAlive() { return life > 0; }
}