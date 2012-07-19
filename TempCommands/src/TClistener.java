import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * @project TempCommands
 * @author Spenk & Babble
 * @version 0.1
 * @category Misc
 */
public class TClistener extends PluginListener {
	HashMap<String, String> delayed = new HashMap<String, String>();

	public boolean onCommand(Player player, String[] split) {
		if (split[0].equalsIgnoreCase("/tc") && player.canUseCommand("/BLM")) {
			if (split.length < 5) {
				player.notify("/tc <player> </command> <uses> <delay> <Second / Minutes (S/M)>");
				return true;
			}

			Player player2 = etc.getServer().matchPlayer(split[1]);
			this.addTempCommand(player2, split[2], Integer.parseInt(split[3]),
					Integer.parseInt(split[4]), split[5]);
			return true;
		}
		return false;
	}

	/**
	 * Adds the command to the propertiesFile
	 * 
	 * 
	 * @param player
	 * @param command
	 * @param uses
	 * @param delay
	 * @param paramDelay
	 */
	public void addTempCommand(Player player, String command, int uses,
			int delay, String paramDelay) {
		if (uses == 0) {
			return;
		}
		PropertiesFile p = new PropertiesFile(
				"plugins/config/TempCommandsTemp.properties");
		String toSave = command + "," + uses + "," + delay + "," + paramDelay;
		if (p.getString(player.getName()) == null
				|| p.getString(player.getName()).equalsIgnoreCase("")) {
			p.setString(player.getName(), toSave);
		} else {
			p.setString(player.getName(), p.getString(player.getName()) + ":"
					+ toSave);
		}
		return;
	}

	/**
	 * removes a command from the propertiesfile
	 * 
	 * 
	 * @param player
	 * @param command
	 */
	public void removeTempCommand(Player player, String command) {
		PropertiesFile p = new PropertiesFile(
				"plugins/config/TempCommandsTemp.properties");
		if (!p.containsKey(player.getName())
				|| p.getString(player.getName()) == null
				|| p.getString(player.getName()).equalsIgnoreCase("")) {
			return;
		}
		StringBuilder toRecover = new StringBuilder();
		List<String> list = Arrays.asList(p.getString(player.getName()).split(
				":"));
		for (String s : list) {
			String[] l2 = s.split(",");
			if (l2[0].equalsIgnoreCase(command)) {
				list.remove(s);
			}
			if (toRecover.toString().equalsIgnoreCase("")) {
				toRecover.append(s);
			} else {
				toRecover.append(":" + s);
			}
			if (!toRecover.toString().equalsIgnoreCase("")) {
				p.setString(player.getName(), toRecover.toString());
			}
		}
		return;
	}

	/**
	 * checks if the propertiesFile still contains the command
	 * 
	 * @param player
	 * @param command
	 * @return canUseCommand
	 */
	public boolean isAllowed(Player player, String command) {
		PropertiesFile p = new PropertiesFile(
				"plugins/config/TempCommandsTemp.properties");
		if (!p.containsKey(player.getName())
				|| p.getString(player.getName()) == null
				|| p.getString(player.getName()).equalsIgnoreCase("")) {
			return false;
		}
		for (String s : delayed.get(player.getName()).split(",")) {
			if (s.equalsIgnoreCase(command)) {
				return false;
			}
		}

		List<String> list = Arrays.asList(p.getString(player.getName()).split(
				":"));
		for (String s : list) {
			String[] l2 = s.split(",");
			if (l2[0].equalsIgnoreCase(command)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * checks how many times the player can use the command
	 * 
	 * 
	 * @param player
	 * @param command
	 * @return times the player can still use the command
	 */
	public int getTimes(Player player, String command) {
		PropertiesFile p = new PropertiesFile(
				"plugins/config/TempCommandsTemp.properties");
		if (!p.containsKey(player.getName())
				|| p.getString(player.getName()) == null
				|| p.getString(player.getName()).equalsIgnoreCase("")) {
			return 0;
		}
		List<String> list = Arrays.asList(p.getString(player.getName()).split(
				":"));
		for (String s : list) {
			String[] l2 = s.split(",");
			if (l2[0].equalsIgnoreCase(command)) {
				return Integer.parseInt(l2[1]);
			}
		}
		return 0;
	}

	/**
	 * sets the times of usage left
	 * 
	 * 
	 * @param player
	 * @param command
	 * @param times
	 */
	public void setTimes(Player player, String command, int times) {
		if (times <= 0) {
			this.removeTempCommand(player, command);
			return;
		}
		PropertiesFile p = new PropertiesFile(
				"plugins/config/TempCommandsTemp.properties");
		if (!p.containsKey(player.getName())
				|| p.getString(player.getName()) == null
				|| p.getString(player.getName()).equalsIgnoreCase("")) {
			return;
		}
		StringBuilder toRecover = new StringBuilder();
		List<String> list = Arrays.asList(p.getString(player.getName()).split(
				":"));
		for (String s : list) {
			String[] l2 = s.split(",");
			if (l2[0].equalsIgnoreCase(command)) {
				int time = Integer.parseInt(l2[1]) - 1;
				String toAddToTorecover = l2[0] + "," + time + l2[2] + ","
						+ l2[3];
				if (toRecover.toString().equalsIgnoreCase("")) {
					toRecover.append(toAddToTorecover);
				} else {
					toRecover.append(":" + toAddToTorecover);
				}
			}
			if (toRecover.toString().equalsIgnoreCase("")) {
				toRecover.append(s);
			} else {
				toRecover.append(":" + s);
			}
			if (!toRecover.toString().equalsIgnoreCase("")) {
				p.setString(player.getName(), toRecover.toString());
			}
		}
		return;
	}

	/**
	 * returns the delay between command usage
	 * 
	 * 
	 * @param player
	 * @param command
	 * @return delay
	 */
	public int getDelay(Player player, String command) {
		PropertiesFile p = new PropertiesFile(
				"plugins/config/TempCommandsTemp.properties");
		if (!p.containsKey(player.getName())
				|| p.getString(player.getName()) == null
				|| p.getString(player.getName()).equalsIgnoreCase("")) {
			return 0;
		}
		List<String> list = Arrays.asList(p.getString(player.getName()).split(
				":"));
		for (String s : list) {
			String[] l2 = s.split(",");
			if (l2[0].equalsIgnoreCase(command)) {
				return Integer.parseInt(l2[2]);
			}
		}
		return 0;
	}

	/**
	 * returns M or S depending on Minutes or Seconds
	 * 
	 * 
	 * @param player
	 * @param command
	 * @return
	 */
	public String getDelayParam(Player player, String command) {
		PropertiesFile p = new PropertiesFile(
				"plugins/config/TempCommandsTemp.properties");
		if (!p.containsKey(player.getName())
				|| p.getString(player.getName()) == null
				|| p.getString(player.getName()).equalsIgnoreCase("")) {
			return null;
		}
		List<String> list = Arrays.asList(p.getString(player.getName()).split(
				":"));
		for (String s : list) {
			String[] l2 = s.split(",");
			if (l2[0].equalsIgnoreCase(command)) {
				return l2[3].toUpperCase();
			}
		}
		return null;
	}

	/**
	 * 
	 * this is mend to add a command to the hashmap after this
	 * startDelayTimer(); has to be triggered!
	 * 
	 * @param player
	 * @param command
	 */
	public void addToDelay(Player player, String command) {
		if (!delayed.containsKey(player.getName())) {
			delayed.put(player.getName(), command);
			return;
		}
		String exist = delayed.get(player.getName());
		delayed.remove(player.getName());
		delayed.put(player.getName(), exist + "," + command);
		return;
	}

	public void removeFromDelay(Player player, String command) {
		if (delayed.containsKey(player.getName())) {
			String[] commands = delayed.get(player.getName()).split(",");
			int i = 0;
			while (i < commands.length) {
				if (commands[i].equalsIgnoreCase(command)) {
					commands[i].replace(command, "");
				}
			}
			String toSave = etc.combineSplit(0, commands, ",");
			if (toSave.equalsIgnoreCase("") || toSave == null) {
				delayed.remove(player.getName());
			} else {
				delayed.remove(player.getName());
				delayed.put(player.getName(), toSave);
			}
		}
	}

	/**
	 * 
	 * this is mend to remove a command from the hashmap
	 * 
	 * @param player
	 * @param command
	 * @param time
	 * @param delayParam
	 */
	public void startDelayTimer(final Player player, final String command,
			int time, String delayParam) {
		long timer = 0;
		if (delayParam.equalsIgnoreCase("M")) {
			timer = (time * 60) * 1000;
		} else if (delayParam.equalsIgnoreCase("S")) {
			timer = time * 1000;
		}
		final long t = timer;
		new Thread() {
			public void run() {
				try {
					Thread.sleep(t);
					removeFromDelay(player, command);
					setTimes(player, command, getTimes(player, command) - 1);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}.start();
	}

	
	public PluginLoader.HookResult canPlayerUseCommand(Player player,
			String[] command) {
		if (this.isAllowed(player, command[0])) {
			this.addToDelay(player, command[0]);
			this.startDelayTimer(player, command[0],
					this.getDelay(player, command[0]),
					this.getDelayParam(player, command[0]));
			return PluginLoader.HookResult.ALLOW_ACTION;
		}
		return PluginLoader.HookResult.DEFAULT_ACTION;
	}
}