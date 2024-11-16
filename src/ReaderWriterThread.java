import java.util.HashMap;

public class ReaderWriterThread implements Runnable{
    NetworkConnection nc;
    String username;
    HashMap<String,Information>clientlist;

    ReaderWriterThread(NetworkConnection nc, String username, HashMap<String,Information>cl){
        this.nc=nc;
        this.username=username;
        clientlist=cl;
        Thread t=new Thread(this);
        t.start();
    }

    @Override
    public void run() {

        while (true)
        {
            String msg=(String)nc.read();
            System.out.println(msg);
            String word[]=msg.split("#"); //divided into sender ,receiver and actual message
            /*
            word[0]=sender
            word[1]=receiver
            word[2]=keyword
            word[3]=message/null
            */

            if(word[2].equals("list"))
            {
                StringBuilder msgToSend = new StringBuilder("List of Clients...\n");
                for (String clientName : clientlist.keySet()) {
//                    System.out.println(clientName);
                    msgToSend.append(clientName).append("\n");
                }

                nc.write(msgToSend.toString());
            }
            Information info=clientlist.get(word[1]);
            if(word[2].toLowerCase().equals("send"))
            {
               info.nc.write(word[1]+"Says :"+word[3]);
            }




        }
    }
}
