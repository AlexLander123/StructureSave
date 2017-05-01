package me.AlexLander123.StructureSave;

import java.util.logging.Logger;

import me.AlexLander123.StructureSave.NMS.NMSInterface;
import me.AlexLander123.StructureSave.NMS.NMS_v1_10_R1;
import me.AlexLander123.StructureSave.NMS.NMS_v1_11_R1;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.BlockCommandSender;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;

public class StructureSave extends JavaPlugin{

	public Logger logger = Logger.getLogger("Minecraft");
	NMSInterface nms;

	@Override
	public void onEnable() {
		PluginDescriptionFile pdfFile = this.getDescription();
		this.logger.info(pdfFile.getName() + " Version "  + pdfFile.getVersion() + " by " + pdfFile.getAuthors() + " Has Been Enabled!");
		setupNMS();	
	}

	@Override
	public void onDisable() {
		PluginDescriptionFile pdfFile = this.getDescription();
		this.logger.info(pdfFile.getName() + " Has Been Disabled!");
	}

	public void setupNMS() {
		String version = getServer().getClass().getPackage().getName().split("\\.")[3];
		switch(version) {

		case ("v1_10_R1"):
			nms = new NMS_v1_10_R1();
			break;
			
		case ("v1_11_R1"):
			nms = new NMS_v1_11_R1();
			break;
		
		default:
			this.logger.severe(ChatColor.RED + "[StructureSave] Unsupported version disabling plugin.");
			getServer().getPluginManager().disablePlugin(this);
		}
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (cmd.getName().equalsIgnoreCase("structuresave")) { 

			if(sender instanceof ConsoleCommandSender){
				sender.sendMessage(ChatColor.RED + "[StructureSave] Only player or commandblocks can execute this command.");
				return true;
			}

			if(sender.hasPermission("structuresave.save")){

				if(args.length == 1){

					Location location = null;

					if(sender instanceof Player){
						location = ((Player) sender).getLocation();
					}
					else if(sender instanceof BlockCommandSender){
						location = ((BlockCommandSender) sender).getBlock().getLocation();
					}
					if(location != null){
						String structureName = args[0];
						nms.saveStructure(structureName, location);
					}
				}
				else { 
					sender.sendMessage(ChatColor.RED + "[StructureSave] Correct Usage: /structuresave <Structure Name>");
				}

			}
			else {
				sender.sendMessage(ChatColor.RED + "[StructureSave] You do not have permission to execute this command.");
			}
			return true;
		} 
		return false; 
	}
}
