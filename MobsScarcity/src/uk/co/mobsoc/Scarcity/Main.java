package uk.co.mobsoc.Scarcity;

import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin{
	public static Main main;
	public String worldName="war";

	public void onEnable(){
		main = this;
		new InhibitListener(this);
		SaplingPlanter sp = new SaplingPlanter();
		sp.setTimer();
	}
	
	public void onDisable(){
		
	}
}
