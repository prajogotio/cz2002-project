package rep.scrame.controller.command;

import java.util.StringTokenizer;


public class HelpCommand implements Command{
    @Override
    public void invoke(CommandInterpreter context, StringTokenizer tokens) {
        context.getTemplate().printHelpScreen();
    }
}
