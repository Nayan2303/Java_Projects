package org.game;

import java.awt.Graphics;

import javax.swing.JButton;

public class Game_button extends JButton
{
	private boolean ismine;
	int mines_next_to=0;
	boolean hidden;
	public Game_button()
	{
		// TODO Auto-generated constructor stub
		ismine=false;
		hidden=true;
	}
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		if(hidden) {
			return;
		}
		if(ismine) {
			this.setText("M");
		}
		else {
			this.setText(" "+this.mines_next_to);
		}
	}
	public boolean get_mine() {
		return ismine;
	}
	public void arm_mine() {
		 ismine=true;
	}

}
