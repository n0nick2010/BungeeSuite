package com.mcdimensions.BungeeSuite.chat;

import com.mcdimensions.BungeeSuite.BungeeSuite;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.plugin.Command;

public class reply extends Command {
	BungeeSuite plugin;
	public reply(BungeeSuite bungeeSuite) {
		super(bungeeSuite.reply);
		plugin = bungeeSuite;
	}

	@Override
	public void execute(CommandSender arg0, String[] arg1) {
		if(arg1.length<1){
			arg0.sendMessage(ChatColor.RED+"/"+ plugin.reply+ " (message)");
			return;
		}
		ChatPlayer cp =plugin.getChatPlayer(arg0.getName());
		String player =cp.getReplyPlayer();
		if(player!=null){
		ChatPlayer rp =plugin.getChatPlayer(player);
		if(rp.ignoringPlayer(arg0.getName())){
			arg0.sendMessage(plugin.PLAYER_IGNORING);
			return;
		}
		if(rp!=null){
			String message="";
			for(String data:arg1){
				message+=data+" ";
			}
			rp.sendPrivate(message, arg0.getName());
			arg0.sendMessage(ChatColor.GOLD+"[me->"+rp.getName()+"]"+ChatColor.WHITE+message);
			return;
		}else{
			arg0.sendMessage(plugin.PLAYER_NOT_ONLINE);
		}
		}else{
			arg0.sendMessage(plugin.PLAYER_REPLY_NONE);
		}
	}

}
