import java.io.IOException;
import java.io.ObjectInputStream;

public class ReadThreader implements Runnable {
    String name;
    ObjectInputStream ois;
    Thread t;
    ReadThreader(ObjectInputStream ois,String name)
    {
        this.ois=ois;
        this.name=name;
        t=new Thread(this);
        t.start();
    }

    @Override
    public void run() {

        while (true)
        {

            try {
                Object msg=ois.readObject();
                System.out.println(name+" Got: "+(String)msg);
            } catch (IOException e) {
                throw new RuntimeException(e);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }

    }
}
