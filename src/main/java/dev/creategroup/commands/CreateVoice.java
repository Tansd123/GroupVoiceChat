package dev.creategroup.commands;

import dev.creategroup.VoiceChatInteractionPlugin;
import de.maxhenkel.voicechat.api.Group;
import de.maxhenkel.voicechat.api.VoicechatConnection;
import org.bukkit.command.*;
import org.bukkit.entity.Player;

import javax.annotation.Nullable;
import java.util.UUID;

import static de.maxhenkel.voicechat.Voicechat.LOGGER;

public class CreateVoice implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if(sender instanceof ConsoleCommandSender){
            if(args.length == 3){

                if (VoiceChatInteractionPlugin.SERVER_API == null) {
                    System.out.println("Client not Connect");
                    return false;
                }

                Player p = parsePlayer(sender, args[0]);


                String password = args[2];
                String name = args[1];

                Group group;
                VoicechatConnection playerConnection = VoiceChatInteractionPlugin.SERVER_API.getConnectionOf(p.getUniqueId());

                group = VoiceChatInteractionPlugin.SERVER_API.createGroup(name, password);
                VoicechatConnection connection = VoiceChatInteractionPlugin.SERVER_API.getConnectionOf(p.getUniqueId());

                connection.setGroup(group);
                LOGGER.info(p + "Create Group " + "Name: " + args[0] + " Password: " + args[1]);
            }


        }

        return true;
    }
    @Nullable
    public static Player parsePlayer(CommandSender commandSender, String playerArg) {
        Player player = commandSender.getServer().getPlayer(playerArg);
        if (player != null) {
            return player;
        }
        try {
            UUID uuid = UUID.fromString(playerArg);
            return commandSender.getServer().getPlayer(uuid);
        } catch (IllegalArgumentException e) {
            return null;
        }
    }
}