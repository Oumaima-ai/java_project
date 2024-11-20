import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class RenderEngine extends JPanel implements Engine {
    private final ArrayList<Displayable> renderList = new ArrayList<>();

    public RenderEngine(JFrame displayZoneFrame) {
        super();
    }

    public void addToRenderList(Displayable displayable) {
        renderList.add(displayable);
    }
    public void addToRenderList(ArrayList<Displayable> displayable){
        if (!renderList.contains(displayable)){
            renderList.addAll(displayable);
        }
    }

    @Override
    public void update() {
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        for (Displayable displayable : renderList) {
            displayable.draw(g);
        }
    }
}
