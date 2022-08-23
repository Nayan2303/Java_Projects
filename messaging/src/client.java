import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class client {
Socket s;
    JTextArea jt;
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
            username=st;
            panel=new JPanel();
            jt=new JTextArea();
            frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
            frame.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosing(WindowEvent e) {
                    frame_close();
                }
            });
            jt.addKeyListener(new KeyListener() {
                @Override
                public void keyTyped(KeyEvent e) {

                }

                @Override
                public void keyPressed(KeyEvent e) {
                    if(e.getKeyCode() == KeyEvent.VK_ENTER){
                        e.consume();
                        System.out.println("Enter pressed");
                        send();
                    }
                }

                @Override
                public void keyReleased(KeyEvent e) {

                }
            });
            frame.setTitle(username);
            frame.setPreferredSize(new Dimension(400,400));
            frame.setLayout(new BorderLayout());
            panel.setPreferredSize(new Dimension(350,350));
            panel.setLayout(new BoxLayout(panel,BoxLayout.Y_AXIS));
            frame.add(panel);
            frame.add(BorderLayout.SOUTH,jt);
            frame.pack();
            frame.setVisible(true);
            buffwr=new BufferedWriter(new OutputStreamWriter(s.getOutputStream()));
            buffr=new BufferedReader(new InputStreamReader(s.getInputStream()));


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


//             Scanner sc=new Scanner((Readable) jt);
//
//             if(!sc.hasNextLine()){
//                 return;
//             }
//                while(running) {
                    message=jt.getText();
                    if(message.equals("")) return;
                    buffwr.write(username + ": " + message);
                    buffwr.newLine();
                    buffwr.flush();
                    panel.add(new JLabel(username + ": " + message));
                    frame.pack();
                    frame.repaint();
            jt.selectAll();
            jt.replaceSelection("");
            jt.setCaretPosition(jt.getSelectionStart());
                //}
        }
        catch (Exception e){
            e.printStackTrace();
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
                    panel.add(new JLabel(messages));
                    frame.pack();
                    frame.repaint();

                    System.out.println(messages);
                }catch(IOException e){
                    frame_close();
                }

            }

        }).start();
    }
    public void send_init(){
        try {
            buffwr.write(username);
            buffwr.newLine();
            buffwr.flush();
        } catch (IOException e) {
            frame_close();
        }

    }

    public static void main(String [] args){
        Scanner sc=new Scanner(System.in);
        String username=sc.nextLine();
        client cli=new client(username);
        cli.getmessages();
        cli.send_init();
    }
}
