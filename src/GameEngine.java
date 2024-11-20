import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class GameEngine implements Engine, KeyListener {
    private final DynamicSprite hero;

    public GameEngine(DynamicSprite hero, JFrame displayZoneFrame) {
        this.hero = hero;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_UP -> hero.setDirection(Direction.NORTH);
            case KeyEvent.VK_DOWN -> hero.setDirection(Direction.SOUTH);
            case KeyEvent.VK_LEFT -> hero.setDirection(Direction.WEST);
            case KeyEvent.VK_RIGHT -> hero.setDirection(Direction.EAST);
        }
    }

    @Override
    public void update() {}

    @Override
    public void keyReleased(KeyEvent e) {}
    @Override
    public void keyTyped(KeyEvent e) {}
}
