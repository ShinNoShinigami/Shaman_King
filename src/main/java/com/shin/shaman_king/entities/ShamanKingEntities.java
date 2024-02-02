package com.shin.shaman_king.entities;

import com.shin.shaman_king.Shaman_King;
import com.shin.shaman_king.entities.living.MorphinProjectile;
import com.shin.shaman_king.entities.renderer.MorphinTest;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.projectile.FishingHook;
import net.minecraft.world.flag.FeatureElement;
import net.minecraft.world.flag.FeatureFlagSet;
import net.minecraft.world.level.entity.EntityTypeTest;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.Nullable;

public class ShamanKingEntities {
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES =
            DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, Shaman_King.MODID);

    public static final RegistryObject<EntityType<MorphinProjectile>> MORPHIN_PENDULUM =
            ENTITY_TYPES.register("morphin_pendulum", () -> EntityType.Builder.<MorphinProjectile>of(MorphinProjectile::new, MobCategory.MISC)
                    .noSave().noSummon().sized(0.25F, 0.25F).clientTrackingRange(4).updateInterval(5).build("morphin_pendulum"));
    public static void register(IEventBus eventBus) {
        ENTITY_TYPES.register(eventBus);
    }

}
