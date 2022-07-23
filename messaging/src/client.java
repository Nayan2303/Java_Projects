import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class client {
Socket s;
BufferedReader buffr;
BufferedWriter buffwr;
String username;
    public client(String st) {
        try {
            s=new Socket("localhost",9999);
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
