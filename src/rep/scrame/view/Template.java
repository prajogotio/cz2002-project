package rep.scrame.view;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Scanner;

public class Template {

    public Template() {

    }

    public void printFile(String fileLocation) {
        try {
            Scanner scanner = new Scanner(new BufferedReader(new FileReader(fileLocation)));
            while (scanner.hasNext()) {
                System.out.println(scanner.nextLine());
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    public void printWelcomeScreen() {
        printFile("res/home.txt");
    }

    public void printHelpScreen() {
        printFile("res/instruction.txt");
    }

    public void printLogOffScreen() { printFile("res/quit.txt");}

}
