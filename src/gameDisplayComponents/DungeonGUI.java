package gameDisplayComponents;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;

import factories.DungeonFactory;

public class DungeonGUI extends JFrame{
	private static final long serialVersionUID = 1L;
	private JMenuBar menuBar;
	private JMenu fileMenu;
	private JMenuItem saveItem;
	private JMenuItem settingsItem;
	private JFrame settingsFrame;
	private JScrollPane scrollPane;
	private JPanel contentPanel;
	private JPanel buttonPanel;
	private JLabel contentLabel;
	private JButton genButton;
	private HTMLDungeonPrinter print;
	private DungeonFactory fact;
	
	/***
	 * Construct a new swing Dungeon GUI
	 */
	public DungeonGUI(HTMLDungeonPrinter print, DungeonFactory fact){
		this.print = print;
		this.fact = fact;
		initMenu();
		initGUI();
		initSettingsFrame();
		initContentPanel();
		initScrollPane();
		initGenerateButton();
		revalidate();
	}
	
	/***
	 * Initialize the dungeon generation button and add an event listener.
	 */
	private void initGenerateButton() {
		buttonPanel = new JPanel();
		genButton = new JButton("Generate Dungeon!");
		buttonPanel.add(genButton, BorderLayout.EAST);
		genButton.addActionListener(new generateButtonListener());
		this.add(buttonPanel, BorderLayout.SOUTH);
		
	}

	/***
	 * Initialize the content of the frame.
	 */
	private void initContentPanel() {
		contentPanel = new JPanel();
		contentLabel = new JLabel("Test");
		contentPanel.add(contentLabel);
		this.add(contentPanel, BorderLayout.CENTER);
		contentPanel.revalidate();
		
		
	}

	/***
	 * Initialize the frame
	 */
	private void initGUI(){
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setTitle("WillowQuest!");
		this.setSize(1000,750);
		this.setLayout(new BorderLayout());
		this.setVisible(true);
		this.setJMenuBar(menuBar);
	}
	
	/***
	 * Create the settings menu frame
	 */
	private void initSettingsFrame(){
		this.settingsFrame = new SettingsFrame(fact);
		
	}
	
	/***
	 * Initialize the menu bar at the top of the frame, and add the menu items.
	 */
	private void initMenu(){
		this.menuBar = new JMenuBar();
		this.fileMenu = new JMenu("Dungeon");
		this.saveItem = new JMenuItem("Save Dungeon");
		this.settingsItem = new JMenuItem("Dungeon Settings");
		menuBar.add(fileMenu);
		fileMenu.add(saveItem);
		fileMenu.add(settingsItem);
		settingsItem.addActionListener(new settingsListener());
	}
	
	/***
	 * Initialize the scrolling pane
	 */
	private void initScrollPane(){
		this.scrollPane = new JScrollPane(this.contentPanel);
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		this.add(scrollPane, BorderLayout.CENTER);
		revalidate();
	}
	
	/***
	 * Opens the settings menu
	 * @author Mitch Powell
	 *
	 */
	private class settingsListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			settingsFrame = new SettingsFrame(fact);
			settingsFrame.setVisible(true);
		}
		
	}
	
	/***
	 * Generates a new dungeon
	 * @author Mitch Powell
	 *
	 */
	private class generateButtonListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			//Print an html dungeon representation to the contentLabel
			contentLabel.setText(print.printDungeon(fact.getDungeon()));
			contentPanel.revalidate();
		}
		
	}
}
