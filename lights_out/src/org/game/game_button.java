//Made by Nayan Patel 2022
package org.game;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;

import javax.swing.JButton;

public class game_button extends JButton
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 7888845738016501073L;
	 boolean on;
	Point coord;
	public game_button(int x, int y,boolean state)
	{
		// TODO Auto-generated constructor stub
		on=state;
		coord=new Point(x,y);
		this.setPreferredSize((new Dimension(40, 40)));
	}
	protected void paintComponent(Graphics g)
	{
		
		super.paintComponent(g);
		if(on) {
			this.setBackground(Color.YELLOW);
		}
		else {
			this.setBackground(Color.BLACK);
		}
	}
	public void toggle() {
		on=!on;
	}

}
