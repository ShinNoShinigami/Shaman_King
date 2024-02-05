package com.shin.shaman_king;

import com.shin.shaman_king.network.ShamanKingVariables;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.player.EntityItemPickupEvent;
import net.minecraftforge.event.level.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.Random;

@Mod.EventBusSubscriber
public class AASAProcedure {
	@SubscribeEvent
	public static void onBlockBreak(BlockEvent.BreakEvent event) {
		event.getPlayer().getCapability(ShamanKingVariables.PLAYER_VARIABLES_CAPABILITY, null).ifPresent(capability -> {
			capability.Furyoku = 5;
			capability.syncPlayerVariables(event.getPlayer());
		});
	}
	@SubscribeEvent
	public static void onEntityDeath(LivingDeathEvent event){
		ItemEntity entityToSpawn = new ItemEntity(event.getEntity().level(), event.getEntity().getX(), event.getEntity().getY(), event.getEntity().getZ(), new ItemStack(Blocks.DIRT));
		event.getEntity().level().addFreshEntity(entityToSpawn);
	}
	@SubscribeEvent
	public static void onSoulPickup(EntityItemPickupEvent event){
		ItemStack itemstack = event.getItem().getItem();
		if (!itemstack.hasTag()) {
			int rangeMin = 0;
			int rangeMax = 101;
			Random r = new Random();
			int SpiritType = r.nextInt(rangeMax - rangeMin) + rangeMin;
			if(SpiritType == 100){
				int ReiVal = r.nextInt(1000000)+100000;
				itemstack.getOrCreateTag().putString("Spirit Type", "Kami");
				itemstack.getOrCreateTag().putDouble("Reiryoku", ReiVal);
			}
			else if (SpiritType >= 90){
				itemstack.getOrCreateTag().putString("Spirit Type", "Seireitei");
				int ReiVal = r.nextInt(100) < 1?
						r.nextInt(50001)+50000:
						r.nextInt(100) < 10 ?
						r.nextInt(40001)+10000:
						r.nextInt(9001)+1000;
				itemstack.getOrCreateTag().putDouble("Reiryoku", ReiVal);
			}
			else{
				int ReiVal = r.nextInt(1001);
				itemstack.getOrCreateTag().putString("Spirit Type", "Seibutsu");
				itemstack.getOrCreateTag().putDouble("Reiryoku", ReiVal);
			}
		}
	}
}
