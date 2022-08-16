package dev.creategroup.commands;

import de.maxhenkel.voicechat.api.Group;
import de.maxhenkel.voicechat.api.VoicechatConnection;
import de.maxhenkel.voicechat.net.NetManager;
import dev.creategroup.VoiceChatInteractionPlugin;
import net.kyori.adventure.text.*;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.event.HoverEvent;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.Style;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Unmodifiable;

import javax.annotation.Nullable;
import java.util.List;
import java.util.UUID;

public class Phone implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender instanceof ConsoleCommandSender){
            Player p1 = parsePlayer(sender, args[0]);
            Player p2 = parsePlayer(sender, args[1]);
            if(p1 == null || p2 == null){
                sender.sendMessage(ChatColor.RED + "Can't find player!!");
                return false;
            }
            Player p3 = sender.getServer().getPlayer(args[0]);
            String password = p3.getUniqueId().toString();
            String name = p3.getName();

            Group group;
            VoicechatConnection playerConnection = VoiceChatInteractionPlugin.SERVER_API.getConnectionOf(p3.getUniqueId());

            group = VoiceChatInteractionPlugin.SERVER_API.createGroup(name, password);
            VoicechatConnection connection = VoiceChatInteractionPlugin.SERVER_API.getConnectionOf(p3.getUniqueId());

            connection.setGroup(group);

            String passwordSuffix = password == null ? "" : " \"" + password + "\"";
           /* NetManager.sendMessage(p2, Component.translatable("message.voicechat.invite",
                    Component.text(commandSender.getName()),
                    Component.text(group.getName()).toBuilder().color(NamedTextColor.GRAY).asComponent(),
                    Component.text("[").toBuilder().append(
                            Component.translatable("message.voicechat.accept_invite")
                                    .toBuilder()
                                    .clickEvent(ClickEvent.clickEvent(ClickEvent.Action.RUN_COMMAND, "/voicechat join " + group.getId().toString() + passwordSuffix))
                                    .hoverEvent(HoverEvent.hoverEvent(HoverEvent.Action.SHOW_TEXT, Component.translatable("message.voicechat.accept_invite.hover")))
                                    .color(NamedTextColor.GREEN)
                                    .build()
                    ).append(Component.text("]")).color(NamedTextColor.GREEN).asComponent()
            ));*/
            p2.sendMessage(Component.text()
                    .append(Component.text("You have Call by: "))
                    .append(Component.text(name)).color(NamedTextColor.WHITE)
                    .append(Component.text(" [Accept]"))
                        .clickEvent(ClickEvent.clickEvent(ClickEvent.Action.RUN_COMMAND, "/voicechat join " + group.getId().toString() + passwordSuffix))
                        .color(NamedTextColor.GREEN)

            );
            p3.sendMessage("Người chơi đã chấp nhận cuộc gọi");


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
