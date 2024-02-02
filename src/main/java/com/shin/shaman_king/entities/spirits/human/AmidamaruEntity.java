package com.shin.shaman_king.entities.spirits.human;

import com.shin.shaman_king.ai.FollowShamanGoal;
import com.shin.shaman_king.entities.BondedEntity;
import com.shin.shaman_king.entities.spirits.ISpirits;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.goal.target.ResetUniversalAngerTargetGoal;
import net.minecraft.world.entity.monster.AbstractSkeleton;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

public class AmidamaruEntity extends BondedEntity implements ISpirits {
    protected AmidamaruEntity(EntityType<? extends BondedEntity> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
        this.setBond(false);
    }
    protected void registerGoals() {
        this.goalSelector.addGoal(1, new FloatGoal(this));
        this.goalSelector.addGoal(6, new FollowShamanGoal(this, 1.0, 10.0F, 2.0F, false));
        this.goalSelector.addGoal(8, new WaterAvoidingRandomStrollGoal(this, 1.0));
        this.goalSelector.addGoal(10, new LookAtPlayerGoal(this, Player.class, 8.0F));
        this.goalSelector.addGoal(10, new RandomLookAroundGoal(this));

    }
    public AttributeSupplier.Builder createAttributes() {
        return Monster.createMonsterAttributes()
                .add(Attributes.MAX_HEALTH, 10000000D)
                .add(Attributes.FOLLOW_RANGE, 10D)
                .add(Attributes.MOVEMENT_SPEED, 1D)
                .add(Attributes.ARMOR_TOUGHNESS, 0);
    }
    public void setBond(boolean pTamed) {
        super.setBond(pTamed);
    }
    @Override
    public boolean hurt(DamageSource source, float amount) {
        Entity entity = source.getEntity();
        if (entity instanceof Player) {
            this.bond((Player) entity);
            System.out.println("Bonded");
        }
        return super.hurt(source, amount);
    }

    @Override
    public boolean canSeirei() {
        return true;
    }

    @Override
    public boolean canKami() {
        return false;
    }
}
