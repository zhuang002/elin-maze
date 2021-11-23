import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import java.awt.FlowLayout;
import javax.swing.SwingConstants;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.awt.event.ActionEvent;

public class MazeUI {

	private JFrame frame;
	private JTextField textFieldFilePath;
	private Maze maze = new Maze();
	private JPanel gridPanel = null;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MazeUI window = new MazeUI();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public MazeUI() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 800, 600);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JPanel panelNorth = new JPanel();
		frame.getContentPane().add(panelNorth, BorderLayout.NORTH);
		panelNorth.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		JButton btnGenerate = new JButton("Generate random Maz");
		btnGenerate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				maze.generateMaze();
				char[][] grid = maze.getGrid();
				displayGrid(grid);
			}
		});
		panelNorth.add(btnGenerate);
		
		JLabel lblNewLabel = new JLabel("or");
		panelNorth.add(lblNewLabel);
		
		textFieldFilePath = new JTextField();
		panelNorth.add(textFieldFilePath);
		textFieldFilePath.setColumns(10);
		
		JButton btnDone = new JButton("Done");
		btnDone.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String dir = System.getProperty("user.dir");
				String path = textFieldFilePath.getText();
				try {
					
					maze.loadFile(new File(dir+"\\"+path));
					char[][] grid = maze.getGrid();
					displayGrid(grid);
				} catch(FileNotFoundException ex) {
					JOptionPane.showMessageDialog(frame, dir+"\\"+path+" not found.");
					
				}
			}
		});
		panelNorth.add(btnDone);
		
		JPanel panelSouth = new JPanel();
		frame.getContentPane().add(panelSouth, BorderLayout.SOUTH);
		
		JButton btnFindPath = new JButton("Find Path");
		btnFindPath.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				maze.findPath();
				char[][] grid = maze.getGrid();
				displayGrid(grid);
			}
		});
		panelSouth.add(btnFindPath);
		
		JPanel panelCenter = new JPanel();
		frame.getContentPane().add(panelCenter, BorderLayout.CENTER);
		panelCenter.setLayout(new GridLayout(20, 20, 0, 0));
		
		for (int i=0;i<20;i++) {
			for (int j=0;j<20;j++) {
				JLabel label = new JLabel("B");
				label.setBackground(Color.darkGray);
				label.setForeground(Color.white);
				label.setOpaque(true);
				label.setBorder(BorderFactory.createLineBorder(Color.black));
				label.setHorizontalAlignment(SwingConstants.CENTER);
				panelCenter.add(label);
			}
		}
		
		gridPanel = panelCenter;
	}

	protected void displayGrid(char[][] grid) {
		// TODO Auto-generated method stub
		gridPanel.removeAll();
		gridPanel.setLayout(new GridLayout(grid.length, grid[0].length, 0, 0));
		for (int i=0;i<grid.length;i++) {
			for (int j=0;j<grid[0].length;j++) {
				char c = grid[i][j];
				JLabel label = new JLabel(""+c);
				label.setBorder(BorderFactory.createLineBorder(Color.black));
				label.setOpaque(true);
				label.setHorizontalAlignment(SwingConstants.CENTER);
				if (c==maze.block) {
					label.setBackground(Color.darkGray);
					label.setForeground(Color.white);
				} else if (c==maze.open) {
					label.setBackground(Color.yellow);
					label.setForeground(Color.black);
				} else if (c==maze.startChar) {
					label.setBackground(Color.lightGray);
					label.setForeground(Color.black);
				} else if (c == maze.exitChar) {
					label.setBackground(Color.green);
					label.setForeground(Color.black);
				}
				gridPanel.add(label);
			}
		}
		
		gridPanel.revalidate();
	}
}
