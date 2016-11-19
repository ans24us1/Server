package cloudletserver;

import java.net.*;
import java.awt.image.BufferedImage;
import java.io.*;


public class MultipleRequest implements Runnable {

    private Socket connection;
    private String TimeStamp;
    private int ID;
    private byte[] mac;

    public static void main(String[] args) {
        int port = 8229;
        int count = 0;
        try {
            ServerSocket socket1 = new ServerSocket(port);
            System.out.println("MultipleSocketServer Initialized");
            while (true) {
                Socket connection = socket1.accept();
                Runnable runnable = new MultipleRequest(connection, ++count);
                Thread thread = new Thread(runnable);
                thread.start();
            }
        } catch (Exception e) {
        }
    }
    

    MultipleRequest(Socket s, int i) {
        this.connection = s;
        this.ID = i;
    }

    @Override
    public void run() {
        // TODO Auto-generated method stub
        System.out.println("MultipleSocketServer Initialized");
        BufferedImage tmp = new BufferedImage(400, 400, BufferedImage.TYPE_INT_RGB);
        String appendIPAddress = connection.getInetAddress().toString().substring(1);
        Boolean flag=false;
        try {
        String temp;
  
      
      InetAddress host = InetAddress.getLocalHost();
      String hostIPAddress= host.getHostAddress();
      System.out.println(hostIPAddress);
      /*  String fileName=appendIPAddress+".jpg.txt";
        System.out.println(fileName);
         File[] files = new File("/home/iitr/Result").listFiles();
            
            for (File file : files) {
                temp = file.getName();
                if (temp.equalsIgnoreCase(fileName)) {
                    serversend.send(connection,appendIPAddress);
                    flag=true;
                    break;
                }
            }
            if(flag==false)
            {*/
      
             serverRecieve serverrecive= new serverRecieve();
             serverrecive.recieve(connection, appendIPAddress);
             if(connection.isClosed())
              {
               //if(hostIPAddress.equalsIgnoreCase("192.168.111.186"))
                   connection=new Socket("192.168.111.174",7119);
               //else
                 //  connection=new Socket("192.168.111.186",8119);
              }
             serverSend serversend=new serverSend();
             serversend.send(connection, appendIPAddress);
         //   }
    //     

        } catch (Exception exp) {
             System.out.println("Catch");
        }
    }
}
