package fr.alluniverse.AllProtect;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.server.PluginEnableEvent;

import ru.tehkode.permissions.PermissionManager;
import ru.tehkode.permissions.PermissionUser;
import ru.tehkode.permissions.bukkit.PermissionsEx;

public class AllProtectPlayerListener implements Listener 
{

	public static String pluginVersion;
	private AllProtect plugin;
	
	public AllProtectPlayerListener(AllProtect plugin)
	{
		this.plugin = plugin;
				
		
	}
	
	
	@EventHandler(priority = EventPriority.MONITOR)
	public void onPluginEnable(PluginEnableEvent e)
	{
		if(plugin.pManager == null && Bukkit.getServer().getPluginManager().isPluginEnabled("PermissionsEx"))
		{
			plugin.pManager = PermissionsEx.getPermissionManager();
			
			 
			
		}
	}

	@EventHandler(priority = EventPriority.NORMAL)
	public void checkVersion(PlayerJoinEvent event) throws IOException
	{
		Player p = event.getPlayer();
		
		URL url_verFile = new URL("http://plugins.all-universe.com/AllProtect/version");
		URLConnection conn = url_verFile.openConnection();
		conn.setConnectTimeout(5000);
		conn.setDoOutput(true);
		
		
		BufferedReader getVer = new BufferedReader(new InputStreamReader(conn.getInputStream()));
		String checkVer = getVer.readLine();
		

		if(pluginVersion.equals(checkVer))
		{
			
		}
		else
		{
			System.out.println("[AllProtect] Nouvelle version du plugin disponible ->" + checkVer );
			if(p.isOp())
			{
				event.setJoinMessage("[AllProtect] - Une nouvelle version est disponible -> " + pluginVersion + " -> " + checkVer);	
			}
		}	
	}
	
	@EventHandler(priority = EventPriority.LOWEST)
	public void verifLuncher(PlayerLoginEvent event) throws IOException
	{
		Player p = event.getPlayer();

  	  	PermissionManager pex = PermissionsEx.getPermissionManager();
  	  	boolean hasPerm = pex.has(p, "AllProtect.allow");
		
		URL url_verif = new URL("http://plugins.all-universe.com/AllProtect/FileAccess/" + event.getPlayer().getName());
		URLConnection conVerif = url_verif.openConnection();
		conVerif.setConnectTimeout(5000);
		conVerif.setDoOutput(true);
		
		try 		   
		{
			BufferedReader getKey = new BufferedReader(new InputStreamReader(conVerif.getInputStream()));
			String checkKey = getKey.readLine();
			
			URL url_banni = new URL("http://plugins.all-universe.com/AllProtect/AllVerif.php?k=" + checkKey);		
			URLConnection conBan = url_banni.openConnection();
			conBan.setConnectTimeout(5000);
			conBan.setDoOutput(true);
			BufferedReader getBan = new BufferedReader(new InputStreamReader(conBan.getInputStream()));
			String checkBan = getBan.readLine();

			if(checkBan.equals("BANNI"))
			{
				event.disallow(PlayerLoginEvent.Result.KICK_OTHER, "Votre compte est bloqué ! Veuillez contacter un administrateur.");
			}
	      
		}	      
		catch (Exception g)       
		{    	  
			if(!hasPerm)    	  
			{	    		  
				event.disallow(PlayerLoginEvent.Result.KICK_OTHER, "Seul les connexions depuis le launcher ALLUNIVERSE sont autorisées !");			    				   	  
  
			}   
		}	
	}
}
