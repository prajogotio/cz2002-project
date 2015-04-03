package rep.scrame;

import rep.scrame.controller.AppController;

public class Main {
    public static void main(String[] args) {
        System.out.println("Initializing system...\n");
        AppController app = new AppController();
        app.run();
    }
}
