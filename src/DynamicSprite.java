import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.io.IOException;
import java.util.ArrayList;

public class DynamicSprite extends SolidSprite {
    private boolean isWalking = true;
    private final double speed = 5;
    private Direction direction=Direction.SOUTH;


    public DynamicSprite(double x, double y, Image image, double width, double height) throws IOException {
        super(x, y, image, width, height);
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }
    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }
    @Override
    public void draw(Graphics g) {
        int spriteSheetNumberOfColumn = 10;
        int timeBetweenFrame = 200;
        int index = (int) ((System.currentTimeMillis() / timeBetweenFrame) % spriteSheetNumberOfColumn);
        int attitude = direction.getFrameLineNumber();
        g.drawImage(image, (int) x, (int) y, (int) (x + width), (int) (y + height),
                index * (int) width, attitude * (int) height,
                (index + 1) * (int) width, (attitude + 1) * (int) height, null);
    }
    private boolean isMovingPossible(ArrayList<SolidSprite> environment) {

        Rectangle2D.Double futureHitBox = new Rectangle2D.Double(x, y, width, height);

        switch (direction) {
            case NORTH -> futureHitBox.y -= speed;
            case SOUTH -> futureHitBox.y += speed;
            case WEST -> futureHitBox.x -= speed;
            case EAST -> futureHitBox.x += speed;
        }


        for (Sprite sprite : environment) {
            if (sprite instanceof SolidSprite) {
                Rectangle2D.Double solidHitBox = new Rectangle2D.Double(sprite.x, sprite.y, sprite.width, sprite.height);
                if (futureHitBox.intersects(solidHitBox) && sprite != this) {
                    return false;
                }
            }
        }

        return true; // Pas de collision
    }


    private void move() {
        if (direction != null) {
            switch (direction) {
                case NORTH -> y -= speed;
                case SOUTH -> y += speed;
                case WEST -> x -= speed;
                case EAST -> x += speed;
            }
        }
    }


    public void moveIfPossible(ArrayList<SolidSprite> environment) {
        if (isMovingPossible(environment)) {
            move();
        }
    }

    public boolean isWalking() {
        return isWalking;
    }

    public void setWalking(boolean walking) {
        isWalking = walking;
    }
}