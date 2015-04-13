package rep.scrame.view;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Scanner;

/**
 * Template class which displays static view.
 */
public class Template {

	/**
	 * Constructor of Template class.
	 */
    public Template() {}

    /**
     * Print a static view based on a file.
     * @param fileLocation	File descriptor of the view.
     */
    public void printFile(String fileLocation) {
        try {
            Scanner scanner = new Scanner(new BufferedReader(new FileReader(fileLocation)));
            while (scanner.hasNext()) {
                System.out.println(scanner.nextLine());
            }
            scanner.close();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    /**
     * Prints the welcome screen.
     */
    public void printWelcomeScreen() {
        printFile("res/home.txt");
    }

    /**
     * Prints the help screen.
     */
    public void printHelpScreen() {
        printFile("res/instruction.txt");
    }

    /**
     * Prints the log off screen.
     */
    public void printLogOffScreen() { printFile("res/quit.txt");}

}
