import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class Client {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        System.out.println("Client Started");
        Socket socket=new Socket("127.0.0.1",5000);
        System.out.println("Client connect on the vpn server");
        ObjectOutputStream oos=new ObjectOutputStream(socket.getOutputStream());
        ObjectInputStream ois=new ObjectInputStream(socket.getInputStream());

        new WriteThreader(oos,"Client 1");
        new ReadThreader(ois,"Client 1");
//        input from user
//        while (true) {
//            Scanner sc = new Scanner(System.in);
//            System.out.println("Enter a message");
//            String msg = sc.nextLine();
//            if (msg.equals("exit")) break;
////          send to server msg;
//            oos.writeObject(msg);
//            /* recieved  from server*/
//            Object serverMsg = ois.readObject();
//            System.out.println("Message from server:" + serverMsg);
//        }
//        socket.close();


    }
}
