package rep.scrame.controller.command;

import java.util.StringTokenizer;



public class QuitCommand implements Command {
    @Override
    public void invoke(CommandInterpreter context, StringTokenizer tokens) {
        System.out.println("Saving data...");
        context.getContext().stop();
        context.getTemplate().printLogOffScreen();
    }
}
