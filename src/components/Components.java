package components;

import javax.swing.*;

public class Components {

    public static void displayOptionPane(String message) {
        JOptionPane.showMessageDialog(null, message, "Alert", JOptionPane.ERROR_MESSAGE);
    }

}
