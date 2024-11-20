import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;

import static javax.swing.WindowConstants.EXIT_ON_CLOSE;

public class Main {

    JFrame displayZoneFrame;

    RenderEngine renderEngine;
    GameEngine gameEngine;
    PhysicEngine physicEngine;

    private long startTime;
    private Timer timer;
    private JLabel timerLabel;
    private boolean gameOver = false;

    public Main() throws Exception {
        // Initialisation de la fenêtre
        displayZoneFrame = new JFrame("Java Labs");
        displayZoneFrame.setSize(400, 600);
        displayZoneFrame.setDefaultCloseOperation(EXIT_ON_CLOSE);

        // Initialisation du héros
        DynamicSprite hero = new DynamicSprite(200, 300,
                ImageIO.read(new File("./img/heroTileSheetLowRes.png")), 48, 50);

        // Initialisation des moteurs
        renderEngine = new RenderEngine(displayZoneFrame);
        physicEngine = new PhysicEngine();
        gameEngine = new GameEngine(hero, displayZoneFrame);

        // Ajout du chronomètre
        timerLabel = new JLabel("Temps écoulé : 0 s", SwingConstants.CENTER);
        displayZoneFrame.add(timerLabel, BorderLayout.NORTH);

        // Démarrer le chronomètre
        startTime = System.currentTimeMillis();
        startTimer();

        // Timers pour les moteurs
        Timer renderTimer = new Timer(50, (time) -> renderEngine.update());
        Timer gameTimer = new Timer(50, (time) -> {
            gameEngine.update();
            checkExit(hero); // Vérifier si le héros atteint la sortie
        });
        Timer physicTimer = new Timer(50, (time) -> physicEngine.update());

        renderTimer.start();
        gameTimer.start();
        physicTimer.start();

        displayZoneFrame.getContentPane().add(renderEngine);
        displayZoneFrame.setVisible(true);

        // Charger le niveau
        Playground level = new Playground("./data/level1.txt");
        renderEngine.addToRenderList(level.getSpriteList());
        renderEngine.addToRenderList(hero);
        physicEngine.addToMovingSpriteList(hero);
        physicEngine.setEnvironment(level.getSolidSpriteList());

        // Ajout du KeyListener
        displayZoneFrame.addKeyListener(gameEngine);
    }

    // Démarrer le chronomètre
    private void startTimer() {
        timer = new Timer(1000, (e) -> {
            if (gameOver) {
                return; // Arrêter toute logique si le jeu est terminé
            }

            long elapsedTime = (System.currentTimeMillis() - startTime) / 1000; // Temps en secondes
            timerLabel.setText("Temps écoulé : " + elapsedTime + " s");

            // Vérifiez si 10 secondes sont dépassées
            if (elapsedTime >= 10) {
                stopTimer("Game Over ! Vous n'avez pas trouvé la sortie à temps.");
            }
        });
        timer.start();
    }


    private void stopTimer(String message) {
        gameOver = true; // Marquer le jeu comme terminé
        timer.stop(); // Arrêter le chronomètre
        long elapsedTime = (System.currentTimeMillis() - startTime) / 1000; // Temps final en secondes
        JOptionPane.showMessageDialog(displayZoneFrame,
                message + "\nTemps final : " + elapsedTime + " secondes.",
                "Fin du Jeu", JOptionPane.INFORMATION_MESSAGE);
        System.exit(0); // Quitter l'application
    }


    private void checkExit(DynamicSprite hero) {
        if (gameOver) {
            return; // Ne rien faire si le jeu est terminé
        }

        // Coordonnées de la sortie
        double exitX = 300;
        double exitY = 500;

        // Vérifiez si le héros atteint la sortie
        if (hero.getX() >= exitX && hero.getY() >= exitY) {
            stopTimer("Félicitations ! Vous avez trouvé la sortie !");
        }
    }


    public static void main(String[] args) throws Exception {
        new Main();
    }
}

















