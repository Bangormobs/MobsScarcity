package uk.co.mobsoc.Scarcity;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Item;

public class SaplingPlanter implements Runnable{
	@Override
	public void run() {
		setTimer();
		for(Item e : Bukkit.getWorld(Main.main.worldName).getEntitiesByClass(Item.class)){
			if(e.getTicksLived() < 100){ continue; }
			if(e.getItemStack().getType() == Material.SAPLING){
				Material a = e.getLocation().getBlock().getType();
				if(a == Material.AIR || a == Material.SNOW || a == Material.LONG_GRASS){
					Material m = e.getLocation().getBlock().getRelative(0, -1, 0).getType();
					if(m == Material.GRASS || m == Material.DIRT){
						e.getLocation().getBlock().setTypeIdAndData(Material.SAPLING.getId(), (byte)e.getItemStack().getDurability(),true);
						if(e.getItemStack().getAmount() <= 1){
							e.remove();
						}else{
							e.getItemStack().setAmount(e.getItemStack().getAmount()-1);
						}
					}
				}
			}
			if((e.getItemStack().getType() == Material.RED_MUSHROOM || e.getItemStack().getType() == Material.BROWN_MUSHROOM)){
				if(e.getLocation().getBlock().getType() == Material.AIR){
					Material m = e.getLocation().getBlock().getRelative(0, -1, 0).getType();
					if(m == Material.GRASS || m == Material.DIRT || m == Material.STONE || m == Material.GRAVEL){
						e.getLocation().getBlock().setTypeIdAndData(e.getItemStack().getTypeId(), (byte)e.getItemStack().getDurability(),true);
						if(e.getItemStack().getAmount() <= 1){
							e.remove();
						}else{
							e.getItemStack().setAmount(e.getItemStack().getAmount()-1);
						}
					}
				}
			}
		}
	}
	
	public void setTimer(){
		Bukkit.getScheduler().scheduleSyncDelayedTask(Main.main.main, this, 10);
	}

}
