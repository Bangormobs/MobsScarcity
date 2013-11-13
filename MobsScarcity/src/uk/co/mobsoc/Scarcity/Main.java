package uk.co.mobsoc.Scarcity;

import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin{
	public void onEnable(){
		new InhibitListener(this);
	}
	
	public void onDisable(){
		
	}
}
