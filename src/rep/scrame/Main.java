package rep.scrame;

import rep.scrame.controller.AppController;


/**
 * 
 * The entry point of our SCRAME system. This class instantiate AppController class
 * which allows the app to be run.
 * 
 */
public class Main {
    public static void main(String[] args) {
        System.out.println("Initializing system...\n");
        AppController app = new AppController();
        app.run();
    }
}
