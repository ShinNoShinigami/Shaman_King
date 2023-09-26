package com.shin.shaman_king;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Items;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.client.event.RenderLivingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.function.Function;
@Mod.EventBusSubscriber(value = Dist.CLIENT, modid = Shaman_King.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ClientEvents {
    public static ModelLayerLocation OVERSOUL_LAYER = new ModelLayerLocation(new ResourceLocation("minecraft:player"), "testoversoul");
    @Mod.EventBusSubscriber(modid = Shaman_King.MOD_ID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class ModBusEvents {

        @SubscribeEvent
        public static void registerLayer(EntityRenderersEvent.RegisterLayerDefinitions event) {
            event.registerLayerDefinition(OVERSOUL_LAYER, TestOversoul::createBodyLayer);
        }

        @SubscribeEvent
        public static void construct(EntityRenderersEvent.AddLayers event) {
            addLayerToHumanoid(event, EntityType.ARMOR_STAND, LayerOversoul::new);
            addLayerToHumanoid(event, EntityType.ZOMBIE, LayerOversoul::new);
            addLayerToHumanoid(event, EntityType.SKELETON, LayerOversoul::new);
            addLayerToHumanoid(event, EntityType.HUSK, LayerOversoul::new);
            addLayerToHumanoid(event, EntityType.DROWNED, LayerOversoul::new);
            addLayerToHumanoid(event, EntityType.STRAY, LayerOversoul::new);

            addLayerToPlayerSkin(event, "default", LayerOversoul::new);
            addLayerToPlayerSkin(event, "slim", LayerOversoul::new);
        }

        @SuppressWarnings({"rawtypes", "unchecked"})
        private static <E extends Player, M extends HumanoidModel<E>>
        void addLayerToPlayerSkin(EntityRenderersEvent.AddLayers event, String skinName, Function<LivingEntityRenderer<E, M>, ? extends RenderLayer<E, M>> factory) {
            LivingEntityRenderer renderer = event.getSkin(skinName);
            if (renderer != null) renderer.addLayer(factory.apply(renderer));
        }

        private static <E extends LivingEntity, M extends HumanoidModel<E>>
        void addLayerToHumanoid(EntityRenderersEvent.AddLayers event, EntityType<E> entityType, Function<LivingEntityRenderer<E, M>, ? extends RenderLayer<E, M>> factory) {
            LivingEntityRenderer<E, M> renderer = event.getRenderer(entityType);
            if (renderer != null) renderer.addLayer(factory.apply(renderer));
        }

    }
    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public void onSpiritEntRender(RenderLivingEvent.Pre event) {
        event.getEntity();
        if (event.getEntity().isInWater()) {
            Player player = Minecraft.getInstance().player;
            boolean cancelRender = false;
            assert player != null;
            if (player.getOffhandItem().getItem() == Items.APPLE) {
                cancelRender = true;
            }
            if (cancelRender) {
                event.setCanceled(true);
            }
        }
    }
}
