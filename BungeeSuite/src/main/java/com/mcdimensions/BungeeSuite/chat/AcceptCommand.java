package com.mcdimensions.BungeeSuite.chat;

import java.sql.SQLException;

import com.mcdimensions.BungeeSuite.BungeeSuite;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.plugin.Command;

public class AcceptCommand extends Command {

	BungeeSuite plugin;

	public AcceptCommand(BungeeSuite bungeeSuite) {
		super(bungeeSuite.accept);
		plugin = bungeeSuite;
	}

	@Override
	public void execute(CommandSender arg0, String[] arg1) {
		if (arg1.length != 1) {
			arg0.sendMessage(ChatColor.RED + "/" + plugin.accept + " (channel)");
			return;
		}

		String channelName = arg1[0];
		String playerName = arg0.getName();
		try {
			if (plugin.getUtilities().chatChannelExists(channelName)) {
				ChatChannel cc = plugin.getChannel(channelName);
				
				if (cc.isInvited(playerName) || arg0.hasPermission("BungeeSuite.admin"))
					cc.acceptInvite(playerName);
				else
					arg0.sendMessage(plugin.CHANNEL_NO_PERMISSION);
			} else {
				arg0.sendMessage(plugin.CHANNEL_NOT_EXIST);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
