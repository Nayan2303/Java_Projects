import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class client {
Socket s;
JFrame frame;
JPanel panel;
BufferedReader buffr;
BufferedWriter buffwr;
String username;
    public client(String st) {
        try {
            s=new Socket("localhost",9999);
            frame=new JFrame();
            panel=new JPanel();
            frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            frame.setPreferredSize(new Dimension(100,100));
            panel.setLayout(new FlowLayout());
            frame.add(panel);
            frame.setVisible(true);
            buffwr=new BufferedWriter(new OutputStreamWriter(s.getOutputStream()));
            buffr=new BufferedReader(new InputStreamReader(s.getInputStream()));
            username=st;
        } catch (IOException e) {
            System.out.println("Failed to create client");
        }
    }
    public void send(){
        String message;
        try{
            buffwr.write(username);
            buffwr.newLine();
            buffwr.flush();

             Scanner sc=new Scanner(System.in);
             while(s.isConnected()){
                 message=sc.nextLine();
                 buffwr.write(username +": "+message);
                 buffwr.newLine();
                 buffwr.flush();
                 panel.add(new JLabel(username +": "+message));
                 frame.pack();
                 frame.repaint();

            }
        }
        catch (Exception e){
            return;
        }
    }
    public void getmessages(){
        new Thread(new Runnable() {
            @Override
            public void run() {
              String messages;
              while(s.isConnected()){
                  try{
                    messages=buffr.readLine();

                    System.out.println(messages);
                  }catch(IOException e){
                      return;
                  }
                  panel.add(new JLabel(messages));
                  frame.pack();
                  frame.repaint();
              }
            }
        }).start();
    }

    public static void main(String [] args){
        Scanner sc=new Scanner(System.in);
        String username=sc.nextLine();
        client cli=new client(username);
        cli.getmessages();
        cli.send();
    }
}
