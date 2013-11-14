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
			Material blockHere = e.getLocation().getBlock().getType(), blockBelow = e.getLocation().getBlock().getRelative(0, -1,  0).getType();
			if(e.getItemStack().getType() == Material.SAPLING){
				if(blockHere == Material.AIR || 
				   blockHere == Material.SNOW || 
				   blockHere == Material.LONG_GRASS){
					if(blockBelow == Material.GRASS || 
					   blockBelow == Material.DIRT){
						setBlockHere(e);
					}
				}
			}
			if(e.getItemStack().getType() == Material.RED_MUSHROOM ||
			   e.getItemStack().getType() == Material.BROWN_MUSHROOM){
				if(blockHere == Material.AIR){
					if(blockBelow == Material.GRASS ||
					   blockBelow == Material.DIRT ||
					   blockBelow == Material.STONE ||
					   blockBelow == Material.GRAVEL){
						setBlockHere(e);
					}
				}
			}
			// TODO: Update to add 1.7.2 flower types
			if(e.getItemStack().getType() == Material.RED_ROSE ||
			   e.getItemStack().getType() == Material.YELLOW_FLOWER){
				if(blockHere == Material.AIR ||
				   blockHere == Material.LONG_GRASS){
					if(blockBelow == Material.GRASS || 
					   blockBelow == Material.DIRT){
						setBlockHere(e);
					}
				}
			}
		}
	}
	
	/* Sets the block to one of the items throw into the locaton. Use the setBlockHere(item, material)
	 * if the item is a different ID to the block
	 */
	public void setBlockHere(Item item){
		int itemId = item.getItemStack().getTypeId();
		byte itemDura = (byte)item.getItemStack().getDurability();
		int amount = item.getItemStack().getAmount();
		item.getLocation().getBlock().setTypeIdAndData(itemId, itemDura,true);
		if(amount <= 1){
			item.remove();
		}else{
			item.getItemStack().setAmount(amount-1);
		}
	}
	
	public void setBlockHere(Item item, Material m){
		item.getLocation().getBlock().setType(m);
		int amount = item.getItemStack().getAmount();
		if(amount <= 1){
			item.remove();
		}else{
			item.getItemStack().setAmount(amount-1);
		}
	}
	
	public void setTimer(){
		Bukkit.getScheduler().scheduleSyncDelayedTask(Main.main.main, this, 10);
	}

}
