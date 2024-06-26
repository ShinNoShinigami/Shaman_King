package com.shin.shaman_king.events;

import com.shin.shaman_king.Shaman_King;
import com.shin.shaman_king.entities.spirits.ISpirits;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Items;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.RenderGuiOverlayEvent;
import net.minecraftforge.client.event.RenderHandEvent;
import net.minecraftforge.client.event.RenderLivingEvent;
import net.minecraftforge.client.gui.overlay.VanillaGuiOverlay;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
@Mod.EventBusSubscriber(value = Dist.CLIENT, modid = Shaman_King.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ClientEvents {
    /*
    @OnlyIn(Dist.CLIENT)
    public static ModelLayerLocation OVERSOUL_LAYER = new ModelLayerLocation(new ResourceLocation("minecraft:player"), "testoversoul");
    @OnlyIn(Dist.CLIENT)
    public static ModelLayerLocation ORACLEBELL_LAYER = new ModelLayerLocation(new ResourceLocation("minecraft:player"), "oraclebell");
    @Mod.EventBusSubscriber(modid = Shaman_King.MODID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class ModBusEvents {
        @SubscribeEvent
        public static void registerLayer(EntityRenderersEvent.RegisterLayerDefinitions event) {
            event.registerLayerDefinition(OVERSOUL_LAYER, TestOversoul::createBodyLayer);
            event.registerLayerDefinition(ORACLEBELL_LAYER, OracleBell::createBodyLayer);
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
     */
    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    @SuppressWarnings("rawtypes")
    public void onSpiritEntRender(RenderHandEvent event) {
        //if (event.getEntity().isInWater() && event.getEntity() instanceof ISpirits) {
        Player player = Minecraft.getInstance().player;
        assert player != null;
        if (player.getOffhandItem().getItem() == Items.APPLE) {
            event.setCanceled(true);
        }
        //}
    }
    @SubscribeEvent
    public void onSpiritEntHurt(LivingAttackEvent event){
        if (event.getEntity() instanceof ISpirits){
            event.setCanceled(true);
        }
    }
    @SubscribeEvent
    public static void renderHealthBar(RenderGuiOverlayEvent event){
        if(VanillaGuiOverlay.PLAYER_HEALTH.type() == event.getOverlay()){
            event.setCanceled(true);
        }
    }
}
