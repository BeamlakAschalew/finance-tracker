// LET'S DO THIS!
import com.formdev.flatlaf.FlatLaf;
import com.formdev.flatlaf.FlatLightLaf;
import ui.ScreenManager;

import javax.swing.*;
import java.awt.*;

import components.*;

public class Main {
    /**
     * Everything in the app starts and stops running here, everything being done in the display method
     * */
    Main() {
        new ScreenManager().display();
    }
    public static void main(String[] args) {
        try {
            Font globalFont = new Font("Candara", Font.PLAIN, 15);

            // Set the global font for all Swing components
            Components.setUIFont(globalFont);
            UIManager.setLookAndFeel(new FlatLightLaf());
            FlatLaf.updateUI();
            new Main();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}