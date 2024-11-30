import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) throws IOException {
        Socket socket = new Socket();
        try {
            socket.connect(new InetSocketAddress("www.google.com", 80)); //find the actual ip address
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Client Started--- ");
        String ip=socket.getLocalAddress().getHostAddress();
        System.out.println(ip);
        NetworkConnection nc = new NetworkConnection(ip, 5000); //Connection to the server ip and port

        System.out.println("Connected to VPN");
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter a User Name:");
        String uname = sc.nextLine();
        AES aes=new AES(); //encrypt username and send the server
       String en=aes.encrypt(uname);
        nc.write(en);

        // Create and start threads
        Thread writer = new Thread(new writerThread(nc, uname));
        Thread reader = new Thread(new readerThread(nc));

        writer.start();
        reader.start();

        try {
            writer.join(); // Wait for writer thread to complete
            reader.join(); // Wait for reader thread to complete
        } catch (InterruptedException e) {
            System.err.println("Main thread interrupted: " + e.getMessage());
        }

        System.out.println("Client shutting down...");
    }
}

class writerThread implements Runnable {
    private final NetworkConnection nc;
    private final String uname;

    writerThread(NetworkConnection nc, String uname) {
        this.uname = uname;
        this.nc = nc;
    }

    @Override
    public void run() {
        Scanner sc = new Scanner(System.in);
        AES aes=new AES();
        while (true) {
            System.out.println("1. List");
            System.out.println("2. Message");
            System.out.println("Choose an option:");
            int option;
            try {
                option = sc.nextInt();
                sc.nextLine();
            } catch (Exception e) {
                System.out.println("Invalid input, please enter a number.");
                sc.nextLine();
                continue;
            }

            switch (option) {
                case 1:
                    String listRequest = uname + "#" + uname + "#list";

                    String newMsg= aes.encrypt(listRequest);
                    nc.write(newMsg);
                    break;
                case 2:
                    System.out.println("Enter User Name:");
                    String name = sc.nextLine();
                    System.out.println("Enter Message:");
                    String msg = sc.nextLine();

                    String sendMessage = uname + "#" + name + "#send#" + msg;
                    String enMsg= aes.encrypt(sendMessage);
                    nc.write(enMsg);
                    System.out.println("Message sent...");
                    break;
                default:
                    System.out.println("Invalid option, please choose 1 or 2.");
            }
        }
    }
}

class readerThread implements Runnable {
    private final NetworkConnection nc;

    readerThread(NetworkConnection nc) {
        this.nc = nc;
    }

    @Override
    public void run() {
        AES aes=new AES();
        while (true) {
            String enmsg = (String) nc.read();
            String msg=aes.decrypt(enmsg);
            if (msg != null && !msg.isEmpty()) {
                System.out.println("Message Received: " + msg);
            } else {
                System.out.println("No message received or connection lost.");
            }
        }
    }
}
