import java.util.ArrayList;

public class TClistener extends PluginListener{
	ArrayList<String> cmdlist = new ArrayList<String>();
	ArrayList<String> playerlist = new ArrayList<String>();
	ArrayList<String> timeslist = new ArrayList<String>();
	ArrayList<String> minlist = new ArrayList<String>();
	static ArrayList<String> currentplayer = new ArrayList<String>();
	
	public boolean onCommand(Player player, String[] split) {
    	if (split[0].equalsIgnoreCase("/tcadd") && player.canUseCommand("/BLM") && split.length > 3) { // Player command times intime
    		playerlist.add(split[1]); 
    		cmdlist.add(split[2]); 
    		timeslist.add(split[3]);
    		if (split.length == 5){
    			minlist.add(split[4]);
    		}
    		player.sendMessage(Colors.Green+split[1]+" succesfully added the command "+split[2]+" "+ split[3]+" times.");
    		return true;
    	}   	
    	
    	if (PlayerIsAllowedToDoThisCommand(player, split) == true) { // check if timeslist[playergetnum] != 0 .....
    		player.sendMessage("bis jetzt alles richtig");
    		// player allow command split[0]
    		player.chat(split[1] + restofsplit(split));
    		// player disallow command
    		// times -1
    		return true;
    	}else{
    		return false;
    	}
	}
	
	
	private boolean PlayerIsAllowedToDoThisCommand(Player player,String[] command){
		int zahl=0;
		while (zahl < playerlist.toArray().length){
			if (playergetnum(player,command) != 999){
				if (command[0].equals(cmdlist.toArray()[playergetnum(player,command)])){
					return true;
				}
			}
		}
		return false;
	}
	
	private int playergetnum(Player player,String[] split){
		int zahl = 0;
		while (zahl < playerlist.toArray().length){
		   	if (player.getName().equals(playerlist.toArray()[zahl]) && split[0].equals(cmdlist.toArray()[zahl])){
		   		return zahl;
		   	}
		   	zahl++;
		}
		return 999;
	}
	
	private String restofsplit(String[] split){
		int zahl=2;
		String returner = "";
		while (zahl<split.length){
			returner += " "+split[zahl];
			zahl++;
		}
		return returner;
	}
	
	/*public PluginLoader.HookResult canPlayerUseCommand(Player player, String[] command) {
		if (PlayerIsAllowedToDoThisCommand(player, command) == true && (cmdlist.toArray()[playergetnum(player, command)] == command)){
			
			PluginLoader.HookResult.ALLOW_ACTION;
			
		}else{
			
		}
	}*/
}