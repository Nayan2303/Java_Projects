//Made by Nayan Patel 2022
package org.game;

import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.PrintStream;
import java.util.Random;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class Player
  implements ActionListener
{
  JFrame playframe;
  JPanel playpan;
  game_button[][] lights = new game_button[5][5];
  int lights_on = 0;
  
  public void create_gui()
  {
    playframe = new JFrame();
    playframe.setDefaultCloseOperation(3);
    playpan = new JPanel();
    playpan.setLayout(new GridLayout(5, 5));
    for (int i = 0; i < 5; i++) {
      for (int j = 0; j < 5; j++)
      {
        lights[i][j] = new game_button(i, j, false);
        lights[i][j].addActionListener(this);
        playpan.add(lights[i][j]);
      }
    }
    starting_lights();
    playframe.add(playpan);
    playframe.pack();
    playframe.repaint();
    playframe.setVisible(true);
  }
  
  public void actionPerformed(ActionEvent e)
  {
    game_button pressed = (game_button)e.getSource();
    change_score(pressed);
    System.out.println("pressed at" + pressed.coord.x + " " + pressed.coord.y);
    change_other_buttons(pressed.coord.x, pressed.coord.y);
    System.out.println("Number of lights on: " + lights_on);
    playpan.repaint();
  }
  
  public void change_other_buttons(int x, int y)
  {
    for (int x_off = -1; x_off < 2; x_off++) {
      for (int y_off = -1; y_off < 2; y_off++) {
        if ((x_off != 0) || (y_off != 0)) {
          if ((x + x_off >= 0) && (x + x_off <= 4) && (y + y_off >= 0) && 
            (y + y_off <= 4)) {
            if (((x_off != -1) || (y_off != -1)) && ((x_off != -1) || (y_off != 1)) && ((x_off != 1) || (y_off != -1)) && ((x_off != 1) || (y_off != 1))) {
              change_score(lights[(x + x_off)][(y + y_off)]);
            }
          }
        }
      }
    }
  }
  
  public void change_score(game_button button)
  {
    button.toggle();
    if (button.on) {
      lights_on += 1;
    } else {
      lights_on -= 1;
    }
    if (lights_on == 0)
    {
      JFrame win_frame = new JFrame();
      JOptionPane.showMessageDialog(win_frame, "You win!!");
      
      starting_lights();
    }
  }
  
  public void starting_lights()
  {
    Random rand = new Random();
    int num_lights_on = rand.nextInt(10);int randx = rand.nextInt(5);int randy = rand.nextInt(5);int count = 0;
    System.out.println(num_lights_on);
    while (num_lights_on == 0) {
      num_lights_on = rand.nextInt(10);
    }
    while (count < num_lights_on) {
      if (!lights[randx][randy].on)
      {
        change_score(lights[randx][randy]);
        count++;
        
        randx = rand.nextInt(5);
        randy = rand.nextInt(5);
      }
    }
    playframe.repaint();
  }
  
  public static void main(String[] args)
  {
    new Player().create_gui();
  }
}