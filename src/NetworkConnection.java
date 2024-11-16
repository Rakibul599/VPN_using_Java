import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class NetworkConnection {
    Socket socket;
    ObjectInputStream ois;
    ObjectOutputStream oos;
    NetworkConnection(Socket socket) throws IOException
    {
        this.socket=socket;
        this.oos=new ObjectOutputStream(socket.getOutputStream());
        this.ois=new ObjectInputStream(socket.getInputStream());
    }
    NetworkConnection(String ip ,int port) throws IOException
    {
        socket=new Socket(ip,port);
        this.oos=new ObjectOutputStream(socket.getOutputStream());
        this.ois=new ObjectInputStream(socket.getInputStream());
    }
    void write(String obj)
    {

        try {
            oos.writeObject(obj);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            System.out.println("Failed to write");
        }
    } 
    public Object read() {
        try {
            return ois.readObject();
        } catch (ClassNotFoundException | IOException e) {
            e.printStackTrace();
            System.err.println("Failed to read object from the input stream.");
        }
        return null; 
    }
    
    
}
