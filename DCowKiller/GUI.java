package scripts.DCowKiller;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.GroupLayout;
import javax.swing.border.*;

import scripts.DTools.DWeb;

/**
 * @author TheD
 */
public class GUI extends JFrame {
	public GUI() {
		initComponents();
	}
	
	private boolean GUIWait = true;
	private boolean abc2 = false;
	private boolean bones = false;
	private boolean cowhides = false;
	private boolean rawbeef = false;
	private boolean burybones = false;
	private int foodamt = 1;
	private String foodName = "";
	
	
	public boolean getGUIWait()
	{
		return GUIWait;
	}
	
	public boolean getabc2()
	{
		return abc2;
	}

	public boolean getBones()
	{
		return bones;
	}
	
	public boolean getCowhides()
	{
		return cowhides;
	}
	
	public boolean getRawbeef()
	{
		return rawbeef;
	}
	
	public boolean getBurybones()
	{
		return burybones;
	}
	
	public int getFoodamt()
	{
		return foodamt;
	}
	
	public String getfoodName()
	{
		return foodName;
	}
	
	private void StartScriptActionPerformed(ActionEvent e) 
	{
		abc2 = useABC2.isSelected();
		bones = lootBones.isSelected();
		cowhides = lootCowhides.isSelected();
		rawbeef = lootRawbeef.isSelected();
		burybones = buryTheBones.isSelected();
		foodamt = (int) spinner1.getValue();
		String selectedFood = comboBox1.getSelectedItem().toString();
		if(selectedFood.startsWith("1. "))
		{
			foodName = "";
		}
		else if(selectedFood.startsWith("2. "))
		{
			foodName = "Cooked meat";
		}
		else if(selectedFood.startsWith("3. "))
		{
			foodName = "Cooked chickens";
		}
		else if(selectedFood.startsWith("4. "))
		{
			foodName = "Trout";
		}
		else if(selectedFood.startsWith("5. "))
		{
			foodName = "Salmon";
		}
		else if(selectedFood.startsWith("6. "))
		{
			foodName = "Tuna";
		}
		else if(selectedFood.startsWith("7. "))
		{
			foodName = "Lobster";
		}
		
		GUIWait = false;
	}

	private void menuItemTribotorgActionPerformed(ActionEvent e) 
	{
		DWeb.openSite("www.tribot.org/forums");
	}

	private void initComponents() 
	{
		menubar = new JMenuBar();
		menu = new JMenu();
		menuItemTribotorg = new JMenuItem();
		dialogPane = new JPanel();
		buttonBar = new JPanel();
		StartScript = new JButton();
		setupTabbedPane = new JTabbedPane();
		panel2 = new JPanel();
		label2 = new JLabel();
		scrollPane1 = new JScrollPane();
		textPane1 = new JTextPane();
		panel3 = new JPanel();
		lootingLabel = new JLabel();
		lootBones = new JCheckBox();
		lootCowhides = new JCheckBox();
		lootRawbeef = new JCheckBox();
		buryTheBones = new JCheckBox();
		foodLabel = new JLabel();
		label4 = new JLabel();
		label5 = new JLabel();
		spinner1 = new JSpinner();
		comboBox1 = new JComboBox<>();
		useABC2 = new JCheckBox();

		//set-up
		setAlwaysOnTop(true);
		setResizable(false);
		setTitle("DScripts by TheD");
		Container contentPane = getContentPane();
		contentPane.setLayout(new BorderLayout());

		//menubar
		{

			//menu
			{
				menu.setText("General");

				// menuItem for tribot website
				menuItemTribotorg.setText("www.tribot.org");
				menuItemTribotorg.addActionListener(e -> menuItemTribotorgActionPerformed(e));
				menu.add(menuItemTribotorg);
			}
			menubar.add(menu);
		}
		setJMenuBar(menubar);

		//======== dialogPane ========
		{
			dialogPane.setBorder(new EmptyBorder(12, 12, 12, 12));

			// DScripting mark
			dialogPane.setBorder(new javax.swing.border.CompoundBorder(
				new javax.swing.border.TitledBorder(new javax.swing.border.EmptyBorder(0, 0, 0, 0),
					"DScripting limited", javax.swing.border.TitledBorder.CENTER,
					javax.swing.border.TitledBorder.BOTTOM, new java.awt.Font("Dialog", java.awt.Font.BOLD, 12),
					java.awt.Color.red), dialogPane.getBorder())); dialogPane.addPropertyChangeListener(new java.beans.PropertyChangeListener(){public void propertyChange(java.beans.PropertyChangeEvent e){if("border".equals(e.getPropertyName()))throw new RuntimeException();}});

			dialogPane.setLayout(new BorderLayout());

			//======== buttonBar ========
			{
				buttonBar.setBorder(new EmptyBorder(12, 0, 0, 0));
				buttonBar.setLayout(new GridBagLayout());
				((GridBagLayout)buttonBar.getLayout()).columnWidths = new int[] {0, 80};
				((GridBagLayout)buttonBar.getLayout()).columnWeights = new double[] {1.0, 0.0};

				//---- StartScript ----
				StartScript.setText("Start Script");
				StartScript.addActionListener(e -> StartScriptActionPerformed(e));
				buttonBar.add(StartScript, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0,
					GridBagConstraints.CENTER, GridBagConstraints.BOTH,
					new Insets(0, 0, 0, 0), 0, 0));
			}
			dialogPane.add(buttonBar, BorderLayout.SOUTH);

			//======== setupTabbedPane ========
			{
				setupTabbedPane.setFont(new Font("Tahoma", Font.PLAIN, 12));

				//======== panel2 ========
				{

					//---- label2 ----
					label2.setText("DCows");
					label2.setFont(new Font("Tahoma", Font.PLAIN, 48));

					//======== scrollPane1 ========
					{

						//---- textPane1 ----
						textPane1.setFont(new Font("Tahoma", Font.PLAIN, 12));
						textPane1.setText("Thank you for using DCows,\n\nThis script will kill cows at northen lumbridge\nUse the Setup tab for the settings of your liking.\n\nRegards,\n\nDScripting");
						scrollPane1.setViewportView(textPane1);
					}

					GroupLayout panel2Layout = new GroupLayout(panel2);
					panel2.setLayout(panel2Layout);
					panel2Layout.setHorizontalGroup(
						panel2Layout.createParallelGroup()
							.addGroup(panel2Layout.createSequentialGroup()
								.addComponent(label2)
								.addGap(0, 345, Short.MAX_VALUE))
							.addGroup(panel2Layout.createSequentialGroup()
								.addContainerGap()
								.addComponent(scrollPane1, GroupLayout.DEFAULT_SIZE, 470, Short.MAX_VALUE)
								.addContainerGap())
					);
					panel2Layout.setVerticalGroup(
						panel2Layout.createParallelGroup()
							.addGroup(panel2Layout.createSequentialGroup()
								.addComponent(label2)
								.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
								.addComponent(scrollPane1, GroupLayout.PREFERRED_SIZE, 240, GroupLayout.PREFERRED_SIZE)
								.addGap(0, 66, Short.MAX_VALUE))
					);
				}
				setupTabbedPane.addTab("General", panel2);

				//======== panel3 ========
				{

					//---- lootingLabel ----
					lootingLabel.setText("Looting");
					lootingLabel.setFont(new Font("Tahoma", Font.PLAIN, 16));

					//---- lootBones ----
					lootBones.setText("Bones");
					lootBones.setFont(new Font("Tahoma", Font.PLAIN, 12));

					//---- lootCowhides ----
					lootCowhides.setText("Cowhides");
					lootCowhides.setFont(new Font("Tahoma", Font.PLAIN, 12));

					//---- lootRawbeef ----
					lootRawbeef.setText("Raw beef");
					lootRawbeef.setFont(new Font("Tahoma", Font.PLAIN, 12));

					//---- buryTheBones ----
					buryTheBones.setText("Bury bones");
					buryTheBones.setFont(new Font("Tahoma", Font.PLAIN, 12));

					//---- foodLabel ----
					foodLabel.setText("Food");
					foodLabel.setFont(new Font("Tahoma", Font.PLAIN, 16));

					//---- label4 ----
					label4.setText("Food to eat:");
					label4.setFont(new Font("Tahoma", Font.PLAIN, 12));

					//---- label5 ----
					label5.setText("Food amount:");
					label5.setFont(new Font("Tahoma", Font.PLAIN, 12));

					//---- spinner1 ----
					spinner1.setModel(new SpinnerNumberModel(1, 1, 28, 1));
					spinner1.setFont(new Font("Tahoma", Font.PLAIN, 12));

					//---- comboBox1 ----
					comboBox1.setFont(new Font("Tahoma", Font.PLAIN, 12));
					comboBox1.setModel(new DefaultComboBoxModel<>(new String[] {
						"1. Don't eat food",
						"2. Cooked meat",
						"3. Cooked Chicken",
						"4. Trout",
						"5. Salmon",
						"6. Tuna",
						"7. Lobster"
					}));

					//---- useABC2 ----
					useABC2.setText("Use ABC2");
					useABC2.setFont(new Font("Tahoma", Font.PLAIN, 12));
					useABC2.setSelected(true);

					GroupLayout panel3Layout = new GroupLayout(panel3);
					panel3.setLayout(panel3Layout);
					panel3Layout.setHorizontalGroup(
						panel3Layout.createParallelGroup()
							.addGroup(panel3Layout.createSequentialGroup()
								.addContainerGap()
								.addGroup(panel3Layout.createParallelGroup()
									.addComponent(useABC2)
									.addGroup(panel3Layout.createSequentialGroup()
										.addComponent(label5)
										.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
										.addComponent(spinner1, GroupLayout.PREFERRED_SIZE, 52, GroupLayout.PREFERRED_SIZE))
									.addComponent(lootRawbeef)
									.addComponent(lootCowhides)
									.addGroup(panel3Layout.createSequentialGroup()
										.addComponent(lootBones)
										.addGap(18, 18, 18)
										.addComponent(buryTheBones))
									.addComponent(lootingLabel)
									.addComponent(foodLabel)
									.addGroup(panel3Layout.createSequentialGroup()
										.addComponent(label4)
										.addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
										.addComponent(comboBox1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
								.addContainerGap(273, Short.MAX_VALUE))
					);
					panel3Layout.setVerticalGroup(
						panel3Layout.createParallelGroup()
							.addGroup(panel3Layout.createSequentialGroup()
								.addContainerGap()
								.addComponent(lootingLabel)
								.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
								.addGroup(panel3Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
									.addComponent(lootBones)
									.addComponent(buryTheBones))
								.addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
								.addComponent(lootCowhides)
								.addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
								.addComponent(lootRawbeef)
								.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
								.addComponent(foodLabel)
								.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
								.addGroup(panel3Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
									.addComponent(label4)
									.addComponent(comboBox1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
								.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
								.addGroup(panel3Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
									.addComponent(label5)
									.addComponent(spinner1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
								.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 155, Short.MAX_VALUE)
								.addComponent(useABC2)
								.addContainerGap())
					);
				}
				setupTabbedPane.addTab("Setup", panel3);
			}
			dialogPane.add(setupTabbedPane, BorderLayout.NORTH);
		}
		contentPane.add(dialogPane, BorderLayout.CENTER);
		pack();
		setLocationRelativeTo(getOwner());
	}
	
	private JMenuBar menubar;
	private JMenu menu;
	private JMenuItem menuItemTribotorg;
	private JPanel dialogPane;
	private JPanel buttonBar;
	private JButton StartScript;
	private JTabbedPane setupTabbedPane;
	private JPanel panel2;
	private JLabel label2;
	private JScrollPane scrollPane1;
	private JTextPane textPane1;
	private JPanel panel3;
	private JLabel lootingLabel;
	private JCheckBox lootBones;
	private JCheckBox lootCowhides;
	private JCheckBox lootRawbeef;
	private JCheckBox buryTheBones;
	private JLabel foodLabel;
	private JLabel label4;
	private JLabel label5;
	private JSpinner spinner1;
	private JComboBox<String> comboBox1;
	private JCheckBox useABC2;

}
