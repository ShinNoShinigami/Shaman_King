package com.shin.shaman_king;

import com.shin.shaman_king.network.ShamanKingVariables;
import net.minecraft.world.entity.Entity;
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
		execute(event, event.getPlayer());
	}

	private static void execute(@Nullable Event event, Entity entity) {
		if (entity == null)
			return;
		{
			double _setval = (entity.getCapability(ShamanKingVariables.PLAYER_VARIABLES_CAPABILITY, null).orElse(new ShamanKingVariables.PlayerVariables())).Furyoku + 5;
			double rangeMin = 0.0f;
			double rangeMax = 1.0f;
			Random r = new Random();
			entity.getCapability(ShamanKingVariables.PLAYER_VARIABLES_CAPABILITY, null).ifPresent(capability -> {
				capability.Furyoku = _setval;
				capability.OBRed = rangeMin + (rangeMax - rangeMin) * r.nextDouble();
				capability.OBBlue = rangeMin + (rangeMax - rangeMin) * r.nextDouble();
				capability.OBGreen = rangeMin + (rangeMax - rangeMin) * r.nextDouble();
				capability.syncPlayerVariables(entity);
			});
		}
	}
}
