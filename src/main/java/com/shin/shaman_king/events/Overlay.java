package com.shin.shaman_king.events;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.shin.shaman_king.Shaman_King;
import com.shin.shaman_king.network.ShamanKingVariables;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Items;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderGuiEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber({Dist.CLIENT})
public class Overlay {
    private static final ResourceLocation ORACLE_BELL_OVERLAY = new ResourceLocation(Shaman_King.MODID,"textures/overlaybar.png");
    @SubscribeEvent(priority = EventPriority.NORMAL)
    public static void eventHandler(RenderGuiEvent.Pre event) {
        Player entity = Minecraft.getInstance().player;
        assert entity != null;
        double Furyoku = (int) (entity.getCapability(ShamanKingVariables.PLAYER_VARIABLES_CAPABILITY, null).orElse(new ShamanKingVariables.PlayerVariables())).Furyoku;
        double MaxFuryoku = (int) (entity.getCapability(ShamanKingVariables.PLAYER_VARIABLES_CAPABILITY, null).orElse(new ShamanKingVariables.PlayerVariables())).MaxFuryoku;
        double OSP = (int) (entity.getCapability(ShamanKingVariables.PLAYER_VARIABLES_CAPABILITY, null).orElse(new ShamanKingVariables.PlayerVariables())).OSP;
        double MaxOSP = (int) (entity.getCapability(ShamanKingVariables.PLAYER_VARIABLES_CAPABILITY, null).orElse(new ShamanKingVariables.PlayerVariables())).MaxOSP;
        double OBRed = (entity.getCapability(ShamanKingVariables.PLAYER_VARIABLES_CAPABILITY, null).orElse(new ShamanKingVariables.PlayerVariables())).OBRed;
        double OBGreen = (entity.getCapability(ShamanKingVariables.PLAYER_VARIABLES_CAPABILITY, null).orElse(new ShamanKingVariables.PlayerVariables())).OBGreen;
        double OBBlue = (entity.getCapability(ShamanKingVariables.PLAYER_VARIABLES_CAPABILITY, null).orElse(new ShamanKingVariables.PlayerVariables())).OBBlue;
        int w = event.getWindow().getGuiScaledWidth();
        int h = event.getWindow().getGuiScaledHeight();
        int FuryokuBar = (int) ((1 - (Furyoku / MaxFuryoku)) * 64);
        int OSPBar = (int) ((1 - (OSP / MaxFuryoku)) * 64);
        int MaxOSPBar = (int) (32 - ((MaxOSP / MaxFuryoku) * 64));
        boolean OB2021 = true;
        RenderSystem.disableDepthTest();
        RenderSystem.depthMask(false);
        RenderSystem.enableBlend();
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.blendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
        RenderSystem.setShaderColor(1, 1, 1, 1);
        event.getGuiGraphics().blit(ORACLE_BELL_OVERLAY, 20, h / 2 - 32, 93, 0, 6, 64);
        event.getGuiGraphics().blit(ORACLE_BELL_OVERLAY, 27, h / 2 - 32, 100, 0, 5, 64);
        event.getGuiGraphics().blit(ORACLE_BELL_OVERLAY, 20, h / 2 - 32, 80, 0, 6, FuryokuBar);
        event.getGuiGraphics().blit(ORACLE_BELL_OVERLAY, 27, h / 2 - 32, 87, 0, 5, OSPBar);
        event.getGuiGraphics().blit(ORACLE_BELL_OVERLAY, 11, h / 2 + 38, 41, 19, 32, 22);
        event.getGuiGraphics().blit(ORACLE_BELL_OVERLAY, 27, h / 2 + MaxOSPBar, 100, 65, 5, 5);
        if (entity.getMainHandItem().getItem() == Items.APPLE){
            event.getGuiGraphics().blit(ORACLE_BELL_OVERLAY, 3, h / 2 - 52, 33, 0, 46, 18);
        }
        else{
            event.getGuiGraphics().blit(ORACLE_BELL_OVERLAY, 11, h / 2 - 53, 41, 42, 30, 15);
        }
        event.getGuiGraphics().drawString(Minecraft.getInstance().font, Furyoku + " " + OBRed + " " + OBGreen + " " + OBBlue, w / 2  -180, h / 2  -94, -1, false);

        RenderSystem.setShaderColor((float) OBRed, (float) OBGreen, (float) OBBlue, 1);
        event.getGuiGraphics().blit(ORACLE_BELL_OVERLAY, 10, h / 2 - 38, 0, 0, 32, 76);
    }
}
