package fr.alluniverse.AllProtect;

import java.util.logging.Logger;

import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;

import ru.tehkode.permissions.PermissionManager;


public class AllProtect extends JavaPlugin
{
	
	
	public PermissionManager pManager;

	@Override
	public void onDisable()
	{

	}
	
	@Override		
	public void onEnable()
	{ 
		PluginDescriptionFile pdf = getDescription();
		AllProtectPlayerListener.pluginVersion = pdf.getVersion();
		
		
		PluginManager pm = getServer().getPluginManager();
		pm.registerEvents(new AllProtectPlayerListener(this), this);
		
		

	}
	

}
