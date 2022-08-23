package org.game;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;
import java.util.Scanner;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class Player implements ActionListener
{
	JFrame frame;
	JPanel pannel;
	Game_button[][]grid;
	int grid_size;
	public Player(int size)
	{
		// TODO Auto-generated constructor stub
		grid_size=size;
		grid=new Game_button[grid_size][grid_size];
	}
	public void create_gui() {
		frame=new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		pannel=new JPanel();
		pannel.setLayout(new GridLayout(grid_size,grid_size));
		frame.add(pannel);
		for(int i=0;i<grid_size;i++) {
			for(int j=0;j<grid_size;j++) {
				grid[i][j]=new Game_button();
				grid[i][j].addActionListener(this);
				grid[i][j].setPreferredSize(new Dimension(50,50));
				pannel.add(grid[i][j]);
			}
		}
		set_mines();
		
		frame.pack();
		frame.repaint();
		frame.setVisible(true);
		
	}
	public void actionPerformed(ActionEvent e)
	{
		Game_button g=(Game_button)e.getSource();
		g.hidden=false;
		g.setEnabled(false);
		if(g.get_mine()) {
			JFrame Game_over = new JFrame();
			JOptionPane.showMessageDialog(Game_over, "You pressed on a mine!!");
			reset_grid();
			set_mines();
			frame.repaint();
		}
		
	}
	public void set_grid_numbers() {
		for(int i=0;i<grid_size;i++) {
			for(int j=0;j<grid_size;j++) {
////				grid[i][j].hidden=true;
//				grid[i][j].mines_next_to=0;;
				if(grid[i][j].get_mine()) {
					found_mine(i,j);
				}
			}
		}
	}
	public void found_mine(int x,int y) {
		
		for(int x_off=-1;x_off<2;x_off++) {
			for(int y_off=-1;y_off<2;y_off++) {
				if(x_off==0 &&y_off==0) {
					continue;
				}
				if (x + x_off < 0 || x + x_off > grid_size-1 || y + y_off < 0
						|| y + y_off > grid_size-1)
				{
					continue;
				}
				else {
					grid[x+x_off][y+y_off].mines_next_to++;
				}
			}
		}
	}
	public void set_mines() {
		 Random rand = new Random();
		    int num_mines = rand.nextInt(12);int randx = rand.nextInt(grid_size);int randy = rand.nextInt(grid_size);int count = 0;
		    while(num_mines==0) {
		    	num_mines=rand.nextInt();
		    }
		    System.out.println(num_mines);
		    while(count<num_mines) {
		    	
		    	if(grid[randx][randy].get_mine()==false) {
		    		grid[randx][randy].toggle_mine();
		    		System.out.println(randx+","+randy);
		    		count++;
		    		randx = rand.nextInt(grid_size);
		    		randy = rand.nextInt(grid_size);
		    		
		    	}else {
//		    		grid[randx][randy].arm_mine();
		    		randx = rand.nextInt(grid_size);
		    		randy = rand.nextInt(grid_size);
		    	}
		    }
		    set_grid_numbers();
		    
	}
	public void reset_grid() {
		for(int i=0;i<grid_size;i++) {
			for(int j=0;j<grid_size;j++) {
				grid[i][j].hidden=true;
				grid[i][j].mines_next_to=0;
				grid[i][j].setEnabled(true);
				grid[i][j].ismine=false;
			}
		}
	}
	public static void main(String[] args)
	{
		// TODO Auto-generated method stub
		
		new Player(7).create_gui();

	}

}
