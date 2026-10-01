package com.example.antiesp;

import com.github.retrooper.packetevents.PacketEvents;
import com.github.retrooper.packetevents.event.PacketListenerAbstract;
import com.github.retrooper.packetevents.event.PacketListenerPriority;
import com.github.retrooper.packetevents.event.PacketSendEvent;
import com.github.retrooper.packetevents.protocol.packettype.PacketType;
import com.github.retrooper.packetevents.wrapper.play.server.WrapperPlayServerJoinGame;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Random;

public class AntiEspPlugin extends JavaPlugin {

    private final Random random = new Random();

    @Override
    public void onLoad() {
        PacketEvents.getAPI().load();
    }

    @Override
    public void onEnable() {
        PacketEvents.getAPI().getEventManager().registerListener(
            new PacketListenerAbstract(PacketListenerPriority.HIGH) {
                @Override
                public void onPacketSend(PacketSendEvent event) {
                    if (event.getPacketType() == PacketType.Play.Server.JOIN_GAME) {
                        WrapperPlayServerJoinGame joinPacket = new WrapperPlayServerJoinGame(event);
                        joinPacket.setHashedSeed(random.nextLong());
                    }
                }
            }
        );

        PacketEvents.getAPI().init();
        getLogger().info("AntiEspFreecam Core enabled successfully!");
    }

    @Override
    public void onDisable() {
        PacketEvents.getAPI().terminate();
    }
}
