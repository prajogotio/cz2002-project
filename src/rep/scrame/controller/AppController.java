package rep.scrame.controller;

import java.util.Scanner;

import rep.scrame.controller.command.CommandInterpreter;

/**
 * The AppController class
 */
public class AppController {
	/**
	 * Flag if the app is still running.
	 */
    private boolean isRunning;
    
    /**
     * The command interpreter interface.
     */
    private CommandInterpreter commandInterpreter;
    
    /**
     * The scanner instance used by the system.
     */
    private Scanner scanner;
    
    /**
     * The information manager.
     */
    private InformationManager informationManager;

    /**
     * The AppController constructor.
     */
    public AppController() {
        isRunning = true;
        commandInterpreter = CommandInterpreter.getInstance();
        commandInterpreter.setApplicationContext(this);
        informationManager = InformationManager.getInstance();
        scanner = SystemScannerAdapter.getInstance();
    }

    /**
     * Starts the app main logic loop.
     */
    public void run() {
        commandInterpreter.parseStringToCommand("display-home-screen");
        while (isRunning) {
            System.out.print("\nscrame >> ");
            String currentCommand = scanner.nextLine();
            commandInterpreter.parseStringToCommand(currentCommand);
        }
    }

    /**
     * Stops the app.
     */
    public void stop() {
        informationManager.saveInformation();
        isRunning = false;
    }

    /**
     * Gets the information manager.
     * @return	Information manager of the app instance.
     */
    public InformationManager getInformationManager() {
        return informationManager;
    }

}
