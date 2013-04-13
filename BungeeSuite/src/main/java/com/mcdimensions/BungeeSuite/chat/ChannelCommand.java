package com.mcdimensions.BungeeSuite.chat;

import com.mcdimensions.BungeeSuite.BungeeSuite;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.plugin.Command;

public class ChannelCommand extends Command {

	BungeeSuite plugin;

	public ChannelCommand(BungeeSuite bungeeSuite) {
		super(bungeeSuite.channel, null, bungeeSuite.c);
		plugin = bungeeSuite;
	}

	@Override
	public void execute(CommandSender sender, String[] arg1) {
		if (arg1.length == 0 || arg1[0].equalsIgnoreCase("help")) {
			sendHelp(sender);
			return;
		}

		ChatPlayer cp = plugin.getChatPlayer(sender.getName());
		ChatChannel cc = cp.getCurrent();
		if (!(sender.hasPermission("BungeeSuite.admin") || sender.getName().equalsIgnoreCase(cc.getOwner()))) {
			sender.sendMessage(plugin.CHANNEL_NO_PERMISSION);
			return;
		}

		if (arg1[0].equalsIgnoreCase("kick")) {
			if (arg1.length < 2) {
				sender.sendMessage(ChatColor.RED + "/" + plugin.channel + " kick (player)");
				return;
			}
			
			String player = arg1[1];
			if (cc.containsMember(player)) {
				cc.removeMember(player);
				plugin.getChatPlayer(player).removeChannel(cc.getName());
			} else {
				sender.sendMessage(plugin.CHANNEL_NOT_MEMBER);
				return;
			}
			
			sender.sendMessage(ChatColor.DARK_GREEN + player + "kicked from channel");
			return;
		}

		if (arg1[0].equalsIgnoreCase("format")) {
			sender.sendMessage(ChatColor.GOLD + "Channel format: " + ChatColor.RESET + cc.getFormat());
			return;
		}

		if (arg1[0].equalsIgnoreCase("editformat")) {
			if (arg1.length < 2) {
				sender.sendMessage(ChatColor.RED + "/" + plugin.channel + " editformat (format)");
				return;
			}
			String format = "";
			for (String data : arg1) {
				if (!data.equalsIgnoreCase(arg1[0])) {
					format += data + " ";
				}
			}
			cc.reformat(format);
			sender.sendMessage(ChatColor.DARK_GREEN + "Channel format changed");
			return;
		}

		if (arg1[0].equalsIgnoreCase("rename")) {
			if (arg1.length < 2) {
				sender.sendMessage(ChatColor.RED + "/" + plugin.channel + " rename (name)");
				return;
			}
			String name = arg1[1];
			cc.rename(name);
			sender.sendMessage(ChatColor.DARK_GREEN + "Channel renamed");
			return;
		}

		if (arg1[0].equalsIgnoreCase("setowner")) {
			if (arg1.length < 2) {
				sender.sendMessage(ChatColor.RED + "/" + plugin.channel + " setowner (name)");
				return;
			}
			String name = arg1[1];
			ChatPlayer player = plugin.getChatPlayer(name);
			if (player == null) {
				sender.sendMessage(plugin.PLAYER_NOT_ONLINE);
				return;
			}
			if (!cc.containsMember(player.getName())) {
				sender.sendMessage(plugin.CHANNEL_NOT_MEMBER);
				return;
			}
			if (player.getChannelsOwned() >= plugin.maxCustomChannels) {
				sender.sendMessage(plugin.CHANNEL_TOO_MANY);
				return;
			}
			cc.setOwner(player);
			sender.sendMessage(ChatColor.DARK_GREEN + "Owner changed");
			return;
		}

		if (arg1[0].equalsIgnoreCase("members")) {
			String playerList = ChatColor.DARK_AQUA + "Members: " + ChatColor.WHITE;
			
			for (String data : cc.members) {
				playerList += data + ", ";
			}
			sender.sendMessage(playerList);
			return;
		}

		sendHelp(sender);
		return;
	}

	private void sendHelp(CommandSender sender) {
		sender.sendMessage(ChatColor.DARK_GREEN + "----Channel help----");
		sender.sendMessage(ChatColor.RED + "/" + plugin.channel + " kick (player)" + ChatColor.GOLD
				+ "- Kicks player from channel");
		sender.sendMessage(ChatColor.RED + "/" + plugin.channel + " format"
				+ ChatColor.GOLD + "- Displays channel format");
		sender.sendMessage(ChatColor.RED + "/" + plugin.channel + " editformat (format)" + ChatColor.GOLD
				+ "- edits channel format");
		sender.sendMessage(ChatColor.RED + "/" + plugin.channel + " rename (name)" + ChatColor.GOLD + "- Renames the channel");
		sender.sendMessage(ChatColor.RED + "/" + plugin.channel + " setowner (player)" + ChatColor.GOLD
				+ "- Changes the owner of the channel");
		sender.sendMessage(ChatColor.RED + "/" + plugin.channel + " members *(channel) " + ChatColor.GOLD
				+ "- Displays channel members");
	}

}
