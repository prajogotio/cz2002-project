package rep.scrame.controller.command;

import java.util.StringTokenizer;

/**
 * Interface for command handler. A command stores a group of function calls and
 * functionality that can be invoked when needed by the system to decouple
 * the system's command handling and command invocation.
 */
public interface Command {
	/**
	 * Invokes the command.
	 * @param context	The CommandInterpreter object used to interpret the command.
	 * @param tokens	Tokenizer used tokenized the command currently being interpreted.
	 */
    public void invoke(CommandInterpreter context, StringTokenizer tokens);
}