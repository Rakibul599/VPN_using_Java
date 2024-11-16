import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;

public class VpnServer {
    public static void main(String[] args) throws IOException {
        ServerSocket socket=new ServerSocket(5000);
        System.out.println("Vpn server Started...");
        HashMap<String,Information>clientlist=new HashMap<String,Information>();

        while (true) {
            Socket sock=socket.accept();
            System.out.println("CLent connectd");
            NetworkConnection nc=new NetworkConnection(sock);
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
        String username=(String) nc.read();
        System.out.println(username+" connected");
        clientlist.put(username,new Information(username,nc));
        System.out.println("Hasmap: "+clientlist);
        new ReaderWriterThread(nc,username,clientlist);

    }
}
