import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

public class Client_handle implements Runnable {
    public static ArrayList<Client_handle> handles=new ArrayList<>();
Socket socket;
BufferedWriter bufw;
BufferedReader bufr;
String user;
public Client_handle(Socket sock){
    try {
        this.socket = sock;
       bufw=new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        bufr=new BufferedReader(new InputStreamReader(socket.getInputStream()));
        user=bufr.readLine();
        handles.add(this);

    }catch (IOException e){

    }
    send(user +" has joined the chat");
}

@Override
    public void run(){
    while(socket.isConnected()){
        try{
            String message=bufr.readLine();
            send(message);
        }catch (IOException e){
            close_handle(bufr,bufw,socket);
        }
    }
}
public void send(String s){
    for(Client_handle c: handles){
        try{
            if(!c.user.equals(this.user)){
                c.bufw.write(s);
                c.bufw.newLine();
                c.bufw.flush();

            }
        }catch(IOException e){
        close_handle(bufr,bufw,socket);
        }
    }
}
public void close_handle(BufferedReader br, BufferedWriter bw, Socket s){

    handles.remove(this);
    try{
        if(br!=null){
            br.close();
        }
        if(bw!=null){
            bw.close();
        }
        if(s!=null){
            s.close();
        }

    }catch (IOException I){
        I.printStackTrace();
    }
}


}
