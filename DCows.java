package scripts;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;

import org.tribot.api.General;
import org.tribot.api.input.Mouse;
import org.tribot.api2007.Banking;
import org.tribot.api2007.Inventory;
import org.tribot.api2007.Login;
import org.tribot.api2007.Player;
import org.tribot.api2007.Skills;
import org.tribot.api2007.WebWalking;
import org.tribot.api2007.ext.Doors;
import org.tribot.api2007.types.RSArea;
import org.tribot.api2007.types.RSItem;
import org.tribot.api2007.types.RSObject;
import org.tribot.api2007.types.RSPlayer;
import org.tribot.api2007.types.RSTile;
import org.tribot.script.Script;
import org.tribot.script.ScriptManifest;
import org.tribot.script.interfaces.Painting;

import scripts.DCowKiller.GUI;
import scripts.DTools.DAntiban;
import scripts.DTools.DCombat;
import scripts.DTools.DGrandExchange;
import scripts.DTools.DTimer;
import scripts.DTools.Newsloader;


/**
 * 
 * @author TheD
 * Created at: 06/07/2016
 * {@www.d-scripting.com}
 *
 */

@ScriptManifest(authors = { "TheD" }, category = "TheDScripts", name = "DCows")
public class DCows extends Script implements Painting
{

	//Painting
	private final Font font2 = new Font("Arial", Font.BOLD, 14);
	private GUI gui = new GUI();
	
	private State scriptState = State.NOTHING;
  
	//Locations
	  
		private RSArea cowarea = new RSArea(new RSTile(3155,3316), new RSTile(3202,3344));
		private RSArea FENCE_AREA = new RSArea(new RSTile(3170,3310,0), new RSTile(3186,3320));
		private RSTile GATE_POSITION = new RSTile(3176,3315,0);
		private RSTile cowPos = new RSTile(3177,3319,0);
	
	//Targets
	private String[] potentialTargets = {"Cow","Cow calf"};
	private String[] lootList = {"123a314351ad","123a314351ad","123a314351ad"}; // just creating items that do not exist. Tried private String[] lootList = new String[3] but that makes the bot search for item named null ;S

	
	//Settings
	private boolean buryBones = false;
	private boolean useFood = false;
	private boolean ABC2 = false;
	private boolean justAttacked = false;
	private String foodName = "";
	private int foodAmt = 0;
	
	//Trackers
	private int xpGained = 0;
	private int startSTRxp = 0;
	private int startATTxp = 0;
	private int startDEFxp = 0;
	private int startRNGxp = 0;
	private int startMGCxp = 0;
	
	private int currentSTRxp = 0;
	private int currentATTxp = 0;
	private int currentDEFxp = 0;
	private int currentRNGxp = 0;
	private int currentMGCxp = 0;
	
	private int bonePrice = 0;
	private int cowhidePrice = 0;
	private int rawbeefPrice = 0;
	private int boneLooted = 0;
	private int cowhideLooted = 0;
	private int rawbeefLooted = 0;
	private int totalProfit = 0;
	//DGrandExchange init so we can load price
	private static DGrandExchange dge = new DGrandExchange();
	
	
	
	private enum State { NOTHING, KILL, LOOT, WALK_TO_FENCE, OPEN_FENCE, WALK_TO_BANK, OPEN_BANK, 
		DEPOSIT_ALL,WALK_FROM_BANK_TO_FENCE, BURY, WITHDRAW_FOOD,WALK_IN_COW_AREA
		};
		
	@Override
	public void run() 
	{
		
		initializeScript();

		while(true)
		{
			if(ABC2) DAntiban.timedActions();
			loop();
			sleep(100,200);
		}
		
	}
	
	private void loop()
	{
		scriptState = getState();
		RSPlayer player = Player.getRSPlayer();
		switch(scriptState)
		{
			case KILL:
				DAntiban.activateRun();
				DCombat.Attack(DCombat.getTarget(potentialTargets,cowarea));
				DAntiban.setLastUnderAttackTime(System.currentTimeMillis());
				justAttacked = true;
			break;
		
			case LOOT:
				DAntiban.activateRun();
				DCombat.loot(DCombat.getLootableItem(lootList, cowarea));
			break;
			
			case WALK_TO_FENCE:
				WebWalking.walkTo(new RSTile(3177,3317,0));
				DAntiban.smartSleep(General.random(400, 600),General.random(700, 1000));
			break;
			
			case OPEN_FENCE:
				RSObject fence = Doors.getDoorAt(GATE_POSITION);
				if(fence != null && player != null && !player.isMoving())
				{
					if(!fence.isOnScreen()) DAntiban.goToAnticipated(fence.getPosition()); 
					else Doors.handleDoor(fence, true);
					DAntiban.smartSleep(500,1500);
				}
			break;
			
			case WALK_TO_BANK:
				WebWalking.walkToBank();
			break;
			
			case OPEN_BANK:
				DAntiban.openPreferedBank();
			break;
			
			case DEPOSIT_ALL:
				Banking.depositAll();
				boneLooted = 0; //Resetting
				cowhideLooted = 0; //Resetting
				rawbeefLooted = 0; //Resetting
			break;
			
			case WALK_FROM_BANK_TO_FENCE:
				WebWalking.walkTo(new RSTile(General.random(3174, 3179),General.random(3312, 3314),0));
			break;
			
			case BURY:
				RSItem[] myBones = Inventory.find("Bones");
				RSItem boneTobury = null;
				if(myBones.length > 0)
				boneTobury = myBones[0];
				if(boneTobury != null)
				{
					boneTobury.click("bury");
				}
				DAntiban.smartSleep(500,900);
			break;
			
			case WITHDRAW_FOOD:
				if(Banking.isInBank() && !Banking.isBankScreenOpen())
					DAntiban.openPreferedBank();
				if(Banking.isBankScreenOpen())
					Banking.withdraw(foodAmt, foodName);
				DAntiban.smartSleep(1000,1700);
			break;
			
			case WALK_IN_COW_AREA:
				DAntiban.activateRun();
				DAntiban.goToAnticipated(cowPos);
			break;
			
			case NOTHING:
				if(justAttacked)
				{
					DAntiban.generateTrackers(General.random(800, 1200));
					DAntiban.sleepReactionTime();
					justAttacked = false;
				}
				DCombat.checkcanAttack();
				checkProfit(); //Doing nothing, so we might aswell check if we made any moneys.
			break;
		}
	}
	
	private State getState()
	{
		Eat();
		boolean canFight = DCombat.playerCanFight();
		boolean canLoot = DCombat.canLoot(lootList, cowarea);
		boolean inventoryFull = Inventory.isFull();
		RSObject gate = Doors.getDoorAt(GATE_POSITION);
		RSPlayer p = Player.getRSPlayer();
		RSTile pos = null;
		if(p != null)
		pos = p.getPosition();
		if(pos == null || p == null) return State.NOTHING;
		if(cowarea.contains(pos) && canLoot && canFight && !inventoryFull) return State.LOOT;
		else if(buryBones && Inventory.find("Bones").length > 0 && canFight) return State.BURY;
		else if(cowarea.contains(pos) && !canLoot && canFight && !inventoryFull) return State.KILL;
		else if(cowarea.contains(pos) && inventoryFull && !FENCE_AREA.contains(pos) && pos.getY() > 3315) return State.WALK_TO_FENCE;
		else if(FENCE_AREA.contains(pos) && inventoryFull && gate != null) return State.OPEN_FENCE;
		else if(FENCE_AREA.contains(pos) && gate != null && pos.getY() < 3316) return State.OPEN_FENCE;
		else if(FENCE_AREA.contains(pos) && gate == null && pos.getY() < 3316) return State.WALK_IN_COW_AREA;
		else if(inventoryFull && !Banking.isInBank()) return State.WALK_TO_BANK;
		else if(inventoryFull && Banking.isInBank() && !Banking.isBankScreenOpen()) return State.OPEN_BANK;
		else if(Inventory.getAll().length > 0 && Banking.isInBank() && Banking.isBankScreenOpen()) return State.DEPOSIT_ALL;
		else if(useFood && Inventory.getAll().length < 1 && Banking.isInBank()) return State.WITHDRAW_FOOD;
		else if(!Inventory.isFull() && !FENCE_AREA.contains(pos) && !cowarea.contains(pos)) return State.WALK_FROM_BANK_TO_FENCE;
		return State.NOTHING;
	}
	
	private void checkProfit()
	{
		int boneInInv = Inventory.find("Bones").length;
		int cowhideInInv = Inventory.find("Cowhide").length;
		int rawbeefInInv = Inventory.find("Raw beef").length;
		if(boneInInv > boneLooted)
		{
			boneLooted++;
			if(!buryBones) totalProfit += bonePrice;
		}
		if(cowhideInInv > cowhideLooted)
		{
			cowhideLooted++;
			totalProfit += cowhidePrice;
		}
		if(rawbeefInInv > rawbeefLooted)
		{
			rawbeefLooted++;
			totalProfit += rawbeefPrice;
		}
	}
	
	private void Eat()
	{
		RSItem foodToEat = null;
		if(Inventory.find(foodName).length > 0) foodToEat = Inventory.find(foodName)[0];
		if(foodToEat != null)
		{
			if(DAntiban.eat("Eat", foodToEat))
			{
				DAntiban.smartSleep(700,1100);
			}
		}
	}
	
	private void initializeScript()
	{
		this.setLoginBotState(false); //Don't want the bot to log-in before we started the script.
		gui.setVisible(true);
		while(gui.getGUIWait())
		{
			sleep(500);
			if(!gui.getGUIWait()) break;
		}
		ABC2 = gui.getabc2();
		buryBones = gui.getBurybones();
		if(gui.getBones()) lootList[0] = "Bones"; //Not checking if it has length, since it has been declared initialy.
		if(gui.getCowhides()) lootList[1] = "Cowhide";
		if(gui.getRawbeef()) lootList[2] = "Raw beef";
		foodAmt = gui.getFoodamt();
		gui.dispose();
		DTimer.startScriptTimer();
		DTimer.callGUI();
		General.useAntiBanCompliance(ABC2);
		this.setLoginBotState(true); //Enabling auto log-in again
		Mouse.setSpeed(General.random(150, 160));
		bonePrice = dge.getData(526).getPrice();
		cowhidePrice = dge.getData(1739).getPrice();
		rawbeefPrice = dge.getData(2132).getPrice();
		if(Login.getLoginState() != Login.STATE.INGAME) return; //don't load any details in, we're not even in game.
		startSTRxp = Skills.getXP(Skills.SKILLS.STRENGTH);
		startATTxp = Skills.getXP(Skills.SKILLS.ATTACK);
		startDEFxp = Skills.getXP(Skills.SKILLS.DEFENCE);
		startRNGxp = Skills.getXP(Skills.SKILLS.RANGED);
		startMGCxp = Skills.getXP(Skills.SKILLS.MAGIC);
		boneLooted = Inventory.find("Bones").length;
		cowhideLooted = Inventory.find("Cowhide").length;
		rawbeefLooted = Inventory.find("Raw beef").length;

	}
	
	private void calculatePaint()
	{
		currentSTRxp = Skills.getXP(Skills.SKILLS.STRENGTH) - startSTRxp;
		currentATTxp = Skills.getXP(Skills.SKILLS.ATTACK) - startATTxp;
		currentDEFxp = Skills.getXP(Skills.SKILLS.DEFENCE) - startDEFxp;
		currentRNGxp = Skills.getXP(Skills.SKILLS.RANGED) - startRNGxp;
		currentMGCxp = Skills.getXP(Skills.SKILLS.MAGIC) - startMGCxp;
		xpGained = currentSTRxp + currentATTxp + currentDEFxp + currentRNGxp + currentMGCxp;
	}
	
	private Image getImage(String url) 
	{
		try 
		{
			return ImageIO.read(new URL(url));
		} 
		catch(IOException e) 
		{
			return null;
		}
	}
	
	private final Image dcowspaint = getImage("http://d-scripting.com/DEssentials/paint/dcows.png");
	private final RenderingHints aa = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
	private final Color darkred = new Color(193,48,38);
	
    int paintOffset = 325;
	
	@Override
	public void onPaint(Graphics g) 
	{
		calculatePaint();
		g.setColor(Color.CYAN);
		g.setFont(font2);
		g.drawString("NEWS: "+ Newsloader.getLatestNews(), 40,40);
		Graphics2D g2 = (Graphics2D)g;
		g2.setRenderingHints(aa);
		g2.drawImage(dcowspaint, 0, paintOffset-5, null);
		g.setColor(Color.CYAN);
		g.setColor(darkred);
		g.setFont(font2);
		g.drawString("Time ran: "+ DTimer.getHour() + ":" + DTimer.getMinute() + ":" + DTimer.getSecond(), 5,paintOffset+60);
		g.drawString("Experience gained: "+xpGained+" ("+DTimer.getPH(xpGained)+" PH)", 5,paintOffset+75);
		g.drawString("Money made: "+totalProfit+" ("+DTimer.getPH(totalProfit)+" PH)", 5,paintOffset+90);
		if(DTimer.getEndAfterTime()) g.drawString("Ending script when we ran "+(DTimer.getEndHour())+":"+(DTimer.getEndMinute())+":"+(DTimer.getEndSeconds())+" (HH:MM:SS)", 5,paintOffset+105);
		if(!DTimer.getEndAfterTime()) g.drawString("Not using End-timer or not yet set-up", 5,paintOffset+105);
		g.drawString("State: "+scriptState, 5,paintOffset+120);
		g.drawString("Version: 1.00", 5,paintOffset+155); 
		
	}

}
