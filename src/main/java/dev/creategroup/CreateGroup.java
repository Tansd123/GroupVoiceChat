package dev.creategroup;



import de.maxhenkel.voicechat.api.BukkitVoicechatService;
import dev.creategroup.commands.CreateVoice;
import dev.creategroup.commands.Phone;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.bukkit.GameEvent;
import org.bukkit.Server;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import javax.annotation.Nullable;

public final class CreateGroup extends JavaPlugin {

    public static final String PLUGIN_ID = "Create Groups";
    public static final Logger LOGGER = LogManager.getLogger(PLUGIN_ID);
    public static Server SERVER;
    public static GameEvent VOICE_GAME_EVENT;
    public static CreateGroup INSTANCE;

    @Nullable
    private VoiceChatInteractionPlugin voicechatPlugin;

    @Override
    public void onEnable() {
        SERVER = getServer();
        VOICE_GAME_EVENT = GameEvent.PRIME_FUSE;
        INSTANCE = this;
        getCommand("creategroup").setExecutor(new CreateVoice());
        getCommand("call").setExecutor(new Phone());

        BukkitVoicechatService service = getServer().getServicesManager().load(BukkitVoicechatService.class);
        if (service != null) {
            voicechatPlugin = new VoiceChatInteractionPlugin();
            service.registerPlugin(voicechatPlugin);
            LOGGER.info("Successfully registered Create Groups plugin");
        } else {
            LOGGER.info("Failed to register Create Groups plugin");
        }
        System.out.println(service);
    }

    @Override
    public void onDisable() {
        if (voicechatPlugin != null) {
            getServer().getServicesManager().unregister(voicechatPlugin);
            LOGGER.info("Successfully unregistered Group plugin");
        }
    }
}