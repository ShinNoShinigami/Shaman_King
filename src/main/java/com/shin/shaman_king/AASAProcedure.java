package com.shin.shaman_king;

import com.shin.shaman_king.network.ShamanKingVariables;
import net.minecraft.commands.CommandSource;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.monster.Blaze;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
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
			entity.getCapability(ShamanKingVariables.PLAYER_VARIABLES_CAPABILITY, null).ifPresent(capability -> {
				capability.Furyoku = 5;
				capability.syncPlayerVariables(entity);
			});
		}
	}
	@SubscribeEvent
	public static void onEntityDeath(LivingDeathEvent event){
		if (event.getEntity() instanceof Blaze){
			((Blaze) event.getEntity()).captureDrops();

		}
	}
}
