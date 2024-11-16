import java.io.IOException;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) throws IOException {
        NetworkConnection nc=new NetworkConnection("127.0.0.1",5000);
        System.out.println("Connected vpn");
        Scanner sc=new Scanner(System.in);
        System.out.println("Enter a User_Name:");
        String uname=sc.nextLine();
        nc.write(uname);
        new writerThread(nc);
        new readerThread(nc);

//        while (true) {
//            System.out.println("Enter a message:");
//            String ms=sc.nextLine();
////            nc.write(s);
////            Object ms=nc.read();
////            System.out.println("Got:"+(String)ms);
//        }
        
       
       
        
    }
    
}
class writerThread implements Runnable{
    NetworkConnection nc;
    writerThread(NetworkConnection nc)
    {
        this.nc=nc;
        Thread t=new Thread(this);
        t.start();
    }

    @Override
    public void run() {
        Scanner sc=new Scanner(System.in);
    while (true)
    {
        System.out.println("Enter a message:");
        String msg=sc.nextLine();
        nc.write(msg);
        System.out.println("Message send...");

    }
    }
}
class readerThread implements Runnable{
    NetworkConnection nc;
    readerThread(NetworkConnection nc){
        this.nc=nc;
        Thread t=new Thread(this);
        t.start();
    }

    @Override
    public void run() {
    while (true)
    {
        String msg=(String) nc.read();
        System.out.println("Message Received:"+msg);
    }
    }
}
