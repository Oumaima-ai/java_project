import java.util.ArrayList;

public class PhysicEngine implements Engine {
    private final ArrayList<DynamicSprite> movingSpriteList = new ArrayList<>();
    private ArrayList<SolidSprite> environment = new ArrayList<>();

    public void addToMovingSpriteList(DynamicSprite sprite) {
        movingSpriteList.add(sprite);
    }

    public void setEnvironment(ArrayList<SolidSprite> environment) {
        this.environment = environment;
    }

    @Override
    public void update() {
        for (DynamicSprite sprite : movingSpriteList) {
            sprite.moveIfPossible(environment);
        }
    }
}
