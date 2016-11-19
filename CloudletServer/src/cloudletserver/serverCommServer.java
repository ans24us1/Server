/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cloudletserver;

import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Writer;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author iitr
 */
public class serverCommServer implements Runnable{
    private Socket socket;
    private int ID;

    public serverCommServer(Socket sock,int i) {
        this.socket=sock;
        this.ID=i;
    }
    
    

    public static void main(String[] args) {
        int port = 7119;
         int count = 0;
        try {
            ServerSocket socket1 = new ServerSocket(port);
            System.out.println("MultipleSocketServer Initialized");
            while (true) {
                Socket socket = socket1.accept();
                Runnable runnable = new serverCommServer(socket, ++count);
                Thread thread = new Thread(runnable);
                thread.start();
            }
        } catch (Exception e) {
        }
    }
    
     @Override
    public void run() {
        InputStream in = null;
        try {
            int bytesRead;
            in = socket.getInputStream();
            DataInputStream clientData = new DataInputStream(in);
            String fileName = clientData.readUTF();
            OutputStream output = new FileOutputStream(fileName);
            long size = clientData.readLong();
            byte[] buffer = new byte[1024];
            while (size > 0 && (bytesRead = clientData.read(buffer, 0, (int) Math.min(buffer.length, size))) != -1) {
                output.write(buffer, 0, bytesRead);
                size -= bytesRead;
            }
            String sttemp = new String(buffer);
            File file = new File("/home/iitr/Result" + fileName);
            Writer writer = null;
            writer = new BufferedWriter(new FileWriter(file));
            writer.write(sttemp);
            writer.flush();
            writer.close();
            output.close();
        } catch (IOException ex) {
            Logger.getLogger(serverCommServer.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                in.close();
            } catch (IOException ex) {
                Logger.getLogger(serverCommServer.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
        

                

           

   
}
