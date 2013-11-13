package uk.co.mobsoc.Scarcity;

import net.minecraft.server.v1_6_R3.Material;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.Biome;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Item;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockGrowEvent;
import org.bukkit.event.block.BlockSpreadEvent;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.player.PlayerFishEvent;

public class InhibitListener implements Listener{
	public static int ghastCount = 0, blazeCount = 0, mooshroomCount = 0;

	public InhibitListener(Main main) {
		Bukkit.getPluginManager().registerEvents(this, main);
	}

	@EventHandler
	public void onGrow(BlockGrowEvent event){
		System.out.println("Growing "+event.getNewState().getType());
		event.setCancelled(true);
	}
	
	@EventHandler
	public void onSpread(BlockSpreadEvent event){
		System.out.println("Spreading "+event.getNewState().getType());
		if(event.getNewState().getBlock()!=Material.FIRE){
			event.setCancelled(true);
		}
	}
	
	@EventHandler
	public void onSpawn(CreatureSpawnEvent event){
		if(event.isCancelled()){ return ; }
		event.setCancelled(true);
		Location l = event.getEntity().getLocation();
		Biome b = l.getBlock().getBiome();
		EntityType et = event.getEntityType();
		if(b==Biome.OCEAN && et == EntityType.SQUID){
			event.setCancelled(false);
		}else if((b==Biome.JUNGLE || b==Biome.JUNGLE_HILLS) && et == EntityType.OCELOT){
			event.setCancelled(false);
		}else if(b==Biome.SWAMPLAND && et == EntityType.SLIME){
			event.setCancelled(false);
		}else if(b==Biome.EXTREME_HILLS && et == EntityType.SHEEP){
			event.setCancelled(false);
		}else if(b==Biome.HELL && ( et == EntityType.PIG_ZOMBIE || et == EntityType.GHAST || et == EntityType.MAGMA_CUBE || et==EntityType.BLAZE)){
			event.setCancelled(false);
		}else if(b==Biome.MUSHROOM_ISLAND && et==EntityType.MUSHROOM_COW){
			event.setCancelled(false);
		}else if((b==Biome.TAIGA || b == Biome.TAIGA_HILLS) && et == EntityType.WOLF){
			event.setCancelled(false);
		}else if((b==Biome.ICE_PLAINS || b==Biome.PLAINS) && et == EntityType.COW){
			event.setCancelled(false);
		}else if((b==Biome.FOREST || b==Biome.FOREST_HILLS || b==Biome.PLAINS) && et == EntityType.PIG){
			event.setCancelled(false);
		}else if((b==Biome.FROZEN_OCEAN || b == Biome.FROZEN_RIVER || b==Biome.PLAINS) && et == EntityType.CHICKEN){
			event.setCancelled(false);
		}else if( et == EntityType.CAVE_SPIDER || et == EntityType.SPIDER || et == EntityType.BAT || et == EntityType.SKELETON || et == EntityType.CREEPER || et == EntityType.ZOMBIE || et == EntityType.ENDERMAN || et == EntityType.IRON_GOLEM || et == EntityType.SNOWMAN || et == EntityType.HORSE || et == EntityType.SILVERFISH || et == EntityType.VILLAGER){
			event.setCancelled(false);
		}
		
		if(event.isCancelled()){
			// Now we know that we have an invalid spawn for this biome, why not spawn a
			// valid mob for this area?
			if(b == Biome.HELL){
				if(ghastCount<4){
					event.getEntity().getWorld().spawnEntity(l, EntityType.GHAST);
					ghastCount++;
				}else if(blazeCount<50){
					event.getEntity().getWorld().spawnEntity(l, EntityType.BLAZE);
					blazeCount++;
				}
			}else if( b == Biome.MUSHROOM_ISLAND){
				if(mooshroomCount < 10){
					event.getEntity().getWorld().spawnEntity(l, EntityType.MUSHROOM_COW);
					mooshroomCount++;
				}
			}
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
