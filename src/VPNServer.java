import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class VPNServer {
    public static void main(String[] args) throws IOException{
        ServerSocket serverSocket=new ServerSocket(5000);
        System.out.println("vpn Server Starting....");
        while (true) {
            Socket socket = serverSocket.accept();
            System.out.println("Client connected");
            new ServerThread(socket);

        }
    }
}
class ServerThread implements Runnable{

    Socket clientSocket;
    Thread t;


    ServerThread(Socket clientSocket)
    {
       this.clientSocket=clientSocket;
       t=new Thread(this);
       t.start();
    }
    @Override
    public void run() {


        try {
            ObjectInputStream ois = new ObjectInputStream(clientSocket.getInputStream());
            ObjectOutputStream oos = new ObjectOutputStream(clientSocket.getOutputStream());
            while (true) {
                Object cmsg = ois.readObject();
                if (cmsg==null) break;
                String clientmsg = (String) cmsg;
                System.out.println("Received from client:" + clientmsg);

                String serverMsg = clientmsg.toUpperCase();
                oos.writeObject(serverMsg);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }


    }
}
