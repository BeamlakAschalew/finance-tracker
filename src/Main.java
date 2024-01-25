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

    // Create anonymous ScreenManager object and run the display method to display the UI
    Main() {
        new ScreenManager().display();
    }
    public static void main(String[] args) {
        try {
            // Create a font object
            Font globalFont = new Font("Candara", Font.PLAIN, 15);

            // Set the global font for all Swing components
            // The setUIFont method sets the font passed as an argument as the font for different components
            Components.setUIFont(globalFont);

            // Set the look and feel of the swing project
            UIManager.setLookAndFeel(new FlatLightLaf());

            // Update the UI
            FlatLaf.updateUI();

            // Create anonymous object
            new Main();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}