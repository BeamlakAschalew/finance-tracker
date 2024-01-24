package components;

import javax.swing.*;
import java.awt.*;

public class Components {

    // method to display JOptionPane, it takes a message and status, status 0 means error and 1 means okay
    public static void displayOptionPane(String message, int status) {
        JOptionPane.showMessageDialog(null, message, status == 0 ? "Alert" : "Info", status);
    }

    // this method takes a JFrame reference and centers the frame to the center
    public static void centerFrameOnScreen(JFrame frame) {
        // get the physical screen size
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

        // calculate the X and Y coordinates inorder to center the frame
        int centerX = (int) (screenSize.getWidth() - frame.getWidth()) / 2;
        int centerY = (int) (screenSize.getHeight() - frame.getHeight()) / 2;

        // set the frame's location to the calculated X and Y coordinate
        frame.setLocation(centerX, centerY);
    }

    // this method does the same as the above one but for JDialog
    public static void centerDialogOnScreen(JDialog frame) {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int centerX = (int) (screenSize.getWidth() - frame.getWidth()) / 2;
        int centerY = (int) (screenSize.getHeight() - frame.getHeight()) / 2;
        frame.setLocation(centerX, centerY);
    }

    // this method takes a font and assigns it to various swing components
    public static void setUIFont(Font font) {
        // Set the font for all UIManager keys
        UIManager.put("Button.font", font);
        UIManager.put("ToggleButton.font", font);
        UIManager.put("RadioButton.font", font);
        UIManager.put("CheckBox.font", font);
        UIManager.put("ColorChooser.font", font);
        UIManager.put("ComboBox.font", font);
        UIManager.put("Label.font", font);
        UIManager.put("List.font", font);
        UIManager.put("MenuBar.font", font);
        UIManager.put("MenuItem.font", font);
        UIManager.put("RadioButtonMenuItem.font", font);
        UIManager.put("CheckBoxMenuItem.font", font);
        UIManager.put("Menu.font", font);
        UIManager.put("PopupMenu.font", font);
        UIManager.put("OptionPane.font", font);
        UIManager.put("Panel.font", font);
        UIManager.put("ProgressBar.font", font);
        UIManager.put("ScrollPane.font", font);
        UIManager.put("Viewport.font", font);
        UIManager.put("TabbedPane.font", font);
        UIManager.put("Table.font", font);
        UIManager.put("TableHeader.font", font);
        UIManager.put("TextField.font", font);
        UIManager.put("PasswordField.font", font);
        UIManager.put("TextArea.font", font);
        UIManager.put("TextPane.font", font);
        UIManager.put("EditorPane.font", font);
        UIManager.put("TitledBorder.font", font);
        UIManager.put("ToolBar.font", font);
        UIManager.put("ToolTip.font", font);
        UIManager.put("Tree.font", font);
    }

}
