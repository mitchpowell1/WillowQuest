package gameDisplayComponents;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

import factories.DungeonFactory;
import gameSettingComponents.DungeonDensity;
import gameSettingComponents.DungeonSize;
import gameSettingComponents.MonsterLevels;
import gameSettingComponents.TreasureSettings;

/***
 * Settings frame houses the settings menu for the GUI, and its associted components
 * and actions.
 * @author Mitch Powell
 *
 */
@SuppressWarnings("serial")
public class SettingsFrame extends JFrame{
	private MonsterLevels monstLevel;
	private DungeonDensity density;
	private TreasureSettings treasure;
	private DungeonSize size;
	
	private DungeonFactory fact;
	private JPanel settingsContent;
	private JPanel densityContent;
	private JPanel monsterContent;
	private JPanel sizeContent;
	private JPanel treasureContent;
	private JPanel buttonPanel;

	private ButtonGroup monsterSettings;
	private ButtonGroup sizeSettings;
	private ButtonGroup treasureSettings;
	private ButtonGroup densitySettings;
	private JButton select;
	private JButton cancel;
	/**
	 * Instantiate the settings Frame
	 * @param fact
	 */
	public SettingsFrame(DungeonFactory fact){
		this.fact = fact;
		this.monstLevel = fact.getMonsterLevel();
		this.density = fact.getRoomDensity();
		this.treasure = fact.getTreasureSettings();
		this.size = fact.getDungeonSize();
		this.setSize(1000,250);
		this.settingsContent = new JPanel(new GridLayout(0,2));
		initMonsterSettings();
		initDensitySettings();
		initSizeSettings();
		initTreasureSettings();
		initButtons();
		this.add(settingsContent, BorderLayout.NORTH);
		this.add(buttonPanel, BorderLayout.SOUTH);
	}
	
	private void initButtons() {
		buttonPanel = new JPanel();
		select = new JButton("Select");
		cancel = new JButton("Cancel");
		
		cancel.addActionListener(new cancelButtonListener());
		select.addActionListener(new submitButtonListener());
		buttonPanel.add(select);
		buttonPanel.add(cancel);
	}

	public void initMonsterSettings(){
		this.monsterContent = new JPanel();
		monsterContent.setBorder(BorderFactory.createTitledBorder("Monsters"));
		this.monsterSettings = new ButtonGroup();
		//Instantiate monster buttons
		JRadioButton lowMonsters = new JRadioButton("Low");
		JRadioButton medMonsters = new JRadioButton("Medium");
		JRadioButton highMonsters = new JRadioButton("High");
		//Set selected monster settings
		lowMonsters.setSelected(fact.getMonsterLevel() == MonsterLevels.LOW);
		medMonsters.setSelected(fact.getMonsterLevel() == MonsterLevels.MEDIUM);
		highMonsters.setSelected(fact.getMonsterLevel() == MonsterLevels.HIGH);
		
		lowMonsters.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				fact.setMonsterProb(MonsterLevels.LOW);
			}
			
		});
		
		medMonsters.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				fact.setMonsterProb(MonsterLevels.MEDIUM);
			}
		});
		
		highMonsters.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				fact.setMonsterProb(MonsterLevels.HIGH);
			}
		});
		//Add buttons to button group
		monsterSettings.add(lowMonsters);
		monsterSettings.add(medMonsters);
		monsterSettings.add(highMonsters);
		//Add buttons to panel
		monsterContent.add(lowMonsters);
		monsterContent.add(medMonsters);
		monsterContent.add(highMonsters);
		settingsContent.add(monsterContent);
	}
	
	public void initDensitySettings(){
		this.densityContent = new JPanel();
		densityContent.setBorder(BorderFactory.createTitledBorder("Room Density"));
		this.densitySettings = new ButtonGroup();
		
		JRadioButton lowDensity = new JRadioButton("Sparse");
		JRadioButton medDensity = new JRadioButton("Medium");
		JRadioButton highDensity = new JRadioButton("Dense");
		
		lowDensity.setSelected(fact.getRoomDensity() == DungeonDensity.SPARSE);
		medDensity.setSelected(fact.getRoomDensity() == DungeonDensity.MEDIUM);
		highDensity.setSelected(fact.getRoomDensity() == DungeonDensity.DENSE);
		
		lowDensity.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				fact.setRoomDensity(DungeonDensity.SPARSE);
			}
			
		});
		
		medDensity.addActionListener(new ActionListener(){
			
			@Override
			public void actionPerformed(ActionEvent e){
				fact.setRoomDensity(DungeonDensity.MEDIUM);
			}
		});
		
		highDensity.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				fact.setRoomDensity(DungeonDensity.DENSE);
			}
		});
		
		densitySettings.add(lowDensity);
		densitySettings.add(medDensity);
		densitySettings.add(highDensity);
		
		densityContent.add(lowDensity);
		densityContent.add(medDensity);
		densityContent.add(highDensity);
		
		settingsContent.add(densityContent);
	}
	
	public void initSizeSettings(){
		this.sizeContent = new JPanel();
		sizeContent.setBorder(BorderFactory.createTitledBorder("Dungeon Size"));
		this.sizeSettings = new ButtonGroup();
		
		JRadioButton smallSize = new JRadioButton("Small (50 x 50)");
		JRadioButton medSize = new JRadioButton("Medium (75 x 75)");
		JRadioButton lgSize = new JRadioButton("Large (100 x 100)");
		
		smallSize.setSelected(fact.getDungeonSize() == DungeonSize.SMALL);
		medSize.setSelected(fact.getDungeonSize() == DungeonSize.MEDIUM);
		lgSize.setSelected(fact.getDungeonSize() == DungeonSize.LARGE);
		
		smallSize.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				fact.setSize(DungeonSize.SMALL);
			}
			
		});
		
		medSize.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				fact.setSize(DungeonSize.MEDIUM);
			}
			
		});
		
		lgSize.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				fact.setSize(DungeonSize.LARGE);
			}
			
		});
		
		sizeSettings.add(smallSize);
		sizeSettings.add(medSize);
		sizeSettings.add(lgSize);
		
		sizeContent.add(smallSize);
		sizeContent.add(medSize);
		sizeContent.add(lgSize);
		
		settingsContent.add(sizeContent);
	}
	
	public void initTreasureSettings(){
		this.treasureContent = new JPanel();
		treasureContent.setBorder(BorderFactory.createTitledBorder("Treasure Settings"));
		this.treasureSettings = new ButtonGroup();
		
		JRadioButton beggarButton = new JRadioButton("Beggar");
		JRadioButton pirateButton = new JRadioButton("Pirate");
		JRadioButton kingButton = new JRadioButton("King");
		
		beggarButton.setSelected(fact.getTreasureSettings() == TreasureSettings.BEGGAR);
		pirateButton.setSelected(fact.getTreasureSettings() == TreasureSettings.PIRATE);
		kingButton.setSelected(fact.getTreasureSettings() == TreasureSettings.KING);
		
		beggarButton.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				fact.setTreasureSettings(TreasureSettings.BEGGAR);
			}
			
		});
		
		pirateButton.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				fact.setTreasureSettings(TreasureSettings.PIRATE);
			}
			
		});
		
		kingButton.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				fact.setTreasureSettings(TreasureSettings.KING);
			}
			
		});
		
		treasureSettings.add(beggarButton);
		treasureSettings.add(pirateButton);
		treasureSettings.add(kingButton);
		
		treasureContent.add(beggarButton);
		treasureContent.add(pirateButton);
		treasureContent.add(kingButton);
		
		settingsContent.add(treasureContent);
	}
	
	private class submitButtonListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			dispose();
		}
		
	}
	
	private class cancelButtonListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			fact.setSize(size);
			fact.setRoomDensity(density);
			fact.setMonsterProb(monstLevel);
			fact.setTreasureSettings(treasure);
			dispose();
		}
		
	}
}
