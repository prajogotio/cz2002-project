package rep.scrame.controller.command;

import java.util.StringTokenizer;


public interface Command {
    public void invoke(CommandInterpreter context, StringTokenizer tokens);
}