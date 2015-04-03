package rep.scrame.controller.command;

import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

import rep.scrame.controller.AppController;
import rep.scrame.view.Template;

public class CommandInterpreter {
    private static final CommandInterpreter INSTANCE = new CommandInterpreter();

    private Map<String, Command> commandLookUp;
    private AppController context;
    private Template template;

    public static CommandInterpreter getInstance() { return INSTANCE; }
    private CommandInterpreter() {
        context = null;
        template = new Template();

        initializeLookUpTable();
    }

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

    public void setApplicationContext(AppController context) {
        this.context = context;
    }

    public Template getTemplate() {
        return template;
    }

    public AppController getContext() {
        return context;
    }

    public void initializeLookUpTable() {
        commandLookUp = new HashMap<String, Command>();
        commandLookUp.put("display-home-screen", new DisplayHomeScreenCommand());
        commandLookUp.put("help", new HelpCommand());
        commandLookUp.put("quit", new QuitCommand());
        commandLookUp.put("list", new ListCommand());
        commandLookUp.put("add", new AddCommand());
        commandLookUp.put("view", new ViewCommand());
        commandLookUp.put("update", new UpdateCommand());
        commandLookUp.put("get", new GetCommand());
    }
}