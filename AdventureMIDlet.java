import javax.microedition.midlet.*;
import javax.microedition.lcdui.*;

public class AdventureMIDlet extends MIDlet {
    private Display display;
    private BootScreen bootScreen;
    private GameCanvas canvas;

    public AdventureMIDlet() {
        display = Display.getDisplay(this);
    }

    public void startApp() {
        if (bootScreen == null && canvas == null) {
            // Première fois - montrer le boot screen
            bootScreen = new BootScreen(this);
            display.setCurrent(bootScreen);
        } else if (canvas != null) {
            // Le jeu est déjà en cours
            display.setCurrent(canvas);
        }
    }
    
    public void startGame() {
        // Appelé par BootScreen quand l'utilisateur est prêt
        if (bootScreen != null) {
            bootScreen.stop();
            bootScreen = null;
        }
        
        if (canvas == null) {
            canvas = new GameCanvas(this);
        }
        display.setCurrent(canvas);
    }

    public void pauseApp() {}
    
    public void destroyApp(boolean unconditional) {
        if (bootScreen != null) {
            bootScreen.stop();
        }
        notifyDestroyed();
    }
}