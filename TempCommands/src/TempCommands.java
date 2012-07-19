import java.io.File;
import java.util.logging.Logger;

public class TempCommands extends Plugin {
	
	String name ="TempCommands";
	String version ="0.1";
	String author ="Babble";
	final static Logger log = Logger.getLogger("Minecraft");
	TClistener listener = new TClistener();
	
	public void disable() {
		log.info(this.name + " v" + this.version + " by " + this.author + " is disabled.");
	}

    public void enable() {
    	etc.getInstance().addCommand("/tcadd", "[Player] [Command] [Times] <In the time he has to use it>");
	    etc.getLoader().addListener(PluginLoader.Hook.COMMAND, this.listener, this, PluginListener.Priority.MEDIUM);
	    etc.getLoader().addListener(PluginLoader.Hook.COMMAND_CHECK, this.listener, this, PluginListener.Priority.MEDIUM);
	    this.createProperties();
    	log.info(this.name + " v" + this.version + " by " + this.author + " is enabled.");
    }
    
    public void createProperties(){
	    File f = new File("plugins/config");
	    f.mkdir();
	    PropertiesFile p = new PropertiesFile("plugins/config/TempCommandsTemp.properties");
	    p.save();
    }
}