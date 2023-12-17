package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;

class Main_frame implements KeyListener {
	final Color FORREST_GREEN = new Color(34,139,34);
	final Color CRIMSON = new Color(220,20,60);
	final Color SILVER = new Color(192,192,192);
	ImageIcon game_over_icon;
	ImageIcon cover_icon;
	Timer gameUpdate;
	JFrame main;
	JPanel menu;
	JButton start;
	
	JPanel game;
	JPanel holder;
	
	JPanel gameOver;
	
	Board b;
	String direction;
	String prevDirection;
	
	
	public Main_frame() {
		Timer fps = new Timer(1, e->update_game_frame());
		gameUpdate = new Timer(60, e-> update_field());
		
		main = new JFrame("Snake");
		main.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		main.setLayout(new FlowLayout());
		
		
		menu = new JPanel();
		menu.setLayout(new BoxLayout(menu, BoxLayout.Y_AXIS));
		
		menu.setSize(new Dimension(500,500));
		
		JLabel title = new JLabel("Snake");
		title.setFont(new Font("Arial",Font.BOLD, 34));
		title.setForeground(FORREST_GREEN);
		title.setAlignmentX(JLabel.CENTER_ALIGNMENT);
		cover_icon = new ImageIcon(Main_frame.class.getResource("/res/images/Snake_cover.png"));
		
		b = new Board(50);
		direction = "r";
		prevDirection = "";
		holder = game_frame(b);
		holder.setVisible(false);
				
		start = new JButton("Start");
		start.setFont(new Font("arial",Font.PLAIN, 22));
		start.setForeground(CRIMSON);
		start.setAlignmentX(JButton.CENTER_ALIGNMENT);
		start.addActionListener(e-> {
			menu.setVisible(false);
			menu.setEnabled(false);
			fps.start();
			gameUpdate.start();
			holder.setVisible(true);
			main.pack();
			main.setLocationRelativeTo(null);
		});
		
		menu.add(Box.createVerticalStrut(10));
		menu.add(title);
		menu.add(Box.createVerticalStrut(10));
		JLabel imageHold = new JLabel(cover_icon);
		imageHold.setAlignmentX(JLabel.CENTER_ALIGNMENT);
		menu.add(imageHold);
		menu.add(Box.createVerticalStrut(50));
		menu.add(start);
		menu.add(Box.createHorizontalStrut(400));
		menu.add(Box.createVerticalStrut(10));
		
		gameOver = new JPanel();
		
		
		main.addKeyListener(this);
		main.setFocusable(true);
		main.requestFocusInWindow(); 
		main.add(menu);
		main.add(holder);
		main.pack();
		main.setLocationRelativeTo(null);
		main.setForeground(Color.BLACK);
		main.setVisible(true);		
			
	}

	public JPanel game_frame(Board b) {
		JPanel tmp_back = new JPanel();
		tmp_back.setLayout(new GridLayout(50,50));
		int[][] field = b.getBoard();
		
		for (int i = 0; i < field.length; i++) {
			for (int j = 0; j < field[i].length; j++) {
				JPanel point = new JPanel();
				switch(field[i][j]) {
					case 1:
						point.setBackground(FORREST_GREEN);
						break;
					case 0:
						point.setBackground(SILVER);
						break;
					case -1:
						point.setBackground(CRIMSON);
						break;
					}
				point.setPreferredSize(new Dimension(15,15));
				tmp_back.add(point);
			}
		}
		return tmp_back;
	}
	
	public void update_game_frame() {
		JPanel newPan = game_frame(b);
		main.remove(holder);
		holder = newPan;
		main.add(newPan);
		main.validate();
	}
	
	public void update_field() {
		if (!b.isGameOver()) {
			b.playFrame(direction);
		} else {
			gameUpdate.stop();
			gameOver = gameOverPanel();
			gameOver.setVisible(true);
			holder.setVisible(false);
			main.add(gameOver);
			main.pack();
			main.setLocationRelativeTo(null);
			
		}
		
			
	}
	
	public JPanel gameOverPanel() {
		JPanel pan = new JPanel();
		pan.setLayout(new BoxLayout(pan, BoxLayout.Y_AXIS));
		JLabel title = new JLabel();
		title.setText(String.format("Final Score: %d", b.getScore()));
		title.setFont(new Font("Arial", Font.PLAIN, 34));
		title.setAlignmentX(JPanel.CENTER_ALIGNMENT);
		
		JPanel bPanel = new JPanel();
		bPanel.setLayout(new GridLayout(1,2,20,30));
		JButton yes = new JButton("Play Again");
		yes.addActionListener(e-> {
			pan.setVisible(false);
			b = new Board(50);
			Timer fps = new Timer(1, f->update_game_frame());
			gameUpdate = new Timer(60, f-> update_field());
			holder.setVisible(true);
			main.pack();
			fps.start();
			gameUpdate.start();
			main.setLocationRelativeTo(null);
			
		});
		JButton no = new JButton("Exit");
		no.addActionListener(e-> main.dispose());
		
		game_over_icon = new ImageIcon(Main_frame.class.getResource("/res/images/game-over.png"));
		JLabel iconHold = new JLabel(game_over_icon);
		
		iconHold.setAlignmentX(JLabel.CENTER_ALIGNMENT);
		
		bPanel.add(yes);
		bPanel.add(no);
		
		pan.add(iconHold);
		pan.add(title);
		pan.add(Box.createVerticalStrut(10));
		pan.add(bPanel);
		pan.add(Box.createVerticalStrut(10));
		pan.add(Box.createHorizontalStrut(300));
		
		pan.setSize(new Dimension(250,250));
		
		return pan;
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if (prevDirection.isBlank()) {
			prevDirection = String.valueOf(e.getKeyChar());
		}
		switch(e.getKeyChar()) { 	
			case 'w':
				if (!prevDirection.equals("d")) {
					direction = "u";
					prevDirection = direction.toString();
				}
				break;
			case 's':
				if (!prevDirection.equals("u")) {
					
					direction = "d";
					prevDirection = direction.toString();
				}
				break;
			case 'a':
				if (!prevDirection.equals("r")) {
					
					direction = "l";
					prevDirection = direction.toString();
				}
				break;
			case 'd':
				if (!prevDirection.equals("l")) {
					
					direction = "r";
					prevDirection = direction.toString();
				}
				break;
			}
			b.playFrame(direction);
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
	
}