// LET'S DO THIS!

import ui.ScreenManager;
public class Main {
    /**
     * Everything in the app starts and stops running here, everything being done in the display method
     * */
    Main() {
        new ScreenManager().display();
    }
    public static void main(String[] args) {
        new Main();
    }
}