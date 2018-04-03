package org.osps.model.players.packets.commands.all;

import org.osps.model.content.teleport.Position;
import org.osps.model.content.teleport.TeleportExecutor;
import org.osps.model.players.Player;
import org.osps.model.players.packets.commands.Command;

public class Heaven implements Command {

	@Override
	public void execute(Player c, String input) {
		 if (c.inTrade) {
             return;
         }
		TeleportExecutor.teleport(c, new Position(2804, 3184, 0));
	}
}
