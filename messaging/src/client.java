import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
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
Boolean running=true;
    public client(String st) {
        try {
            s=new Socket("localhost",9999);
            frame=new JFrame();
            panel=new JPanel();
            frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
            frame.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosing(WindowEvent e) {
                    frame_close();
                }
            });

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
    public void frame_close(){

        running=false;
        System.out.println("Executed");
        frame.dispose();
        System.out.println("Disposed frame");
//        Thread.currentThread().interrupt();
        close_client(buffr,buffwr,s);

        System.out.println("Closed client");
        System.exit(0);
    }
    public void send(){
        String message;
        try{
            buffwr.write(username);
            buffwr.newLine();
            buffwr.flush();

             Scanner sc=new Scanner(System.in);
             while(running){
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
            frame_close();
        }
    }
    public void close_client(BufferedReader br, BufferedWriter bw, Socket s){

        try{
            if(s!=null){
                s.close();
            }
            if(br!=null){
                br.close();

            }
            if(bw!=null){
                bw.close();
            }


        }catch (IOException I){
            System.out.println("Couldn't close all of them");
        }
    }

    public void getmessages(){
        new Thread(() -> {
          String messages="";
          while(running){

              try{
                messages=buffr.readLine();

                System.out.println(messages);
              }catch(IOException e){
                  frame_close();
              }
              panel.add(new JLabel(messages));
              frame.pack();
              frame.repaint();
          }
          Thread.currentThread().interrupt();
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
