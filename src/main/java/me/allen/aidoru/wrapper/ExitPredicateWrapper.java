package me.allen.aidoru.wrapper;

import net.minecraft.server.v1_8_R3.PacketPlayInFlying;
import org.bukkit.entity.Player;

public class ExitPredicateWrapper {

    private final PacketPlayInFlying packetPlayInFlying;

    private final Player player;

    public ExitPredicateWrapper(PacketPlayInFlying packetPlayInFlying, Player player) {
        this.packetPlayInFlying = packetPlayInFlying;
        this.player = player;
    }

}
