package scripts.DTools;

import java.awt.*;
import java.awt.event.*;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

import javax.swing.*;
import javax.swing.GroupLayout;
import javax.swing.LayoutStyle;
import javax.swing.border.*;
import org.tribot.api.General;
import org.tribot.api2007.Login;
import org.tribot.api2007.Player;


public class DTimer 
{

	/**
	 * @author TheD
	 * {@http://www.d-scripting.com}
	 * @category DTools
	 */
	
	
	private static long startTime = 0;
	
	private static int timeRan = 0;
	private static double multiplier = 0;
	private static int hours = 0;
	private static int minutes = 0;
	private static int seconds = 0;
	
	private static boolean end_after_time = false;
	private static int end_hour = 0;
	private static int end_minute = 0;
	private static int end_second = General.random(1, 59);
	
	private static DEndTimer g = new DEndTimer();
	private static boolean GUIWait = true;
	
	/**
	 * call in your main loop, if true endscript.
	 * @return true if we should end, false if we should not end yet
	 */
	
	public static boolean checkEndTimer()
	{
		setTime(); //updating time statistics
		if(end_after_time)
		{
			if((hours == end_hour && minutes >= end_minute && seconds >= end_second) || (hours >= end_hour && minutes > end_minute) || (hours > end_hour))
			{
				if(Player.getRSPlayer() != null && Player.getRSPlayer().isInCombat()) return false; //still in combat, not going to spam log-out.
				System.out.println("Starting log-out...");
				long starttime = System.currentTimeMillis();
				long endtime = starttime + 7500; //if we take longer than 7.5 seconds clicking logout, we exit the loop.
				while(Login.getLoginState() == Login.STATE.INGAME)
				{
					
					Login.logout();
					General.sleep(500,5000);
					if(Login.getLoginState() != Login.STATE.INGAME || endtime >= System.currentTimeMillis()) break;
				}
				System.out.println("Logged out...");
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Is called from everything that involves time, so we always have the correct time.
	 */
	
	private static void setTime()
	{
		timeRan = (int) (System.currentTimeMillis() - startTime);
		multiplier = timeRan / 3600000D;
		hours = timeRan / 3600000;
	    timeRan = timeRan - (hours*3600000);
	    minutes = timeRan / 60000;
	    timeRan = timeRan - (minutes*60000);
	    seconds = timeRan / 1000;
	    timeRan = timeRan - (seconds*1000);
	}
	
	
	/**
	 * Do you want to let the user decide wether to use EndTimer or not?
	 * use callGUI in those cases. 
	 */
	public static void callGUI()
	{
		g.setVisible(true);
		while(GUIWait)
		{
			General.sleep(500);
			if(!GUIWait) break;
		}
	}
	
	/**
	 * @param endHour after how many hours should the script be terminated ?
	 * @NOTE Both Minute & Second will be totally randomized
	 * @NOTE If you use this, you do not also have to call enableEnTimer(b)
	 */
	public static void setEnd(int endHour)
	{
		end_after_time = true;
		end_hour = endHour;
		end_minute = General.random(1, 59);
	}
	
	/**
	 * @param b should we end after time?
	 * @NOTE No need to call this if you used setEnd(endHour)
	 * Only useful if you want to disable endtimer when we already called it.
	 */
	public static void setEndtimer(boolean b)
	{
		end_after_time = b;
	}
	
	/**
	 * Only call this method once, and only when you want to start the the ScriptTimer
	 * 
	 */
	public static void startScriptTimer()
	{
		startTime = System.currentTimeMillis();
	}
	
	/**
	 * 
	 * @returns the hours run if you called startScriptTimer
	 */
	public static int getHour()
	{
		setTime(); //updating time statistics
		if(startTime != 0)
		return hours;
		return 0;
	}
	
	/**
	 * 
	 * @returns the minutes run if you called startScriptTimer
	 */
	public static int getMinute()
	{
		setTime(); //updating time statistics
		if(startTime != 0)
		return minutes;
		return 0;
	}
	
	/**
	 * 
	 * @returns the seconds run if you called startScriptTimer
	 */
	public static int getSecond()
	{
		setTime(); //updating time statistics
		if(startTime != 0)
		return seconds;
		return 0;
	}
	
	
	/**
	 * 
	 * @returns the end hour if we use end_after_time, if not returns 0
	 */
	public static int getEndHour()
	{
		if(end_after_time)
			return end_hour;
		return 0;
	}
	
	/**
	 * 
	 * @returns the end minute if we use end_after_time, if not returns 0
	 */
	public static int getEndMinute()
	{
		if(end_after_time)
			return end_minute;
		return 0;
	}
	
	/**
	 * 
	 * @returns the end seconds if we use end_after_time, if not returns 0
	 */
	public static int getEndSeconds()
	{
		if(end_after_time)
			return end_second;
		return 0;
	}
	
	
	/**
	 * @param i - the variable of which you want to know how much we did p/h
	 * @return how much of i we did per hour
	 */
	public static int getPH(int i)
	{
		setTime(); //updating time statistics
		return (int) (i / multiplier);
	}
	
	/**
	 * 
	 * @return true if we enabled end after time.
	 */
	public static boolean getEndAfterTime()
	{
		return end_after_time;
	}
	
	/**
	 * @author TheD
	 */
	public static class DEndTimer extends JFrame {
		public DEndTimer() {
			initComponents();
		}

		private void endtimerstartActionPerformed(ActionEvent e) 
		{
			if(useEndTimer.isSelected())
			{
				setEnd((int) hourSpinner.getValue());
			}
			else
			{
				end_after_time = false;
			}
			GUIWait = false;
			g.dispose();
		}

		private void whatisendtimerActionPerformed(ActionEvent e) 
		{
			openSite("http://www.d-scripting.com/whatisendtimer");
		}

		private void visittribotActionPerformed(ActionEvent e) {
			openSite("http://www.tribot.org");
		}

		private void initComponents() 
		{
			menuBar1 = new JMenuBar();
			menu1 = new JMenu();
			whatisendtimer = new JMenuItem();
			visittribot = new JMenuItem();
			dialogPane = new JPanel();
			contentPanel = new JPanel();
			useEndTimer = new JCheckBox();
			hourSpinner = new JSpinner();
			label1 = new JLabel();
			label2 = new JLabel();
			buttonBar = new JPanel();
			endtimerstart = new JButton();

			//======== this ========
			setAlwaysOnTop(true);
			setResizable(false);
			setTitle("EndTimer by TheD");
			Container contentPane = getContentPane();
			contentPane.setLayout(new BorderLayout());

			//======== menuBar1 ========
			{

				//======== menu1 ========
				{
					menu1.setText("General");

					//---- whatisendtimer ----
					whatisendtimer.setText("What is EndTimer?");
					whatisendtimer.addActionListener(e -> whatisendtimerActionPerformed(e));
					menu1.add(whatisendtimer);

					//---- visittribot ----
					visittribot.setText("Visit TRiBot.org");
					visittribot.addActionListener(e -> visittribotActionPerformed(e));
					menu1.add(visittribot);
				}
				menuBar1.add(menu1);
			}
			setJMenuBar(menuBar1);

			//======== dialogPane ========
			{
				dialogPane.setBorder(new EmptyBorder(12, 12, 12, 12));

				dialogPane.setBorder(new javax.swing.border.CompoundBorder(
					new javax.swing.border.TitledBorder(new javax.swing.border.EmptyBorder(0, 0, 0, 0),
						"DTimer", javax.swing.border.TitledBorder.CENTER,
						javax.swing.border.TitledBorder.BOTTOM, new java.awt.Font("Dialog", java.awt.Font.BOLD, 12),
						java.awt.Color.red), dialogPane.getBorder())); dialogPane.addPropertyChangeListener(new java.beans.PropertyChangeListener(){public void propertyChange(java.beans.PropertyChangeEvent e){if("border".equals(e.getPropertyName()))throw new RuntimeException();}});

				dialogPane.setLayout(new BorderLayout());

				//======== contentPanel ========
				{

					//---- useEndTimer ----
					useEndTimer.setText("Use EndTimer");
					useEndTimer.setFont(new Font("Tahoma", Font.PLAIN, 14));

					//---- hourSpinner ----
					hourSpinner.setFont(new Font("Tahoma", Font.PLAIN, 14));
					hourSpinner.setModel(new SpinnerNumberModel(0, 0, null, 1));

					//---- label1 ----
					label1.setText("End after");
					label1.setFont(new Font("Tahoma", Font.PLAIN, 14));

					//---- label2 ----
					label2.setText("hours");
					label2.setFont(new Font("Tahoma", Font.PLAIN, 14));

					GroupLayout contentPanelLayout = new GroupLayout(contentPanel);
					contentPanel.setLayout(contentPanelLayout);
					contentPanelLayout.setHorizontalGroup(
						contentPanelLayout.createParallelGroup()
							.addGroup(contentPanelLayout.createSequentialGroup()
								.addComponent(useEndTimer)
								.addGap(0, 251, Short.MAX_VALUE))
							.addGroup(contentPanelLayout.createSequentialGroup()
								.addGap(21, 21, 21)
								.addComponent(label1)
								.addGap(40, 40, 40)
								.addComponent(hourSpinner, GroupLayout.PREFERRED_SIZE, 88, GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
								.addComponent(label2)
								.addContainerGap(110, Short.MAX_VALUE))
					);
					contentPanelLayout.setVerticalGroup(
						contentPanelLayout.createParallelGroup()
							.addGroup(contentPanelLayout.createSequentialGroup()
								.addComponent(useEndTimer)
								.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
								.addGroup(contentPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
									.addComponent(label1)
									.addComponent(label2)
									.addComponent(hourSpinner, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
								.addGap(0, 12, Short.MAX_VALUE))
					);
				}
				dialogPane.add(contentPanel, BorderLayout.CENTER);

				//======== buttonBar ========
				{
					buttonBar.setBorder(new EmptyBorder(12, 0, 0, 0));
					buttonBar.setLayout(new GridBagLayout());
					((GridBagLayout)buttonBar.getLayout()).columnWidths = new int[] {0, 80};
					((GridBagLayout)buttonBar.getLayout()).columnWeights = new double[] {1.0, 0.0};

					//---- endtimerstart ----
					endtimerstart.setText("Start");
					endtimerstart.setFont(new Font("Tahoma", Font.PLAIN, 16));
					endtimerstart.addActionListener(e -> endtimerstartActionPerformed(e));
					buttonBar.add(endtimerstart, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0,
						GridBagConstraints.CENTER, GridBagConstraints.BOTH,
						new Insets(0, 0, 0, 0), 0, 0));
				}
				dialogPane.add(buttonBar, BorderLayout.SOUTH);
			}
			contentPane.add(dialogPane, BorderLayout.CENTER);
			pack();
			setLocationRelativeTo(getOwner());
		}

		private JMenuBar menuBar1;
		private JMenu menu1;
		private JMenuItem whatisendtimer;
		private JMenuItem visittribot;
		private JPanel dialogPane;
		private JPanel contentPanel;
		private JCheckBox useEndTimer;
		private JSpinner hourSpinner;
		private JLabel label1;
		private JLabel label2;
		private JPanel buttonBar;
		private JButton endtimerstart;
		//end
	}

	
	public static void openSite(String url)
	{
		 URL a = null;
			try {
				a = new URL(url);
			} catch (MalformedURLException e1) {
				System.out.println("ERROR OPENING URL");
				e1.printStackTrace();
			}
			openWebpage(a);
	}  
	  
		public static void openWebpage(URI uri) {
		    Desktop desktop = Desktop.isDesktopSupported() ? Desktop.getDesktop() : null;
		    if (desktop != null && desktop.isSupported(Desktop.Action.BROWSE)) {
		        try {
		            desktop.browse(uri);
		        } catch (Exception e) {
		            e.printStackTrace();
		        }
		    }
		}

		public static void openWebpage(URL url) {
		    try {
		        openWebpage(url.toURI());
		    } catch (URISyntaxException e) {
		        e.printStackTrace();
		    }
		}

}
