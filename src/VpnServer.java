import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;

public class VpnServer {
    public static void main(String[] args) throws IOException {
        ServerSocket socket=new ServerSocket(5000); //Create server socket
        System.out.println("Vpn server Started...");
        System.out.println(InetAddress.getLocalHost());
        HashMap<String,Information>clientlist=new HashMap<String,Information>();  //Store the Client name and Network connection

        while (true) {
            Socket sock=socket.accept();
            System.out.println("CLent connectd");
            NetworkConnection nc=new NetworkConnection(sock); //create network connection
            new Createconnection(clientlist,nc);

        }



    }
}
class Createconnection implements Runnable{
    NetworkConnection nc;
    HashMap<String,Information>clientlist;
    Createconnection(HashMap<String,Information>clist,NetworkConnection nc){
        this.nc=nc;
        clientlist=clist;
        Thread t=new Thread(this);
        t.start();
    }

    @Override
    public void run() {
        String username=(String) nc.read(); //received encrypted message
        AES aes=new AES(); //
        String decuname=aes.decrypt(username); //decrypted the Message
        System.out.println(decuname+" connected");
        clientlist.put(decuname,new Information(decuname,nc)); //Store the separate Client Information
        System.out.println("Hasmap: "+clientlist);
        new ReaderWriterThread(nc,decuname,clientlist);

    }
}
