package com.OblivionNetwork.IceCreamScoop;

import java.util.logging.Logger;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class IceCreamScoop extends JavaPlugin {
	public static final Logger log = Logger.getLogger("Minecraft");
	
	public boolean onCommand(CommandSender sender, Command command, String label, String[] arguments) {
		double radius = 15;
		if(arguments.length < 0) {
			try {
				radius = Double.parseDouble(arguments[0]);
				if((radius < 5) | (radius > 25)) {
					radius = 15;
				}
			} catch (NumberFormatException exception) {
				//do nothing
			}
		}
		
		if(label.equalsIgnoreCase("icecreamscoop")) {
			if(sender instanceof Player) {
				scoopTerrain(sender, radius);
			}
			return true;
		}
		return false;
	}
	
	//dig a circular scoop out o the world
	private void scoopTerrain(CommandSender sender, double rad) {
		Player me = (Player) sender;
		Location spot = me.getLocation();
		World world = me.getWorld();
		
		//Lopp through a 3d square with sides twice the radius width
		for(double x = spot.getX() - rad; x < spot.getBlockX() + rad; x++) {
			for(double y = spot.getY() - rad; y < spot.getBlockY() + rad; y++) {
				for(double z = spot.getZ() - rad; z < spot.getBlockZ() + rad; z++) {
					Location loc = new Location(world, x , y, z);
					double xd = x - spot.getX();
					double yd = y - spot.getY();
					double zd = z - spot.getZ();
					double distance = Math.sqrt(xd * xd + yd * yd + zd * zd);
					
					if(distance < rad) {
						Block current = world.getBlockAt(loc);
						current.setType(Material.AIR);
					}
				}
			}
		}
	
		world.playSound(spot, Sound.BURP, 30, 5);
		log.info("[IceCreamScoop] Scooped at (" + spot.getX() + " ," + spot.getBlockY() + " ," + spot.getBlockZ() );
	}
}
