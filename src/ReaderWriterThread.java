import java.util.HashMap;
//this class use for server
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
        AES aes=new AES();

        while (true)
        {

            String enmsg=(String)nc.read(); //Received encrypted message
            String msg = aes.decrypt(enmsg); //Decrypt the message
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

                    msgToSend.append(clientName).append("\n");

                }
                msgToSend.append("Choose an option:\n");
                String List=msgToSend.toString();
                String enlist=aes.encrypt(List);
                nc.write(enlist);
            }
            Information info=clientlist.get(word[1]);
            if(word[2].toLowerCase().equals("send"))
            {
                String newmsg=(String) word[1]+" Says :"+word[3];
                String ennewmsg=aes.encrypt(newmsg);
               info.nc.write(ennewmsg);
            }




        }
    }
}
