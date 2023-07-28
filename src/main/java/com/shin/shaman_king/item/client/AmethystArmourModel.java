package com.shin.shaman_king.item.client;

import com.shin.shaman_king.Shaman_King;
import com.shin.shaman_king.item.custom.AmethystArmourItem;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class AmethystArmourModel extends GeoModel<AmethystArmourItem> {
    @Override
    public ResourceLocation getModelResource(AmethystArmourItem animatable) {
        return new ResourceLocation(Shaman_King.MOD_ID, "geo/amethyst_armour.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(AmethystArmourItem animatable) {
        return new ResourceLocation(Shaman_King.MOD_ID, "textures/armour/amethyst_armor.png");
    }

    @Override
    public ResourceLocation getAnimationResource(AmethystArmourItem animatable) {
        return new ResourceLocation(Shaman_King.MOD_ID, "animations/model.animation.json");
    }
}
