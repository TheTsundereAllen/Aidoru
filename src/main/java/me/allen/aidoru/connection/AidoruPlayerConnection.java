package me.allen.aidoru.connection;

import me.allen.aidoru.Aidoru;
import me.allen.aidoru.handler.ExitHandler;
import me.allen.aidoru.wrapper.ExitPredicateWrapper;
import net.minecraft.server.v1_8_R3.*;
import org.bukkit.craftbukkit.v1_8_R3.CraftServer;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

import java.lang.reflect.Field;

public class AidoruPlayerConnection extends PlayerConnection {

    private CraftServer craftServer;

    public AidoruPlayerConnection(PlayerConnection playerConnection, EntityPlayer entityPlayer) {
        super(MinecraftServer.getServer(), playerConnection.networkManager, entityPlayer);
        this.resolveFields();
    }

    @Override
    public void a(PacketPlayInFlying packetplayinflying) {
        ExitHandler exitHandler = Aidoru.getInstance().getExitHandler();
        if (exitHandler == null) return;
        if (exitHandler.getCanExit().test(new ExitPredicateWrapper(packetplayinflying, this.player.getBukkitEntity()))) {
            exitHandler.getOnExit().accept(this.player.getBukkitEntity());
        }
    }

    public void a(PacketPlayInArmAnimation packetplayinarmanimation) {

    }

    @Override
    public void a(PacketPlayInEntityAction packetplayinentityaction) {

    }

    @Override
    public void a(PacketPlayInUseEntity packetplayinuseentity) {

    }

    @Override
    public void a(PacketPlayInClientCommand packetplayinclientcommand) {

    }

    @Override
    public void a(PacketPlayInCloseWindow packetplayinclosewindow) {

    }

    @Override
    public void a(PacketPlayInWindowClick packetplayinwindowclick) {

    }

    @Override
    public void a(PacketPlayInEnchantItem packetplayinenchantitem) {

    }

    @Override
    public void a(PacketPlayInSetCreativeSlot packetplayinsetcreativeslot) {

    }

    @Override
    public void a(PacketPlayInTransaction packetplayintransaction) {

    }

    @Override
    public void a(PacketPlayInUpdateSign packetplayinupdatesign) {

    }

    @Override
    public void a(PacketPlayInKeepAlive packetplayinkeepalive) {

    }

    @Override
    public void a(PacketPlayInHeldItemSlot packetplayinhelditemslot) {

    }

    @Override
    public void a(PacketPlayInAbilities packetplayinabilities) {

    }

    @Override
    public void a(PacketPlayInSettings packetplayinsettings) {

    }

    @Override
    public void a(PacketPlayInTabComplete packetplayintabcomplete) {

    }

    @Override
    public void a(PacketPlayInCustomPayload packetplayincustompayload) {
        PlayerConnectionUtils.ensureMainThread(packetplayincustompayload, this, this.player.u());

        try { // CraftBukkit
            if (packetplayincustompayload.a().equals("REGISTER")) {
                String channels = packetplayincustompayload.b().toString(com.google.common.base.Charsets.UTF_8);
                for (String channel : channels.split("\0")) {
                    getPlayer().addChannel(channel);
                }
            } else if (packetplayincustompayload.a().equals("UNREGISTER")) {
                String channels = packetplayincustompayload.b().toString(com.google.common.base.Charsets.UTF_8);
                for (String channel : channels.split("\0")) {
                    getPlayer().removeChannel(channel);
                }
            } else {
                byte[] data = new byte[packetplayincustompayload.b().readableBytes()];
                packetplayincustompayload.b().readBytes(data);
                this.craftServer.getMessenger().dispatchIncomingMessage(player.getBukkitEntity(), packetplayincustompayload.a(), data);
            }
            // CraftBukkit end
            // CraftBukkit start
        } finally {
            if (packetplayincustompayload.b().refCnt() > 0) {
                packetplayincustompayload.b().release();
            }
        }
        // CraftBukkit end
    }

    private void resolveFields() {
        try {
            Field serverField = PlayerConnection.class.getDeclaredField("server");
            this.craftServer = (CraftServer) serverField.get(this);
        } catch (Exception ex) {
            ex.printStackTrace();;
        }
    }

    public static void injectConnection(Player player) {
        EntityPlayer entityPlayer = ( (CraftPlayer) player).getHandle();
        PlayerConnection oldConnection = entityPlayer.playerConnection;
        entityPlayer.playerConnection = new AidoruPlayerConnection(
                oldConnection,
                entityPlayer
        );
    }
}