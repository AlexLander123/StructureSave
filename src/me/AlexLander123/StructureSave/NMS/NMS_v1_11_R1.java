package me.AlexLander123.StructureSave.NMS;

import net.minecraft.server.v1_11_R1.DefinedStructure;
import net.minecraft.server.v1_11_R1.DefinedStructureManager;
import net.minecraft.server.v1_11_R1.MinecraftKey;
import net.minecraft.server.v1_11_R1.MinecraftServer;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_11_R1.CraftServer;
import org.bukkit.craftbukkit.v1_11_R1.CraftWorld;

public class NMS_v1_11_R1 implements NMSInterface {

	@Override
	public void saveStructure(String structureName, Location location) {
		MinecraftServer server = ((CraftServer) Bukkit.getServer()).getServer();
		DefinedStructureManager structureManager = ((CraftWorld) location.getWorld()).getHandle().y();
		DefinedStructure structure = structureManager.b(server, new MinecraftKey(structureName));
		if(structure != null){
			structureManager.c(server, new MinecraftKey(structureName));
		}
	}

}
