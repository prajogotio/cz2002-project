package rep.scrame.controller.command;

import java.util.StringTokenizer;

/**
 * Displays the help screen once invoked.
 */
public class HelpCommand implements Command{
    @Override
    public void invoke(CommandInterpreter context, StringTokenizer tokens) {
        context.getTemplate().printHelpScreen();
    }
}
