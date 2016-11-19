/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cloudletserver;

import java.io.BufferedInputStream;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Writer;
import java.net.Socket;
import java.net.UnknownHostException;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

/**
 *
 * @author ubuntu
 */
public class VirtualCommunication {
     private Socket socket; 
    public void mainImageSend(String filename) throws UnknownHostException, IOException {
        // TODO Auto-generated method stub
         System.out.println("Virtual Image Function");
        long startTime = System.currentTimeMillis();
        socket = new Socket("192.168.122.132", 1234);  // server ip address and socket
        System.out.println("Socket Connected");
        File transferFile = new File("/home/iitr/Result/"+filename); // location of the file to be sent
        System.out.println(filename);
        byte[] bytearray = new byte[(int) transferFile.length()];
        FileInputStream fin = new FileInputStream(transferFile);
        BufferedInputStream bin = new BufferedInputStream(fin);
        bin.read(bytearray, 0, bytearray.length);
        System.out.println("bin read sucessfully");
        bin.close();
        DataOutputStream dos=new DataOutputStream(socket.getOutputStream());      
        System.out.println("Sending Files...");
        dos.writeUTF(filename);
        //dos.writeLong(bytearray.length);
        System.out.println("WriteUTF sucessfully");
        dos.write(bytearray, 0, bytearray.length);
        System.out.println("Write sucessfully");
        
        dos.flush();
        socket.shutdownOutput() ;
        System.out.println("Transfer File "+ transferFile);
        
        System.out.println("File transfer complete");
        
        textrecieve(); // calling the function that now behave as server and ready to recieve the converted text file
        long endTime = System.currentTimeMillis();
        long totalTime = endTime - startTime;
        System.out.println("File recieved successfully");
        System.out.println("Time taken : " + totalTime);
        socket.close();
        
        transferFile.delete();

    }

    @SuppressWarnings("null")
    public void textrecieve() throws IOException {
        System.out.println("Text recieve started");
      
     
        
         try {
            
                if (socket.isClosed()) {
                    System.out.println("here");
                }

                int bytesRead;
                InputStream in = socket.getInputStream();
                DataInputStream clientData = new DataInputStream(in);
                String fileName = clientData.readUTF();
                System.out.println("File Name Receive from VM"+fileName);
              
                OutputStream output = new FileOutputStream(fileName);
                 long size = clientData.readLong();
                byte[] buffer = new byte[1024];
                while (size > 0 && (bytesRead = clientData.read(buffer, 0, (int) Math.min(buffer.length, size))) != -1) {
                    output.write(buffer, 0, bytesRead);
                    size -= bytesRead;
                }
                String sttemp = new String(buffer);
                System.out.println(sttemp);
                
                File file = new File("/home/iitr/Result/"+fileName);
                Writer writer = null;
                writer = new BufferedWriter(new FileWriter(file));
                writer.write(sttemp);
                writer.flush();
                writer.close();
                output.close();
                socket.close();
                System.out.println("Result File "+ fileName);
        
            } catch (IOException e) {
                System.out.println("2");
                e.printStackTrace();
            }
    }
    
}
