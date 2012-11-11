package gcscripts.gcsuperheater.nodes;

import gcapi.constants.items.Runes;
import gcscripts.gcsuperheater.GcSuperheater;

import org.powerbot.core.script.job.state.Node;
import org.powerbot.game.api.methods.Game;
import org.powerbot.game.api.methods.tab.Inventory;
import org.powerbot.game.api.methods.widget.Bank;
import org.powerbot.game.api.methods.widget.Bank.Amount;
import org.powerbot.game.api.wrappers.node.Item;

public class Banker extends Node {

	Item item = null;

	@Override
	public boolean activate() {
		return Game.isLoggedIn() && GcSuperheater.isBanking;
	}

	@Override
	public void execute() {
		if (Bank.open()) {
			GcSuperheater.logger.log("Opened bank.");
			for (Item i : Inventory.getItems()) {
				if (i != null && i.getId() != Runes.NATURE && i.getId() != Runes.FIRE) {
					Bank.deposit(i.getId(), Amount.ALL);
				}
			}
			int slotsAvailable = 0;
			for (Item i : Inventory.getAllItems(false)) {
				if (i == null) {
					++slotsAvailable;
				}
			}

			int primaryOreId = GcSuperheater.primaryOre[0];
			Item primaryOre = Bank.getItem(primaryOreId);
			int primaryOreAmount = GcSuperheater.primaryOre[1];
			int primaryOreWithdrawAmount = 0;

			int secondaryOreId = 0;
			Item secondaryOre = null;
			int secondaryOreAmount = 0;
			int secondaryOreWithdrawAmount = 0;

			if (GcSuperheater.secondaryOre != null) {
				secondaryOre = Bank.getItem(GcSuperheater.secondaryOre[0]);
				secondaryOreId = GcSuperheater.secondaryOre[0];
				secondaryOreAmount = GcSuperheater.secondaryOre[1];
				while (slotsAvailable >= (primaryOreAmount + secondaryOreAmount)) {
					++primaryOreWithdrawAmount;
					secondaryOreWithdrawAmount += secondaryOreAmount;
					slotsAvailable -= primaryOreAmount + secondaryOreAmount;
				}
			} else {
				primaryOreWithdrawAmount = slotsAvailable;
			}

			if (primaryOre != null) {
				primaryOre = Bank.getItem(primaryOreId);
				Bank.search(primaryOre.getName());
				GcSuperheater.logger.log("Withdrawing " + primaryOreWithdrawAmount + " " + primaryOre.getName() + ".");
				Bank.withdraw(primaryOreId, primaryOreWithdrawAmount);
			} else {
				GcSuperheater.logger.log("Out of primary ore, stopping.");
				GcSuperheater.problemFound = true;
			}

			if (secondaryOre != null) {
				secondaryOre = Bank.getItem(secondaryOreId);
				Bank.search(secondaryOre.getName());
				GcSuperheater.logger.log("Withdrawing " + secondaryOreWithdrawAmount + " " + secondaryOre.getName() + ".");
				Bank.withdraw(secondaryOreId, secondaryOreWithdrawAmount);
			} else {
				GcSuperheater.logger.log("Out of secondary ore, stopping.");
				GcSuperheater.problemFound = true;
			}

			primaryOreWithdrawAmount = 0;
			secondaryOreWithdrawAmount = 0;

			Bank.close();
			GcSuperheater.isBanking = false;
			GcSuperheater.logger.log("Finished banking.");
		} else {
			GcSuperheater.logger.log("Unable to bank, stopping.");
			GcSuperheater.problemFound = true;
			return;
		}
	}
}
