package org.osps.model.players.combat.melee;

import org.osps.*;
import org.osps.model.minigames.Dicing;
import org.osps.model.minigames.hunger.HungerManager;
import org.osps.model.players.*;

public class MeleeRequirements {

	public static int getCombatDifference(int combat1, int combat2) {
		if(combat1 > combat2) {
			return (combat1 - combat2);
		}
		if(combat2 > combat1) {
			return (combat2 - combat1);
		}	
		return 0;
	}
	
	public static boolean checkReqs(Player c) {
		if(PlayerHandler.players[c.playerIndex] == null) {
			return false;
		}
		if (c.playerIndex == c.index)
			return false;
		if (c.inPits && PlayerHandler.players[c.playerIndex].inPits)
			return true;
		if (c.inFunPk()) {
			return true;
		}
		if (c.inFunPk2()) {
			return true;
		}
		if(PlayerHandler.players[c.playerIndex].inDuelArena()) {
			if(!Boundary.isIn(c, Boundary.DUEL_ARENAS)) {
				if (!Config.NEW_DUEL_ARENA_ACTIVE) {
					c.getDH().sendStatement("@red@Dueling Temporarily Disabled",
						"The duel arena minigame is currently being rewritten.",
						"No player has access to this minigame during this time.",
						"", "Thank you for your patience.");
					c.nextChat = -1;
					return false;
				}
				Player other = PlayerHandler.players[c.playerIndex];
				if (c.getDuel().requestable(other)) {
					c.getDuel().request(other);
				}	
				c.getCombat().resetPlayerAttack();
				return false;
			}
			return true;
		}
        if(Dicing.inDiceArea(c)) {
			Dicing.setUpDice(c, c.playerIndex);
			return false;
		}
		if(c.hungerGames && PlayerHandler.players[c.playerIndex].hungerGames && HungerManager.getSingleton().gameRunning()) 
			return true;
		else if(c.hungerGames && PlayerHandler.players[c.playerIndex].hungerGames && !HungerManager.getSingleton().gameRunning()) {
			c.sendMessage("The game has not started yet!");
			c.stopMovement();
			c.getCombat().resetPlayerAttack();
			return false;
		}
		if(!PlayerHandler.players[c.playerIndex].inWild()) {
			c.sendMessage("That player is not in the wilderness.");
			c.stopMovement();
			c.getCombat().resetPlayerAttack();
			return false;
		}
		if(!c.inWild()) {
			c.sendMessage("You are not in the wilderness.");
			c.stopMovement();
			c.getCombat().resetPlayerAttack();
			return false;
		}
		if(Config.COMBAT_LEVEL_DIFFERENCE && !c.isInBarrows() && !c.isInBarrows2()) {
			if(c.inWild() && !c.isInBarrows() && !c.isInBarrows2()) {
				int combatDif1 = getCombatDifference(c.combatLevel, PlayerHandler.players[c.playerIndex].combatLevel);
				if((combatDif1 > c.wildLevel || combatDif1 > PlayerHandler.players[c.playerIndex].wildLevel)) {
					c.sendMessage("Your combat level difference is too great to attack that player here.");
					c.stopMovement();
					c.getCombat().resetPlayerAttack();
					return false;
				}
			} else {
				int myCB = c.combatLevel;
				int pCB = PlayerHandler.players[c.playerIndex].combatLevel;
				if((myCB > pCB + 12) || (myCB < pCB - 12)) {
					c.sendMessage("You can only fight players in your combat range!");
					c.stopMovement();
					c.getCombat().resetPlayerAttack();
					return false;
				}
			}
		}
		
		if(Config.SINGLE_AND_MULTI_ZONES) {
			if(!PlayerHandler.players[c.playerIndex].inMulti()) {	// single combat zones
				if(PlayerHandler.players[c.playerIndex].underAttackBy != c.index  && PlayerHandler.players[c.playerIndex].underAttackBy != 0
						/*&& !(c.getBH().hasTarget() && c.getBH().getTarget().getName().equalsIgnoreCase(PlayerHandler.players[c.playerIndex].playerName))*/) {
					c.sendMessage("That player is already in combat.");
					c.stopMovement();
					c.getCombat().resetPlayerAttack();
					return false;
				}
				if(PlayerHandler.players[c.playerIndex].index != c.underAttackBy && c.underAttackBy != 0 || c.underAttackBy2 > 0) {
					c.sendMessage("You are already in combat.");
					c.stopMovement();
					c.getCombat().resetPlayerAttack();
					return false;
				}
			}
		}
		return true;
	}		

	public static int getRequiredDistance(Player c) {
		if (c.followId > 0 && c.freezeTimer <= 0 && !c.isMoving)
			return 2;
		else if(c.followId > 0 && c.freezeTimer <= 0 && c.isMoving) {
			return 3;
		} else {
			return 1;
		}
	}
}