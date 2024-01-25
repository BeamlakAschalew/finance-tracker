package ui;
import components.Components;

import javax.swing.*;
import java.awt.*;

public class ScreenManager {
   /**
    * This display method controls and runs the entire UI */
    public void display() {
        JFrame frame = new JFrame();
        frame.setTitle("Home Page");

        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Create a CardLayout object, this is used to give the feel of "switching" between screens
        CardLayout cardLayout = new CardLayout();

        // Create a new JPanel with the LayoutManager being the cardLayout we created above
        JPanel container = new JPanel(cardLayout);

        // Initialize the two screens: LoginUI and SignupUI here
        LoginUI loginUI = new LoginUI(frame, container, cardLayout);
        SignupUI signupUI = new SignupUI(frame, container, cardLayout);


        // Register the screens, namely the LoginUI and SignupUI by adding it to the panel
        // and giving it a name constraint that can be used later to display that screen
        container.add(loginUI, "loginScreen");
        container.add(signupUI, "signupScreen");

        // Set the initial screen to be displayed
        cardLayout.show(container, "loginScreen");

        // This method gets the physical screen width and height and aligns the frame centrally
        Components.centerFrameOnScreen(frame);

        // Add the JPanel to the JFrame
        frame.add(container);
        frame.setVisible(true);
    }
}
