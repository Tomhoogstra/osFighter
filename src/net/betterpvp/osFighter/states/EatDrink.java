package net.betterpvp.osFighter.states;

import org.osbot.rs07.api.filter.ContainsNameFilter;
import org.osbot.rs07.api.map.Position;
import org.osbot.rs07.api.model.Item;
import org.osbot.rs07.api.ui.Skill;

import net.betterpvp.osFighter.Fighter;
import net.betterpvp.osFighter.data.Potions;
import net.betterpvp.osFighter.data.SessionData;
import net.betterpvp.osFighter.utilities.CustomSleep;
import net.betterpvp.osFighter.utilities.UtilMath;
import net.betterpvp.osFighter.utilities.UtilWalking;

public class EatDrink extends ScriptState{

	private int deviateBy = 0;



	@Override
	public boolean validate(Fighter i) throws InterruptedException {

		SessionData data = i.getSessionData();




		if(data.isEatingFood()) {
			if(i.getSkills().getDynamic(Skill.HITPOINTS) < 
					(deviateBy == 0 ? data.getHealthToEatBelow() : deviateBy)) {
				return true;
			}


		}


		if(data.isDrinkPotions()) {
			if(i.getCombat().isPoisoned() || i.getCombat().isDiseased()) {
				if(data.isDrinkAntidotePotions() || data.isDrinkAntipoisonPotions() || data.isDrinkAntivenomPotions()) {
					return true;
				}
			}



			if(data.isDrinkAttackPotions()) {
				checkStat(i, Skill.ATTACK, Potions.ATTACK);
			}

			if(data.isDrinkStrengthPotions()) {
				checkStat(i, Skill.STRENGTH, Potions.STRENGTH);
			}

			if(data.isDrinkDefencePotions()) {
				checkStat(i, Skill.DEFENCE, Potions.DEFENCE);
			}

			if(data.isDrinkCombatPotions()) {
				checkStat(i, Skill.ATTACK, Potions.COMBAT);
			}

			if(data.isDrinkRangingPotions()) {
				checkStat(i, Skill.RANGED, Potions.RANGED);
			}

			if(data.isDrinkMagicPotions()) {
				checkStat(i, Skill.MAGIC, Potions.MAGIC);
			}

		}
		return false;
	}

	private boolean checkStat(Fighter i, Skill skill, Potions pot) {
		if(i.getSkills().getDynamic(skill) < i.getSkills().getStatic(skill) + 5) {
			if(i.getInventory().contains(pot.getOptions())) {
				return true;
			}
		}

		return false;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void run(Fighter i) throws InterruptedException {
		SessionData data = i.getSessionData();
		if(data.getHealthDeviation() != 0) {
		deviateBy = data.getHealthToEatBelow() 
				+ UtilMath.randInt(data.getHealthDeviation() * -1,  data.getHealthDeviation());
		}

		if(data.isEatingFood()) {
			if(i.getSkills().getDynamic(Skill.HITPOINTS) < (deviateBy == 0 ? data.getHealthToEatBelow() : deviateBy)) {
				int healthPct = i.myPlayer().getHealthPercent();
				if(i.getInventory().contains(data.getFoodToEat())) {
					if(i.getInventory().interact("Eat", data.getFoodToEat())) {
						new CustomSleep(() -> i.myPlayer().getHealthPercent() > healthPct, 2000).sleep();
					}
				}else {
					data.setShouldBankNow(true);
					if(data.isBanking()) {
						i.getWalking().webWalk(UtilWalking.getClosestBank(i.myPosition()));
						
					}else {
						if(i.getInventory().contains("Teleport")) {
							Position p = i.myPosition();
							i.getInventory().interact("Break", "Teleport");
							new CustomSleep(() -> i.myPosition() != p, 10000).sleep();
						}
					}
					// TODO Explore escape options / stopping script
					/*
					 * Teleport
					 * Walk
					 * 
					 */

					return;
				}
			}
		}

		if(data.isDrinkPotions()) {

		}

	}

}
