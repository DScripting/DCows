package scripts.DTools;


import java.util.ArrayList;

import org.tribot.api.Clicking;
import org.tribot.api.DynamicClicking;
import org.tribot.api.General;
import org.tribot.api.types.generic.Filter;
import org.tribot.api2007.ChooseOption;
import org.tribot.api2007.GroundItems;
import org.tribot.api2007.Inventory;
import org.tribot.api2007.NPCs;
import org.tribot.api2007.Player;
import org.tribot.api2007.ext.Filters;
import org.tribot.api2007.types.RSArea;
import org.tribot.api2007.types.RSCharacter;
import org.tribot.api2007.types.RSGroundItem;
import org.tribot.api2007.types.RSNPC;
import org.tribot.api2007.types.RSPlayer;

import scripts.DTools.DAntiban;


	/*
	 * Author - TheDutch
	 * Copyright - DScripting
	 * 22-04-2013
	 * This script was created before the model tools released on TriBot.org
	 */

public class DCombat
{
	
	private static boolean canAttack = false;
	
	/**
	 * 
	 * @param lootList the items we search for
	 * @return true if we can loot and our inventory is not yet full
	 */
	
	public static boolean canLoot(String[] lootList)
	{
		return getLootableItem(lootList) != null && !Inventory.isFull();
	}
	
	/**
	 * 
	 * @param lootList the items we search for
	 * @param area1 the area the item has to be in
	 * @return true if we can loot and our inventory is not yet full
	 */
	
	public static boolean canLoot(String[] lootList, RSArea area1)
	{
		return getLootableItem(lootList,area1) != null && !Inventory.isFull();
	}
	
	/**
	 * 
	 * @param lootList the items we search for
	 * @param area1 One of the areas the item has to be in
	 * @param area2 One of the areas the item has to be in
	 * @return true if we found an item we can loot and our inventory is not yet full
	 */
	public static boolean canLoot(String[] lootList, RSArea area1, RSArea area2)
	{
		return getLootableItem(lootList,area1,area2) != null && !Inventory.isFull();
	}
	
	/**
	 * 
	 * @param names searches for a lootable item out of a list
	 * @return RSGroundItem we can loot or null
	 */
	public static RSGroundItem getLootableItem(String[] names)
	{
		return getLootableItem(names,null,null);
	}
	
	/**
	 * 
	 * @param names names searches for a lootable item out of a list
	 * @param area1 the area the lootable object has to be in
	 * @return RSGroundItem we can loot or null
	 */
	
	public static RSGroundItem getLootableItem(String[] names, RSArea area1)
	{
		return getLootableItem(names,area1,null);
	}
	
	/**
	 * 
	 * @param names names searches for a lootable item out of a list
	 * @param area1 one of the two areas the object has to be in
	 * @param area2 one of the two areas the object has to be in
	 * @return RSGroundItem we can loot or null
	 */
	
	public static RSGroundItem getLootableItem(String[] names, RSArea area1, RSArea area2)
	{
		RSGroundItem toReturn = null;
		RSGroundItem[] lootAbleItems = GroundItems.findNearest(Filters.GroundItems.nameEquals(names));
		ArrayList<RSGroundItem> finalItemList = new ArrayList<RSGroundItem>();
		for(int i=0; i<lootAbleItems.length; i++)
		{
			RSGroundItem curItem = lootAbleItems[i];
			if(area1 == null && area2 == null && curItem.isOnScreen())
				finalItemList.add(curItem);
			if(area1 != null && area2 == null && curItem.isOnScreen() && area1.contains(curItem))
				finalItemList.add(curItem);
			if(area1 != null && area2 != null && curItem.isOnScreen() && (area1.contains(curItem) || area2.contains(curItem)))
				finalItemList.add(curItem);
		}
		if(finalItemList.size() > 0)
		toReturn = finalItemList.get(0);
		return toReturn;
	}
	
	
	/**
	 * Checks if we can just click to loot an item, or if we have to right click and loots it.
	 * Will sleep for a short moment after succesfully clicking
	 * @param rsgrounditem The item to loot
	 * @Warning does not get the item on screen, so only call if you already got the item on screen
	 * @Extra If player is moving, will end the call.
	 */
	public static void loot(RSGroundItem rsgrounditem)
	{
		RSPlayer player = Player.getRSPlayer();
		String itemName = null;
		String itemOption = null;
		if(player == null) return;
		if(player.isMoving()) return;
		if(rsgrounditem == null) return;
		if(rsgrounditem.getDefinition() != null && rsgrounditem.getDefinition().getName() != null)
		itemName = rsgrounditem.getDefinition().getName();
		if(itemName == null) return;
		Clicking.hover(rsgrounditem);
		String[] itemOptions = ChooseOption.getOptions();
		if(itemOptions.length > 0) itemOption = itemOptions[0];
		if(itemOption != null && itemOption.equalsIgnoreCase("Take "+itemName))
		{
			System.out.println("Option is valid! "+itemOption);
			DynamicClicking.clickRSTile(rsgrounditem.getPosition(), "Take "+itemName);
			DAntiban.smartSleep(700,800);
		}
		else
		{
			System.out.println("Option is not valid! "+itemName);
			DynamicClicking.clickRSGroundItem(rsgrounditem, 3);
			DAntiban.sleep(General.random(3, 10));
			if(ChooseOption.isOptionValid("Take "+itemName));
				ChooseOption.select("Take "+itemName);
				DAntiban.smartSleep(700,900);
		}
		
	}
	
	/**
	 * 
	 * @param npcNames a list of possible targets
	 * @return RSNPC or null
	 */
	public static RSNPC getTarget(String[] npcNames)
	{
		return getTarget(npcNames,null,null);
	}
	
	/**
	 * 
	 * @param npcNames a list of possible targets
	 * @param area1 the area our NPC has to be in
	 * @return RSNPC or null
	 */
	public static RSNPC getTarget(String[] npcNames, RSArea area1)
	{
		return getTarget(npcNames,area1,null);
	}
	
	/**
	 * 
	 * @param npcName The NPC we should kill
	 * @param area1 one of the two areas our npc has to be in.
	 * @param area2 one of the two areas our npc has to be in.
	 * @return RSNPC or null
	 */
	
	public static RSNPC getTarget(String[] npcNames, RSArea area1, RSArea area2)
	{
		int targetIndex = 0;
		ArrayList<RSNPC> potentialTargets = new ArrayList<RSNPC>();
		RSNPC[] targetList = null;
		if(area1 == null && area2 == null) targetList = NPCs.findNearest(Filters.NPCs.nameContains(npcNames));
		if(area1 != null && area2 == null) targetList = NPCs.findNearest(Filters.NPCs.nameContains(npcNames).combine(Filters.NPCs.inArea(area1), true));
		if(area1 != null && area2 != null) targetList = NPCs.findNearest(Filters.NPCs.nameContains(npcNames).combine(Filters.NPCs.inArea(area1), true).combine(Filters.NPCs.inArea(area2), true));
			for(int i=0; i< targetList.length; i++)
			{
				RSNPC t = targetList[i];
				if(t != null && !t.isInCombat())
				{
					potentialTargets.add(t);
				}
			}
		
			RSNPC[] dtarget = new RSNPC[potentialTargets.size()];
			for(int j=0; j<potentialTargets.size(); j++)
			{
				if(targetIndex > dtarget.length) return null;
				dtarget[targetIndex] =  potentialTargets.get(j);
				targetIndex++;
			}
		RSNPC target = null;
		if(dtarget.length > 0)
		target = DAntiban.selectNextTarget(dtarget);
		return target;
	}
	
	
	/**
	 * @param target RSNPC Attacks the RSNPC if not null, sleeps after for about 800ms
	 */
	
	public static void Attack(RSNPC target)
	{
		RSPlayer player = Player.getRSPlayer();
		String npcName = null;
		if(target != null && target.getDefinition() != null) npcName = target.getDefinition().getName();
		if(npcName != null)
		{
			if(!target.isOnScreen() && !player.isMoving()) DAntiban.goToAnticipated(target.getPosition());
			DynamicClicking.clickRSNPC(target, "Attack "+npcName);
			canAttack = false;
			DAntiban.smartSleep(700,1000);
		}
	}
	
	
	/**
	 * 
	 * @return true if the player can fight or attack a target if the player is not null
	 */
	
	public static boolean playerCanFight()
	{
		RSPlayer player = Player.getRSPlayer();
		boolean underattack = underAttack();
		if((player != null && (!underattack && player.getInteractingCharacter() == null)) || canAttack)
			return true;
		return false;
	}
	
	/**
	 * 
	 * @return true if nothing with HP higher than 0 is interacting with us.
	 */
	
	private static boolean underAttack()
	{
		RSNPC[] all = NPCs.getAll();
		if(all.length > 0)
		{
			for(int i=0; i< all.length; i++)
			{
				RSNPC npc = all[i];
				if(npc != null && npc.isInteractingWithMe() && npc.getHealth() > 0)
					return true;
			}
		}
		return false;
	}
	
	/**
	 * checks if the target we are going to interact with is already under attack by someone else.
	 */
	public static void checkcanAttack()
	{
		RSPlayer player = Player.getRSPlayer();
		RSCharacter curTarget = null;
		if(player != null)
		curTarget = Player.getRSPlayer().getInteractingCharacter();
		if(curTarget != null && curTarget.isInCombat() && !curTarget.isInteractingWithMe())  setcanAttack(true);
	}
	
	public static void setcanAttack(boolean b)
	{
		canAttack = b;
	}
	
	public static boolean getcanAttack()
	{
		return canAttack;
	}
	
}
