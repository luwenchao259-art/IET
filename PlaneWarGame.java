import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class PlaneWarGame extends JPanel implements ActionListener, KeyListener {
    Timer timer = new Timer(15, this);
    Player player = new Player();
    ArrayList<Bullet> bullets = new ArrayList<>();
    ArrayList<Enemy> enemies = new ArrayList<>();

    public PlaneWarGame() {
        JFrame frame = new JFrame("飞机大战 - 动起来！");
        frame.setSize(480, 800);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(this);
        frame.addKeyListener(this);
        frame.setResizable(false);
        frame.setVisible(true);

        timer.start();
        spawnEnemy();
    }

    public void spawnEnemy() {
        new Timer(1000, e -> enemies.add(new Enemy((int)(Math.random() * 430), -40))).start();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, getWidth(), getHeight());

        player.draw(g);
        bullets.forEach(b -> b.draw(g));
        enemies.forEach(e -> e.draw(g));
    }

    public void actionPerformed(ActionEvent e) {
        bullets.forEach(Bullet::move);
        enemies.forEach(Enemy::move);

        ArrayList<Bullet> toRemoveBullets = new ArrayList<>();
        ArrayList<Enemy> toRemoveEnemies = new ArrayList<>();
        for (Bullet b : bullets) {
            for (Enemy en : enemies) {
                if (b.getBounds().intersects(en.getBounds())) {
                    toRemoveBullets.add(b);
                    toRemoveEnemies.add(en);
                }
            }
        }
        bullets.removeAll(toRemoveBullets);
        enemies.removeAll(toRemoveEnemies);
        repaint();
    }

    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();
        if (code == KeyEvent.VK_LEFT) player.move(-10);
        if (code == KeyEvent.VK_RIGHT) player.move(10);
        if (code == KeyEvent.VK_SPACE) bullets.add(new Bullet(player.x + 20, player.y));
    }

    public void keyReleased(KeyEvent e) {}
    public void keyTyped(KeyEvent e) {}

    public static void main(String[] args) {
        new PlaneWarGame();
    }
}

class Player {
    int x = 220, y = 700;

    public void draw(Graphics g) {
        g.setColor(Color.CYAN);
        g.fillRect(x, y, 40, 40);
    }

    public void move(int dx) {
        x += dx;
        x = Math.max(0, Math.min(x, 440));
    }
}

class Bullet {
    int x, y;

    public Bullet(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void move() {
        y -= 10;
    }

    public void draw(Graphics g) {
        g.setColor(Color.YELLOW);
        g.fillRect(x, y, 5, 10);
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, 5, 10);
    }
}

class Enemy {
    int x, y;

    public Enemy(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void move() {
        y += 3;
    }

    public void draw(Graphics g) {
        g.setColor(Color.RED);
        g.fillRect(x, y, 40, 40);
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, 40, 40);
    }
}
