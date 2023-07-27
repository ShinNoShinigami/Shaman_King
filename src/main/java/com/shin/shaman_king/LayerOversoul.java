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
public class LayerOversoul <T extends LivingEntity,M extends HumanoidModel<T>> extends RenderLayer<T, M>
{
    private final ModelSwordAura<T> modelSwordAura;
    private static final ResourceLocation OVERSOUL_TEX = new ResourceLocation(Shaman_King.MOD_ID, "textures/swordaura.png");
    private static final ResourceLocation OVERSOUL_COLOUR_TEX = new ResourceLocation(Shaman_King.MOD_ID, "textures/swordaura_colour.png");
    private static final ResourceLocation OVERSOUL_PLAIN_TEX = new ResourceLocation(Shaman_King.MOD_ID, "textures/swordaura_plain.png");

    public LayerOversoul(RenderLayerParent p_174493_)
    {
        super(p_174493_);
        modelSwordAura = new ModelSwordAura<>(Minecraft.getInstance().getEntityModels().bakeLayer(ClientEvents.OVERSOUL_LAYER));
    }



    public void render(PoseStack p_117349_, MultiBufferSource p_117350_, int p_117351_, T EntityLivingBaseIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {

        ItemStack itemstack = EntityLivingBaseIn.getItemBySlot(EquipmentSlot.MAINHAND);
        if (shouldRender(itemstack, EntityLivingBaseIn)) {
            System.out.println("should be rendering");
            ResourceLocation resourcelocation;

            resourcelocation = getWingsTexture(EntityLivingBaseIn);

            p_117349_.pushPose();
            p_117349_.translate(0.0F, 0.0F, 0.125F);
            this.getParentModel().copyPropertiesTo(modelSwordAura);
            modelSwordAura.setupAnim(EntityLivingBaseIn, limbSwing, limbSwingAmount, partialTicks, netHeadYaw, headPitch);
            VertexConsumer vertexconsumer = ItemRenderer.getArmorFoilBuffer(p_117350_, RenderType.armorCutoutNoCull(resourcelocation), false, EntityLivingBaseIn.getItemBySlot(EquipmentSlot.MAINHAND).hasFoil());
            modelSwordAura.renderToBuffer(p_117349_, vertexconsumer, p_117351_, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
            p_117349_.popPose();
        }
    }
    public boolean shouldRender(ItemStack stack, T entity) {
        System.out.println("Item fetched");
        return stack.getItem() == Items.DIAMOND_SWORD;
    }
    public ResourceLocation getWingsTexture(T entity)
    {
        System.out.println("Texture fetched");
        return OVERSOUL_TEX;
    }

}

