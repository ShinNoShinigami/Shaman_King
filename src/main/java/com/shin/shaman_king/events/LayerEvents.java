package com.shin.shaman_king.events;

import com.shin.shaman_king.Shaman_King;
import com.shin.shaman_king.entities.ShamanKingEntities;
import com.shin.shaman_king.entities.layers.LayerOracleBell;
import com.shin.shaman_king.entities.layers.LayerOversoul;
import com.shin.shaman_king.entities.models.oversouls.TestOversoul;
import com.shin.shaman_king.entities.renderer.MorphinTest;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

import java.util.function.Function;

@Mod.EventBusSubscriber(value = Dist.CLIENT, modid = Shaman_King.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class LayerEvents {
    @OnlyIn(Dist.CLIENT)
    public static ModelLayerLocation OVERSOUL_LAYER = new ModelLayerLocation(new ResourceLocation("minecraft:player"), "testoversoul");
    @OnlyIn(Dist.CLIENT)
    public static ModelLayerLocation ORACLEBELL_LAYER = new ModelLayerLocation(new ResourceLocation("minecraft:player"), "oraclebell");
    @Mod.EventBusSubscriber(modid = Shaman_King.MODID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class ModBusEvents {
        @SubscribeEvent
        public static void registerLayer(EntityRenderersEvent.RegisterLayerDefinitions event) {
            event.registerLayerDefinition(OVERSOUL_LAYER, TestOversoul::createBodyLayer);
            event.registerLayerDefinition(ORACLEBELL_LAYER, LayerOracleBell.OracleBell::createBodyLayer);
        }

        @SubscribeEvent
        public static void construct(EntityRenderersEvent.AddLayers event) {
            addLayerToPlayerSkin(event, "default", LayerOversoul::new);
            addLayerToPlayerSkin(event, "slim", LayerOversoul::new);
            addLayerToPlayerSkin(event, "default", LayerOracleBell::new);
            addLayerToPlayerSkin(event, "slim", LayerOracleBell::new);
        }

        @SuppressWarnings({"rawtypes", "unchecked"})
        private static <E extends Player, M extends HumanoidModel<E>>
        void addLayerToPlayerSkin(EntityRenderersEvent.AddLayers event, String skinName, Function<LivingEntityRenderer<E, M>, ? extends RenderLayer<E, M>> factory) {
            LivingEntityRenderer renderer = event.getSkin(skinName);
            if (renderer != null) renderer.addLayer(factory.apply(renderer));
        }
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event)
        {
            EntityRenderers.register(ShamanKingEntities.MORPHIN_PENDULUM.get(), MorphinTest::new);
        }
    }
}
