package rep.scrame.controller;

import java.util.Scanner;

import rep.scrame.controller.command.CommandInterpreter;

public class AppController {
    private boolean isRunning;
    private CommandInterpreter commandInterpreter;
    private Scanner scanner;
    private InformationManager informationManager;

    public AppController() {
        isRunning = true;
        commandInterpreter = CommandInterpreter.getInstance();
        commandInterpreter.setApplicationContext(this);
        informationManager = InformationManager.getInstance();
        scanner = SystemScannerAdapter.getInstance();
    }

    public void run() {
        commandInterpreter.parseStringToCommand("display-home-screen");
        while (isRunning) {
            System.out.print("\nscrame >> ");
            String currentCommand = scanner.nextLine();
            commandInterpreter.parseStringToCommand(currentCommand);
        }
    }

    public void stop() {
        informationManager.saveInformation();
        isRunning = false;
    }

    public InformationManager getInformationManager() {
        return informationManager;
    }

}
