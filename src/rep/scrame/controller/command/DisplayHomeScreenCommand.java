package rep.scrame.controller.command;

import java.util.StringTokenizer;


/**
 * Displays the home screen.
 */
public class DisplayHomeScreenCommand implements Command {
    @Override
    public void invoke(CommandInterpreter context, StringTokenizer tokens) {
        context.getTemplate().printWelcomeScreen();
    }
}