package com.shin.shaman_king.network;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.Capability;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.Entity;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.nbt.Tag;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.core.Direction;
import net.minecraft.client.Minecraft;

import com.shin.shaman_king.Shaman_King;
import org.jetbrains.annotations.NotNull;

import java.util.Random;
import java.util.function.Supplier;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class ShamanKingVariables {
    @SubscribeEvent
    public static void init(FMLCommonSetupEvent event) {
        Shaman_King.addNetworkMessage(PlayerVariablesSyncMessage.class, PlayerVariablesSyncMessage::buffer, PlayerVariablesSyncMessage::new, PlayerVariablesSyncMessage::handler);
    }

    @SubscribeEvent
    public static void init(RegisterCapabilitiesEvent event) {
        event.register(PlayerVariables.class);
    }

    @Mod.EventBusSubscriber
    public static class EventBusVariableHandlers {
        @SubscribeEvent
        public static void onPlayerLoggedInSyncPlayerVariables(PlayerEvent.PlayerLoggedInEvent event) {
            if (!event.getEntity().level().isClientSide())
                event.getEntity().getCapability(PLAYER_VARIABLES_CAPABILITY, null).orElse(new PlayerVariables()).syncPlayerVariables(event.getEntity());
        }

        @SubscribeEvent
        public static void onPlayerRespawnedSyncPlayerVariables(PlayerEvent.PlayerRespawnEvent event) {
            if (!event.getEntity().level().isClientSide())
                event.getEntity().getCapability(PLAYER_VARIABLES_CAPABILITY, null).orElse(new PlayerVariables()).syncPlayerVariables(event.getEntity());
        }

        @SubscribeEvent
        public static void onPlayerChangedDimensionSyncPlayerVariables(PlayerEvent.PlayerChangedDimensionEvent event) {
            if (!event.getEntity().level().isClientSide())
                event.getEntity().getCapability(PLAYER_VARIABLES_CAPABILITY, null).orElse(new PlayerVariables()).syncPlayerVariables(event.getEntity());
        }

        @SubscribeEvent
        public static void clonePlayer(PlayerEvent.Clone event) {
            event.getOriginal().revive();
            PlayerVariables original = event.getOriginal().getCapability(PLAYER_VARIABLES_CAPABILITY, null).orElse(new PlayerVariables());
            PlayerVariables clone = event.getEntity().getCapability(PLAYER_VARIABLES_CAPABILITY, null).orElse(new PlayerVariables());
            clone.Furyoku = original.Furyoku;
            clone.OSP = original.OSP;
            clone.Reiryoku = original.Reiryoku;
            clone.MaxFuryoku = original.MaxFuryoku;
            clone.MaxOSP = original.MaxOSP;
            clone.OBRed = original.OBRed;
            clone.OBGreen = original.OBGreen;
            clone.OBBlue = original.OBBlue;
        }
    }

    public static final Capability<PlayerVariables> PLAYER_VARIABLES_CAPABILITY = CapabilityManager.get(new CapabilityToken<>() {
    });

    @Mod.EventBusSubscriber
    private static class PlayerVariablesProvider implements ICapabilitySerializable<Tag> {
        @SubscribeEvent
        public static void onAttachCapabilities(AttachCapabilitiesEvent<Entity> event) {
            if (event.getObject() instanceof Player && !(event.getObject() instanceof FakePlayer))
                event.addCapability(new ResourceLocation(Shaman_King.MODID, "player_variables"), new PlayerVariablesProvider());
        }

        private final PlayerVariables playerVariables = new PlayerVariables();
        private final LazyOptional<PlayerVariables> instance = LazyOptional.of(() -> playerVariables);

        @Override
        public <T> @NotNull LazyOptional<T> getCapability(@NotNull Capability<T> cap, Direction side) {
            return cap == PLAYER_VARIABLES_CAPABILITY ? instance.cast() : LazyOptional.empty();
        }

        @Override
        public Tag serializeNBT() {
            return playerVariables.writeNBT();
        }

        @Override
        public void deserializeNBT(Tag nbt) {
            playerVariables.readNBT(nbt);
        }
    }

    public static class PlayerVariables {
        double rangeMin = 0.0f;
        double rangeMax = 1.0f;
        Random r = new Random();
        public double MaxFuryoku = 100;
        public double Furyoku = 0;
        public double MaxOSP = 10;
        public double OSP = 0;
        public double Reiryoku = 0;
        public double OBRed = rangeMin + (rangeMax - rangeMin) * r.nextDouble();
        public double OBGreen = rangeMin + (rangeMax - rangeMin) * r.nextDouble();
        public double OBBlue = rangeMin + (rangeMax - rangeMin) * r.nextDouble();

        public void syncPlayerVariables(Entity entity) {
            if (entity instanceof ServerPlayer serverPlayer)
                Shaman_King.PACKET_HANDLER.send(PacketDistributor.PLAYER.with(() -> serverPlayer), new PlayerVariablesSyncMessage(this));
        }

        public Tag writeNBT() {
            CompoundTag nbt = new CompoundTag();
            nbt.putDouble("Max Furyoku", MaxFuryoku);
            nbt.putDouble("Furyoku", Furyoku);
            nbt.putDouble("Max OSP", MaxOSP);
            nbt.putDouble("OSP", OSP);
            nbt.putDouble("Reiryoku", Reiryoku);
            nbt.putDouble("Oracle Bell Red", OBRed);
            nbt.putDouble("Oracle Bell Green", OBGreen);
            nbt.putDouble("Oracle Bell Blue", OBBlue);
            return nbt;
        }

        public void readNBT(Tag Tag) {
            CompoundTag nbt = (CompoundTag) Tag;
            Furyoku = nbt.getDouble("Furyoku");
            MaxFuryoku = nbt.getDouble("Max Furyoku");
            OSP = nbt.getDouble("OSP");
            MaxOSP = nbt.getDouble("Max OSP");
            Reiryoku = nbt.getDouble("Reiryoku");
            OBRed = nbt.getDouble("Oracle Bell Red");
            OBGreen = nbt.getDouble("Oracle Bell Green");
            OBBlue = nbt.getDouble("Oracle Bell Blue");
        }
    }

    public static class PlayerVariablesSyncMessage {
        private final PlayerVariables data;

        public PlayerVariablesSyncMessage(FriendlyByteBuf buffer) {
            this.data = new PlayerVariables();
            this.data.readNBT(buffer.readNbt());
        }

        public PlayerVariablesSyncMessage(PlayerVariables data) {
            this.data = data;
        }

        public static void buffer(PlayerVariablesSyncMessage message, FriendlyByteBuf buffer) {
            buffer.writeNbt((CompoundTag) message.data.writeNBT());
        }

        public static void handler(PlayerVariablesSyncMessage message, Supplier<NetworkEvent.Context> contextSupplier) {
            NetworkEvent.Context context = contextSupplier.get();
            context.enqueueWork(() -> {
                if (!context.getDirection().getReceptionSide().isServer()) {
                    assert Minecraft.getInstance().player != null;
                    PlayerVariables variables = Minecraft.getInstance().player.getCapability(PLAYER_VARIABLES_CAPABILITY, null).orElse(new PlayerVariables());
                    variables.Furyoku = message.data.Furyoku;
                    variables.OSP = message.data.OSP;
                    variables.Reiryoku = message.data.Reiryoku;
                    variables.MaxOSP = message.data.MaxOSP;
                    variables.MaxFuryoku = message.data.MaxFuryoku;
                    variables.OBRed = message.data.OBRed;
                    variables.OBGreen = message.data.OBGreen;
                    variables.OBBlue = message.data.OBBlue;
                }
            });
            context.setPacketHandled(true);
        }
    }
}
