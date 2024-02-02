package com.shin.shaman_king;

import com.shin.shaman_king.network.ShamanKingVariables;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.event.level.BlockEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import javax.annotation.Nullable;
import java.util.Random;

@Mod.EventBusSubscriber
public class AASAProcedure {
	@SubscribeEvent
	public static void onBlockBreak(BlockEvent.BreakEvent event) {
		if ((event.getLevel().getBlockState(BlockPos.containing(event.getPos().getX(), event.getPos().getY(), event.getPos().getZ()))).getBlock() == Blocks.POLISHED_DIORITE_SLAB) {

		}
		execute(event, event.getLevel(), event.getPos().getX(), event.getPos().getY(), event.getPos().getZ(), event.getPlayer());
	}
	private static void execute(@Nullable Event event, LevelAccessor world, double x, double y, double z, Entity entity) {
		{
			double rangeMin = 0.0f;
			double rangeMax = 1.0f;
			Random r = new Random();
			entity.getCapability(ShamanKingVariables.PLAYER_VARIABLES_CAPABILITY, null).ifPresent(capability -> {
				capability.Furyoku = 5;
				capability.OBRed = rangeMin + (rangeMax - rangeMin) * r.nextDouble();
				capability.OBBlue = rangeMin + (rangeMax - rangeMin) * r.nextDouble();
				capability.OBGreen = rangeMin + (rangeMax - rangeMin) * r.nextDouble();
				capability.syncPlayerVariables(entity);
			});
		}
	}
}
