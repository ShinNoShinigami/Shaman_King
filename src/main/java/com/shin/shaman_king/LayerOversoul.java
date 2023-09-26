package com.shin.shaman_king;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class LayerOversoul <T extends LivingEntity, M extends HumanoidModel<T>> extends RenderLayer<T, M> {
    private final TestOversoul<T> testOversoul;
    private static final ResourceLocation OVERSOUL_TEX = new ResourceLocation(Shaman_King.MOD_ID, "textures/testoversoul.png");

    public LayerOversoul(RenderLayerParent Parent)
    {
        super(Parent);
        testOversoul = new TestOversoul<>(Minecraft.getInstance().getEntityModels().bakeLayer(ClientEvents.OVERSOUL_LAYER));
    }



    public void render(PoseStack poseStack, MultiBufferSource multiBufferSource, int pPackedLight, T EntityLivingBaseIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {

        ItemStack itemstack = EntityLivingBaseIn.getItemBySlot(EquipmentSlot.OFFHAND);
        if (shouldRender(itemstack)) {
            ResourceLocation resourcelocation;

            resourcelocation = getTexture();

            poseStack.pushPose();
            poseStack.translate(0.0F, 0.0F, 0.0F);
            this.getParentModel().copyPropertiesTo(this.testOversoul);
            this.testOversoul.setupAnim(EntityLivingBaseIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
            VertexConsumer vertexconsumer = ItemRenderer.getArmorFoilBuffer(multiBufferSource, RenderType.armorCutoutNoCull(resourcelocation), false, itemstack.hasFoil());
            this.testOversoul.renderToBuffer(poseStack, vertexconsumer, pPackedLight, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
            poseStack.popPose();
        }
    }
    public boolean shouldRender(ItemStack stack) {
        return stack.getItem() == Items.DIAMOND_SWORD;
    }
    public ResourceLocation getTexture()
    {
        return OVERSOUL_TEX;
    }

}

