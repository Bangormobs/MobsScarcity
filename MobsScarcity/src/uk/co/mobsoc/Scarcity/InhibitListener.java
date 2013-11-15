package uk.co.mobsoc.Scarcity;

import java.util.ArrayList;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.TreeType;
import org.bukkit.block.Biome;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Item;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockGrowEvent;
import org.bukkit.event.block.BlockSpreadEvent;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.world.StructureGrowEvent;

public class InhibitListener implements Listener{
	public static int ghastCount = 0, blazeCount = 0, mooshroomCount = 0;
	public Random rand = new Random();
	
	public InhibitListener(Main main) {
		Bukkit.getPluginManager().registerEvents(this, main);
	}

	// While in the future I may add a config setup for this, I will use hard-coded config for now.	
	public ArrayList<EntityType> getAllowedEntities(Biome b){
		ArrayList<EntityType> entities = new ArrayList<EntityType>();
		entities.add(EntityType.CREEPER);
		entities.add(EntityType.ZOMBIE);
		entities.add(EntityType.SKELETON);
		entities.add(EntityType.SPIDER);
		entities.add(EntityType.CAVE_SPIDER);
		entities.add(EntityType.SILVERFISH);
		entities.add(EntityType.BAT);
		boolean sorted=false;
		if(b == Biome.FOREST || b == Biome.FOREST_HILLS){
			entities.add(EntityType.PIG);
			sorted = true;
		}else if(b == Biome.DESERT || b == Biome.DESERT_HILLS){
			sorted = true;
		}else if(b == Biome.PLAINS){
			entities.add(EntityType.CHICKEN);
			entities.add(EntityType.COW);
			sorted = true;
		}else if(b == Biome.SWAMPLAND){
			entities.add(EntityType.SLIME);
			sorted = true;
		}else if(b == Biome.JUNGLE || b == Biome.JUNGLE_HILLS){
			entities.add(EntityType.OCELOT);
			entities.add(EntityType.PIG);
			sorted = true;
		}else if(b == Biome.ICE_PLAINS || b == Biome.ICE_MOUNTAINS){
			entities.add(EntityType.COW);
			sorted = true;
		}else if(b == Biome.TAIGA || b == Biome.TAIGA_HILLS){
			entities.add(EntityType.WOLF);
			sorted = true;
		}else if(b == Biome.EXTREME_HILLS || b == Biome.SMALL_MOUNTAINS){
			entities.add(EntityType.SHEEP);
			sorted = true;
		}else if(b == Biome.OCEAN || b == Biome.FROZEN_OCEAN){
			entities.add(EntityType.SQUID);
			sorted = true;
		}else if(b == Biome.MUSHROOM_ISLAND || b == Biome.MUSHROOM_SHORE){
			entities.add(EntityType.MUSHROOM_COW);
			sorted = true;
		}else if(b == Biome.HELL){
			entities.clear();
			entities.add(EntityType.BLAZE);
			entities.add(EntityType.GHAST);
			entities.add(EntityType.WITHER);
			entities.add(EntityType.SKELETON);
			sorted = true;
		}else if(b == Biome.RIVER || b == Biome.FROZEN_RIVER || b == Biome.BEACH){
			sorted = true;
		}
		if(!sorted){
			System.out.println("Unsure how to deal with Biome "+b+" in getAllowedEntities() - Assuming hostiles only");

		}
		return entities;
	}
	
	// While in the future I may add a config setup for this, I will use hard-coded config for now.	
	public boolean isTreeAllowed(Biome b, TreeType t){
		if(b == Biome.FOREST || b == Biome.FOREST_HILLS){
			if(t == TreeType.BIRCH || t == TreeType.TREE){
				return true;
			}
			return false;
		}else if(b == Biome.DESERT || b == Biome.DESERT_HILLS){
			return false;
		}else if(b == Biome.PLAINS){
			if(t== TreeType.TREE){
				return true;
			}
			return false;
		}else if(b == Biome.SWAMPLAND){
			if(t == TreeType.SWAMP){
				return true;
			}
			return false;
		}else if(b == Biome.JUNGLE || b == Biome.JUNGLE_HILLS){
			if(t == TreeType.JUNGLE || t == TreeType.JUNGLE_BUSH || t == TreeType.BIG_TREE || t == TreeType.SMALL_JUNGLE){
				return true;
			}
			return false;
		}else if(b == Biome.ICE_PLAINS || b == Biome.ICE_MOUNTAINS){
			if(t == TreeType.REDWOOD || t == TreeType.TALL_REDWOOD){
				return true;
			}
			return false;
		}else if(b == Biome.TAIGA || b == Biome.TAIGA_HILLS){
			if(t == TreeType.REDWOOD || t == TreeType.TALL_REDWOOD){
				return true;
			}
			return false;
		}else if(b == Biome.EXTREME_HILLS || b == Biome.SMALL_MOUNTAINS){
			return false;
		}else if(b == Biome.OCEAN || b == Biome.FROZEN_OCEAN){
			return false;
		}else if(b == Biome.MUSHROOM_ISLAND || b == Biome.MUSHROOM_SHORE){
			if(t == TreeType.RED_MUSHROOM || t == TreeType.BROWN_MUSHROOM){
				return true;
			}
			return false;
		}else if(b == Biome.HELL){
			return false;
		}else if(b == Biome.RIVER || b == Biome.FROZEN_RIVER){
			return false;
		}
		System.out.println("Unsure how to deal with Biome "+b+" in isTreeAllowed() - Automatically denying");

		return false;
	}
	// While in the future I may add a config setup for this, I will use hard-coded config for now.
	public boolean isResourceAllowed(Biome b, Material m){
		if(m == Material.FIRE){ return true; }
		if(b == Biome.FOREST || b == Biome.FOREST_HILLS){
			return false;
		}else if(b == Biome.DESERT || b == Biome.DESERT_HILLS){
			if(m == Material.CACTUS || m == Material.PUMPKIN_STEM || m == Material.MELON_STEM || m == Material.PUMPKIN || m == Material.MELON_BLOCK){
				return true;
			}
			return false;
		}else if(b == Biome.PLAINS){
			if(m == Material.CROPS || m == Material.CARROT || m == Material.POTATO || m == Material.GRASS){
				return true;
			}
			return false;
		}else if(b == Biome.SWAMPLAND){
			if(m == Material.RED_MUSHROOM || m == Material.BROWN_MUSHROOM || m == Material.GRASS || m == Material.VINE){
				return true;
			}
			return false;
		}else if(b == Biome.JUNGLE || b == Biome.JUNGLE_HILLS){
			if(m == Material.COCOA || m == Material.GRASS || m == Material.VINE){
				return true;
			}
			return false;
		}else if(b == Biome.ICE_PLAINS || b == Biome.ICE_MOUNTAINS){
			if(m == Material.GRASS){
				return true;
			}
			return false;
		}else if(b == Biome.TAIGA || b == Biome.TAIGA_HILLS){
			if(m == Material.GRASS){
				return true;
			}
			return false;
		}else if(b == Biome.EXTREME_HILLS || b == Biome.SMALL_MOUNTAINS){
			if(m == Material.BROWN_MUSHROOM || m == Material.RED_MUSHROOM || m == Material.MYCEL || m == Material.GRASS){
				return true;
			}
			return false;
		}else if(b == Biome.OCEAN || b == Biome.FROZEN_OCEAN){
			return false;
		}else if(b == Biome.MUSHROOM_ISLAND || b == Biome.MUSHROOM_SHORE || m == Material.GRASS){
			if(m == Material.BROWN_MUSHROOM || m == Material.RED_MUSHROOM || m == Material.MYCEL){
				return true;
			}
			return false;
		}else if(b == Biome.HELL){
			if(m == Material.BROWN_MUSHROOM || m == Material.RED_MUSHROOM || m == Material.NETHER_WARTS){
				return true;
			}
			return false;
		}else if(b == Biome.RIVER || b == Biome.FROZEN_RIVER){
			if(m == Material.SUGAR_CANE_BLOCK){
				return true;
			}
			return false;
		}else if(b == Biome.BEACH){
			if(m == Material.SUGAR_CANE_BLOCK){
				return true;
			}
			return false;
		}
		System.out.println("Unsure how to deal with Biome "+b+" in isResourceAllowed() - Automatically denying");
		return false;
	}



	@EventHandler
	public void onGrow(BlockGrowEvent event){
		if(!isResourceAllowed(event.getBlock().getBiome(), event.getNewState().getType())){
			event.setCancelled(true);
			System.out.println("Stopped "+event.getNewState().getType()+ " from growing in "+event.getBlock().getBiome());
		}
	}
	
	@EventHandler
	public void onTreeGrow(StructureGrowEvent event){
		if(!isTreeAllowed(event.getBlocks().get(0).getBlock().getBiome(), event.getSpecies())){
			event.setCancelled(true);
			System.out.println("Stopped "+event.getSpecies()+ " from growing in "+event.getBlocks().get(0).getBlock().getBiome());

		}
	}
	
	@EventHandler
	public void onSpread(BlockSpreadEvent event){
		if(!isResourceAllowed(event.getBlock().getBiome(), event.getNewState().getType())){
			event.setCancelled(true);
			System.out.println("Stopped "+event.getNewState().getType()+ " from spreading in "+event.getBlock().getBiome());

		}
	}
	
	@EventHandler
	public void onPlayerBoneMeal(PlayerInteractEvent event){
		if(event.hasItem()){
			if(event.getAction() == Action.RIGHT_CLICK_BLOCK){
				if(event.getItem().getType() == Material.INK_SACK){
					if(event.getItem().getDurability() == 15){
						event.setCancelled(true);
					}
				}
			}
		}
	}
	
	@EventHandler
	public void onSpawn(CreatureSpawnEvent event){
		if(event.isCancelled()){ return ; }
		// This is already a Plugin-forced spawn. Ignore it to avoid inf-loop :3
		if(event.getSpawnReason() == SpawnReason.CUSTOM){ return; }
		Location l = event.getLocation();
		Biome b = l.getBlock().getBiome();
		ArrayList<EntityType> list = getAllowedEntities(b);
		event.setCancelled(true);
		//Ignore natural spawn info... but let's spawn something of our own maybe?
		EntityType eT = null;
		if(list.size()>0){
			eT = list.get(rand.nextInt(list.size()));
		}
		if(eT == EntityType.GHAST){
			// 4 ghasts at once. We don't want these at the normal consistency of say... pigs or chickens
			if(ghastCount<4){
				ghastCount++;
			}else{
				eT = null;
			}
		}
		if(eT == EntityType.BLAZE){
			if(blazeCount<50){
				blazeCount++;
			}else{
				eT = null;
			}
		}
		if(eT == EntityType.MUSHROOM_COW){
			if(mooshroomCount < 10){
				mooshroomCount++;
			}else{
				eT = null;
			}
		}
		if(eT == EntityType.ZOMBIE || eT == EntityType.SKELETON || eT == EntityType.CREEPER || eT == EntityType.SPIDER || eT == EntityType.CAVE_SPIDER){
			if(l.getBlock().getLightLevel() >= 8){
				eT = null;
			}
		}else if(eT == EntityType.CHICKEN || eT == EntityType.COW || eT == EntityType.PIG || eT == EntityType.MUSHROOM_COW || eT == EntityType.HORSE){
			if(l.getBlock().getLightLevel() <= 4){
				eT = null;
			}
		}
		if(eT != null){
			event.getEntity().getWorld().spawnEntity(l, eT);
		}
	}
	
	@EventHandler
	public void onMonsterDied(EntityDeathEvent event){
		if(event.getEntityType() == EntityType.GHAST){
			if(ghastCount > 0){
				ghastCount -- ;
			}
		}else if(event.getEntityType() == EntityType.BLAZE){
			if(blazeCount>0){
				blazeCount --;
			}
		}else if(event.getEntityType() == EntityType.MUSHROOM_COW){
			if(mooshroomCount>0){
				mooshroomCount --;
			}
		}
	}
	
	
	@EventHandler
	public void onFish(PlayerFishEvent event){
		if(event.getState() == PlayerFishEvent.State.CAUGHT_FISH){
			Biome b = event.getHook().getLocation().getBlock().getBiome();
			if(b == Biome.OCEAN){
				Item i = (Item) event.getCaught();
				if(i.getItemStack()!=null){
					// If lucky - set to another item?
					//i.setItemStack()
				}
			}else if(b == Biome.RIVER){
				
			}else{
				event.setCancelled(true);
			}
		}
	}

}
