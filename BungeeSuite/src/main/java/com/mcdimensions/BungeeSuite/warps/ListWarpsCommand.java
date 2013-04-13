package com.mcdimensions.BungeeSuite.warps;

import java.sql.SQLException;

import com.mcdimensions.BungeeSuite.BungeeSuite;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.plugin.Command;

public class ListWarpsCommand extends Command {
	
	BungeeSuite plugin;

	public ListWarpsCommand(BungeeSuite bungeeSuite) {
		super(bungeeSuite.warpsList);
		plugin = bungeeSuite;
	}

	@Override
	public void execute(CommandSender sender, String[] arg1) {
		String[] list = null;
		
		try {
			list = plugin.getUtilities().getWarpList(sender);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		sender.sendMessage(list[0]);
		if (sender.hasPermission("BungeeSuite.admin")) {
			sender.sendMessage(list[1]);
		}
	}

}
