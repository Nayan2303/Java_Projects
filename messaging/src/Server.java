import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;


public class Server {

    ServerSocket serv_sock;
    public Server() {
        try {
             serv_sock=new ServerSocket(9999);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    public static void main(String [] args) throws IOException {
        Server serv=new Server();
        while(!serv.serv_sock.isClosed()){
            Socket s=serv.serv_sock.accept();
            System.out.println("new connection"+s.getOutputStream());
            Client_handle client=new Client_handle(s);
            Thread t=new Thread(client);
            t.start();




        }
    }
}
