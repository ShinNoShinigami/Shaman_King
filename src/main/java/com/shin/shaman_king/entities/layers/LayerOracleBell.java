package com.shin.shaman_king.entities.layers;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.shin.shaman_king.Shaman_King;
import com.shin.shaman_king.events.LayerEvents;
import com.shin.shaman_king.network.ShamanKingVariables;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public class LayerOracleBell <T extends LivingEntity, M extends HumanoidModel<T>> extends RenderLayer<T, M> {
    private final OracleBell<T> oracleBell;
    private static final ResourceLocation OVERSOUL_TEX = new ResourceLocation(Shaman_King.MODID, "textures/oraclebell.png");

    public LayerOracleBell(RenderLayerParent Parent)
    {
        super(Parent);
        oracleBell = new OracleBell<>(Minecraft.getInstance().getEntityModels().bakeLayer(LayerEvents.ORACLEBELL_LAYER));
    }



    public void render(PoseStack poseStack, MultiBufferSource multiBufferSource, int pPackedLight, T EntityLivingBaseIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
        ItemStack itemstack = EntityLivingBaseIn.getItemBySlot(EquipmentSlot.MAINHAND);
        oracleBell.dyeRed = (float) (EntityLivingBaseIn.getCapability(ShamanKingVariables.PLAYER_VARIABLES_CAPABILITY, null).orElse(new ShamanKingVariables.PlayerVariables())).OBRed;
        oracleBell.dyeGreen = (float) (EntityLivingBaseIn.getCapability(ShamanKingVariables.PLAYER_VARIABLES_CAPABILITY, null).orElse(new ShamanKingVariables.PlayerVariables())).OBGreen;
        oracleBell.dyeBlue = (float) (EntityLivingBaseIn.getCapability(ShamanKingVariables.PLAYER_VARIABLES_CAPABILITY, null).orElse(new ShamanKingVariables.PlayerVariables())).OBBlue;
        ;
        if (shouldRender(EntityLivingBaseIn)) {
            ResourceLocation resourcelocation;


            resourcelocation = getTexture();
            poseStack.pushPose();
            poseStack.translate(0.0F, 0.0F, 0.0F);
            poseStack.scale(1f, 1f, 1f);
            this.getParentModel().copyPropertiesTo(this.oracleBell);
            this.oracleBell.setupAnim(EntityLivingBaseIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
            VertexConsumer vertexconsumer = ItemRenderer.getArmorFoilBuffer(multiBufferSource, RenderType.armorCutoutNoCull(resourcelocation), false, itemstack.hasFoil());
            this.oracleBell.renderToBuffer(poseStack, vertexconsumer, pPackedLight, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
            poseStack.popPose();
        }
    }
    public boolean shouldRender(Entity test) {
        return (test.getCapability(ShamanKingVariables.PLAYER_VARIABLES_CAPABILITY, null).orElse(new ShamanKingVariables.PlayerVariables())).CanSeeSpirits;

    }
    public ResourceLocation getTexture()
    {
        return OVERSOUL_TEX;
    }

    public static class OracleBell<T extends LivingEntity> extends HumanoidModel<T> {
        private final ModelPart OracleBellLeftOther;
        private final ModelPart OracleBellLeftBody;

        public boolean hasColor;
        public float dyeRed;
        public float dyeGreen;
        public float dyeBlue;

        public OracleBell(ModelPart root) {
            super(root, RenderType::entityTranslucent);
            this.OracleBellLeftOther = root.getChild("OracleBellLeftOther");
            this.OracleBellLeftBody = root.getChild("OracleBellLeftBody");
        }

        public static LayerDefinition createBodyLayer() {
            MeshDefinition meshdefinition = HumanoidModel.createMesh(new CubeDeformation(0.0f), 0f);
            PartDefinition partdefinition = meshdefinition.getRoot();

            PartDefinition Head = partdefinition.addOrReplaceChild("Head", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.0F))
                    .texOffs(32, 0).addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.5F)), PartPose.offset(0.0F, 0.0F, 0.0F));

            PartDefinition Body = partdefinition.addOrReplaceChild("Body", CubeListBuilder.create().texOffs(0, 16).addBox(-4.0F, 0.0F, -2.0F, 8.0F, 12.0F, 4.0F, new CubeDeformation(0.0F))
                    .texOffs(16, 32).addBox(-4.0F, 0.0F, -2.0F, 8.0F, 12.0F, 4.0F, new CubeDeformation(0.25F)), PartPose.offset(0.0F, 0.0F, 0.0F));

            PartDefinition RightArm = partdefinition.addOrReplaceChild("RightArm", CubeListBuilder.create().texOffs(24, 16).addBox(-3.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.0F))
                    .texOffs(40, 32).addBox(-3.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.25F)), PartPose.offset(-5.0F, 2.0F, 0.0F));

            PartDefinition LeftArm = partdefinition.addOrReplaceChild("LeftArm", CubeListBuilder.create().texOffs(32, 0).addBox(-1.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.0F))
                    .texOffs(48, 48).addBox(-1.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.25F)), PartPose.offset(5.0F, 2.0F, 0.0F));

            PartDefinition RightLeg = partdefinition.addOrReplaceChild("RightLeg", CubeListBuilder.create().texOffs(0, 32).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.0F))
                    .texOffs(0, 32).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.25F)), PartPose.offset(-1.9F, 12.0F, 0.0F));

            PartDefinition LeftLeg = partdefinition.addOrReplaceChild("LeftLeg", CubeListBuilder.create().texOffs(16, 32).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.0F))
                    .texOffs(0, 48).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.25F)), PartPose.offset(1.9F, 12.0F, 0.0F));

            PartDefinition OracleBellLeftOther = partdefinition.addOrReplaceChild("OracleBellLeftOther", CubeListBuilder.create().texOffs(19, 17).addBox(3.0F, 3.5F, -1.35F, 0.4F, 4.0F, 2.7F, new CubeDeformation(0.0F))
                    .texOffs(1, 27).addBox(3.1F, 9.0F, -1.25F, 0.3F, 0.5F, 2.5F, new CubeDeformation(0.0F))
                    .texOffs(14, 14).addBox(3.35F, 9.3F, -0.05F, 0.3F, 2.9F, 0.5F, new CubeDeformation(0.0F))
                    .texOffs(3, 14).addBox(3.35F, 9.3F, -0.55F, 0.3F, 2.9F, 0.5F, new CubeDeformation(0.0F))
                    .texOffs(0, 14).addBox(3.0F, 1.4F, -2.1F, 0.5F, 0.6F, 4.2F, new CubeDeformation(0.0F)), PartPose.offset(5.0F, 2.0F, 0.0F));

            PartDefinition cube_r1 = OracleBellLeftOther.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(24, 0).addBox(-0.15F, -0.3F, -0.3F, 0.3F, 0.6F, 0.6F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(3.85F, 8.5F, 0.0F, -0.7854F, 0.0F, 0.0F));

            PartDefinition cube_r2 = OracleBellLeftOther.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(24, 3).addBox(-0.15F, -0.3F, -0.3F, 0.3F, 0.6F, 0.6F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(3.85F, 2.5F, 0.0F, -0.7854F, 0.0F, 0.0F));

            PartDefinition cube_r3 = OracleBellLeftOther.addOrReplaceChild("cube_r3", CubeListBuilder.create().texOffs(16, 0).addBox(-0.5F, 0.0F, -1.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(3.3F, 0.6782F, -1.5833F, 0.3927F, 0.0F, 0.0F));

            PartDefinition cube_r4 = OracleBellLeftOther.addOrReplaceChild("cube_r4", CubeListBuilder.create().texOffs(0, 20).addBox(0.0F, -2.0F, 0.0F, 0.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(3.7F, 1.7076F, 0.9273F, -1.1794F, 0.0334F, -0.0807F));

            PartDefinition cube_r5 = OracleBellLeftOther.addOrReplaceChild("cube_r5", CubeListBuilder.create().texOffs(5, 20).addBox(0.0F, -2.0F, -2.0F, 0.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(3.7F, 1.7076F, -0.9273F, 1.1794F, -0.0334F, -0.0807F));

            PartDefinition cube_r6 = OracleBellLeftOther.addOrReplaceChild("cube_r6", CubeListBuilder.create().texOffs(0, 7).addBox(-0.5F, -2.0F, 0.0F, 1.0F, 2.0F, 0.7F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(3.3F, 2.0903F, -0.0034F, 1.1781F, 0.0F, 0.0F));

            PartDefinition cube_r7 = OracleBellLeftOther.addOrReplaceChild("cube_r7", CubeListBuilder.create().texOffs(16, 9).addBox(-0.5F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(3.3F, 0.6782F, 1.5765F, -0.3927F, 0.0F, 0.0F));

            PartDefinition cube_r8 = OracleBellLeftOther.addOrReplaceChild("cube_r8", CubeListBuilder.create().texOffs(7, 14).addBox(-0.4F, -0.375F, -0.55F, 1.0F, 0.7F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(3.2F, 1.5796F, 0.3803F, 0.3927F, 0.0F, 0.0F));

            PartDefinition cube_r9 = OracleBellLeftOther.addOrReplaceChild("cube_r9", CubeListBuilder.create().texOffs(0, 14).addBox(-0.15F, -1.45F, -0.45F, 0.3F, 2.5F, 0.7F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(3.3F, 10.35F, 1.5F, 0.1309F, 0.0F, 0.0F));

            PartDefinition cube_r10 = OracleBellLeftOther.addOrReplaceChild("cube_r10", CubeListBuilder.create().texOffs(15, 20).addBox(-0.15F, -1.55F, -0.35F, 0.3F, 2.8F, 0.7F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(3.4F, 10.65F, 0.8F, 0.0524F, 0.0F, 0.0F));

            PartDefinition cube_r11 = OracleBellLeftOther.addOrReplaceChild("cube_r11", CubeListBuilder.create().texOffs(18, 14).addBox(-0.15F, -1.45F, -0.25F, 0.3F, 2.5F, 0.7F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(3.3F, 10.35F, -1.5F, -0.1309F, 0.0F, 0.0F));

            PartDefinition cube_r12 = OracleBellLeftOther.addOrReplaceChild("cube_r12", CubeListBuilder.create().texOffs(23, 15).addBox(-0.15F, -1.55F, -0.35F, 0.3F, 2.8F, 0.7F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(3.4F, 10.65F, -0.8F, -0.0524F, 0.0F, 0.0F));

            PartDefinition cube_r13 = OracleBellLeftOther.addOrReplaceChild("cube_r13", CubeListBuilder.create().texOffs(21, 0).addBox(-0.15F, -0.7F, 0.0F, 0.3F, 0.7F, 0.7F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(3.5F, 12.7F, -0.05F, 0.7854F, 0.0F, 0.0F));

            PartDefinition OracleBellLeftBody = partdefinition.addOrReplaceChild("OracleBellLeftBody", CubeListBuilder.create().texOffs(17, 3).addBox(3.0F, 7.5F, -1.95F, 0.5F, 0.5F, 3.9F, new CubeDeformation(0.0F))
                    .texOffs(21, 9).addBox(3.0F, 3.5F, -1.95F, 0.5F, 4.0F, 0.6F, new CubeDeformation(0.0F))
                    .texOffs(10, 20).addBox(3.0F, 3.5F, 1.35F, 0.5F, 4.0F, 0.6F, new CubeDeformation(0.0F))
                    .texOffs(14, 27).addBox(3.0F, 3.0F, -1.95F, 0.5F, 0.5F, 3.9F, new CubeDeformation(0.0F))
                    .texOffs(0, 7).addBox(-1.3F, 8.0F, -2.3F, 5.0F, 1.0F, 4.6F, new CubeDeformation(0.0F))
                    .texOffs(0, 0).addBox(-1.3F, 2.0F, -2.3F, 5.0F, 1.0F, 4.6F, new CubeDeformation(0.0F)), PartPose.offset(5.0F, 2.0F, 0.0F));

            return LayerDefinition.create(meshdefinition, 32, 32);
        }

        @Override
        public void setupAnim(@NotNull T pEntity, float pLimbSwing, float pLimbSwingAmount, float pPartialTicks, float pNetHeadYaw, float pHeadPitch) {
            super.setupAnim(pEntity, pLimbSwing, pLimbSwingAmount, pPartialTicks, pNetHeadYaw, pHeadPitch);
            this.crouching = pEntity.isCrouching();
            this.swimAmount = pEntity.getSwimAmount(pLimbSwingAmount);
            this.OracleBellLeftOther.copyFrom(this.leftArm);
            this.OracleBellLeftBody.copyFrom(this.leftArm);
        }

        @Override
        public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
            var cRed = red;
            var cGreen = green;
            var cBlue = blue;
            if (hasColor)
            {
                cRed *= dyeRed;
                cGreen *= dyeGreen;
                cBlue *= dyeBlue;
            }
            OracleBellLeftBody.render(poseStack, vertexConsumer, packedLight, packedOverlay, dyeRed, dyeGreen, dyeBlue, alpha);
            poseStack.pushPose();
            OracleBellLeftOther.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
            poseStack.popPose();
        }
    }
}
