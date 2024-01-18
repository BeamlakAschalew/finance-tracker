// LET'S DO THIS!
import com.formdev.flatlaf.FlatLaf;
import com.formdev.flatlaf.FlatLightLaf;
import ui.ScreenManager;

import javax.swing.*;

public class Main {
    /**
     * Everything in the app starts and stops running here, everything being done in the display method
     * */
    Main() {
        new ScreenManager().display();
    }
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(new FlatLightLaf());
            FlatLaf.updateUI();
            new Main();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}