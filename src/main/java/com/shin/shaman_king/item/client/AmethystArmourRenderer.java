package com.shin.shaman_king.item.client;

import com.shin.shaman_king.item.custom.AmethystArmourItem;
import software.bernie.geckolib.renderer.GeoArmorRenderer;

public class AmethystArmourRenderer extends GeoArmorRenderer<AmethystArmourItem> {
    public AmethystArmourRenderer(){
        super(new AmethystArmourModel());
    }
}
