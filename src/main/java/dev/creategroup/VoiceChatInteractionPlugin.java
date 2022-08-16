package dev.creategroup;

import de.maxhenkel.voicechat.api.*;
import de.maxhenkel.voicechat.api.events.EventRegistration;
import de.maxhenkel.voicechat.api.events.MicrophonePacketEvent;
import de.maxhenkel.voicechat.api.events.VoicechatServerStartedEvent;
import de.maxhenkel.voicechat.api.opus.OpusDecoder;

import org.bukkit.entity.Player;
import org.bukkit.GameEvent;

import javax.annotation.Nullable;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class VoiceChatInteractionPlugin implements VoicechatPlugin {

    public static VoicechatApi voicechatApi;

    @Nullable
    public static VoicechatServerApi SERVER_API;


    @Override
    public String getPluginId() {
        return CreateGroup.PLUGIN_ID;
    }

    @Override
    public void initialize(VoicechatApi api) {
        voicechatApi = api;

    }

    @Override
    public void registerEvents(EventRegistration registration) {
        registration.registerEvent(VoicechatServerStartedEvent.class, evt -> SERVER_API = evt.getVoicechat());
    }



}