package rep.scrame.controller.command;

import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

import rep.scrame.controller.AppController;
import rep.scrame.view.Template;

/**
 * A command interpreter class that interprets user input into a command invocation.
 */
public class CommandInterpreter {
	/**
	 * The singleton instance of CommandInterpreter.
	 */
    private static final CommandInterpreter INSTANCE = new CommandInterpreter();

    /**
     * Command lookup table that manages the mapping between user input and
     * the corresponding commands that are assigned to that particular input.
     */
    private Map<String, Command> commandLookUp;
    
    /**
     * The context in which the command interpreter is running on.
     */
    private AppController context;
    
    /**
     * The template view class used to manage static views.
     */
    private Template template;

    /**
     * Gets the only instance of command interpreter.
     * @return	CommandInterpreter singleton.
     */
    public static CommandInterpreter getInstance() { return INSTANCE; }
    
    /**
     * CommandInterpreter constructor. It is made private to support singleton pattern.
     */
    private CommandInterpreter() {
        context = null;
        template = new Template();

        initializeLookUpTable();
    }

    /**
     * Parses a string of input into a recognized command.
     * @param commandString	String of input.
     */
    public void parseStringToCommand(String commandString) {
        StringTokenizer st = new StringTokenizer(commandString, " ");
        if(!st.hasMoreTokens()) {
            return;
        }
        String commandHead = st.nextToken();
        if(commandLookUp.containsKey(commandHead)) {
            commandLookUp.get(commandHead).invoke(this, st);
        } else {
            System.out.println(commandHead + " is not a recognised command. Enter \"help\" to check the list of recognised commands.");
        }
    }

    /**
     * Sets the application context.
     * @param context	Application context.
     */
    public void setApplicationContext(AppController context) {
        this.context = context;
    }

    /**
     * Gets the template manager.
     * @return	Template manager.
     */
    public Template getTemplate() {
        return template;
    }

    /**
     * Gets the app controller context.
     * @return	AppController context.
     */
    public AppController getContext() {
        return context;
    }

    /**
     * Initializes the look up table to all recognizable input commands.
     */
    public void initializeLookUpTable() {
        commandLookUp = new HashMap<String, Command>();
        commandLookUp.put("display-home-screen", new DisplayHomeScreenCommand());
        commandLookUp.put("help", new HelpCommand());
        commandLookUp.put("quit", new QuitCommand());
        commandLookUp.put("ls", new ListCommand());
        commandLookUp.put("add", new AddCommand());
        commandLookUp.put("vw", new ViewCommand());
        commandLookUp.put("upd", new UpdateCommand());
        commandLookUp.put("get", new GetCommand());
    }
}