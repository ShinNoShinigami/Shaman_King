package com.shin.shaman_king.events;

import com.shin.shaman_king.network.ShamanKingVariables;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.player.EntityItemPickupEvent;
import net.minecraftforge.event.level.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.Random;

@Mod.EventBusSubscriber
public class CommonEvents {
	@SubscribeEvent
	public static void onEntityDeath(LivingDeathEvent event){
		ItemEntity entityToSpawn = new ItemEntity(event.getEntity().level(), event.getEntity().getX(), event.getEntity().getY(), event.getEntity().getZ(), new ItemStack(Blocks.DIRT));
		event.getEntity().level().addFreshEntity(entityToSpawn);
	}
	@SubscribeEvent
	public static void onBlockBreak(BlockEvent.BreakEvent event){
		double rangeMin = 0f;
		double rangeMax = 1f;
		Random r = new Random();
		event.getPlayer().getCapability(ShamanKingVariables.PLAYER_VARIABLES_CAPABILITY, null).ifPresent(capability -> {
			capability.OBRed = rangeMin + (rangeMax - rangeMin) * r.nextDouble();
			capability.OBGreen = rangeMin + (rangeMax - rangeMin) * r.nextDouble();
			capability.OBBlue = rangeMin + (rangeMax - rangeMin) * r.nextDouble();
            capability.CanSeeSpirits = !capability.CanSeeSpirits;
			capability.syncPlayerVariables(event.getPlayer());
		});
        event.getPlayer().getMainHandItem().getOrCreateTag().putDouble("Blue", (event.getPlayer().getCapability(ShamanKingVariables.PLAYER_VARIABLES_CAPABILITY, null).orElse(new ShamanKingVariables.PlayerVariables())).OBBlue);
		event.getPlayer().getMainHandItem().getOrCreateTag().putDouble("Green", (event.getPlayer().getCapability(ShamanKingVariables.PLAYER_VARIABLES_CAPABILITY, null).orElse(new ShamanKingVariables.PlayerVariables())).OBGreen);
		event.getPlayer().getMainHandItem().getOrCreateTag().putDouble("Red", (event.getPlayer().getCapability(ShamanKingVariables.PLAYER_VARIABLES_CAPABILITY, null).orElse(new ShamanKingVariables.PlayerVariables())).OBRed);

	}
	@SubscribeEvent
	public static void onSoulPickup(EntityItemPickupEvent event){
		ItemStack itemstack = event.getItem().getItem();
		if (!itemstack.hasTag()) {
			int rangeMin = 0;
			int rangeMax = 101;
			Random r = new Random();
			int ReiVal;
			int SpiritType = r.nextInt(rangeMax - rangeMin) + rangeMin;
			if(SpiritType == 100){
				ReiVal = r.nextInt(1000000)+100000;
				itemstack.getOrCreateTag().putString("Spirit Type", "Kami");
				itemstack.getOrCreateTag().putDouble("Reiryoku", ReiVal);
			}
			else if (SpiritType >= 90){
				itemstack.getOrCreateTag().putString("Spirit Type", "Seireitei");
				ReiVal = r.nextInt(100) < 1?
						r.nextInt(50001)+50000:
						r.nextInt(100) < 10 ?
						r.nextInt(40001)+10000:
						r.nextInt(9001)+1000;
				itemstack.getOrCreateTag().putDouble("Reiryoku", ReiVal);
			}
			else{
				ReiVal = r.nextInt(1001);
				itemstack.getOrCreateTag().putString("Spirit Type", "Seibutsu");
				itemstack.getOrCreateTag().putDouble("Reiryoku", ReiVal);
			}
		}
	}
	@SubscribeEvent
	public static void refilFuryokuBar(TickEvent.PlayerTickEvent event){
		if (event.phase == TickEvent.Phase.END) {
			int Furyoku = (int) event.player.getCapability(ShamanKingVariables.PLAYER_VARIABLES_CAPABILITY, null).orElse(new ShamanKingVariables.PlayerVariables()).Furyoku;
			int FuryokuMax = (int) event.player.getCapability(ShamanKingVariables.PLAYER_VARIABLES_CAPABILITY, null).orElse(new ShamanKingVariables.PlayerVariables()).MaxFuryoku;
			if (Furyoku < FuryokuMax){
				event.player.getCapability(ShamanKingVariables.PLAYER_VARIABLES_CAPABILITY, null).ifPresent(capability -> {
					capability.Furyoku = Furyoku + 1;
					capability.syncPlayerVariables(event.player);
				});
			}
		}
	}
}
