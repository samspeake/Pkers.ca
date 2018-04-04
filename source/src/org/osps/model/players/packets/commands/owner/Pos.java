package org.osps.model.players.packets.commands.owner;

import org.osps.model.players.Player;
import org.osps.model.players.packets.commands.Command;
import org.osps.util.Misc;

/**
 * Show the current position.
 * 
 * @author Emiel
 *
 */
public class Pos implements Command {

	@Override
	public void execute(Player c, String input) {
		Misc.println("loc=[absX: " + c.absX + " absY:" + c.absY + " h:" + c.height + "] region= id: " + c.getPosition().getRegionId() + " " + c.getPosition().getZ() + "," + c.getPosition().getLocalX() + "," + c.getPosition().getLocalY());
		c.sendMessage("loc=[absX: " + c.absX + " absY:" + c.absY + " h:" + c.height + "] region= id: " + c.getPosition().getRegionId() + " " + c.getPosition().getZ() + "," + c.getPosition().getLocalX() + "," + c.getPosition().getLocalY());
	}
}