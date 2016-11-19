/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cloudletserver;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

/**
 *
 * @author iitr
 */
public class serverSend {
    public void send(Socket connection,String appendIPAddress) throws FileNotFoundException, IOException
    {
              System.out.println("Result send to client");
            String fileName=appendIPAddress+".jpg.txt";
            File transferFile = new File("/home/iitr/Result/" + fileName);
            int length = (int) transferFile.length();
            System.out.println("------------\n" + length + "\n---------------");
            byte[] bytearray = new byte[(int) transferFile.length()];
            System.out.println("-->" + bytearray);
            FileInputStream fin = new FileInputStream(transferFile);
            BufferedInputStream bin = new BufferedInputStream(fin);
            DataInputStream dis=new DataInputStream(bin);
            //bin.read(bytearray, 0, bytearray.length);
            dis.read(bytearray, 0, bytearray.length);
            OutputStream os = connection.getOutputStream();
            DataOutputStream dos=new DataOutputStream(os);
            dos.writeUTF(fileName);
            System.out.println("------------\n" + bytearray.length + "\n---------------");
            dos.writeLong(bytearray.length);
            dos.write(bytearray,0,bytearray.length);
            //dos.flush();
            //os.flush();.
           // connection.shutdownInput();
            connection.shutdownOutput();
           
            os.close();
            dis.close();
            bin.close();
             String Temp=new String(bytearray);
            System.out.println("Result Is" + Temp);
            transferFile.delete();
            connection.close();
            System.out.println("File transfer complete");
            
    
 }
    
}
