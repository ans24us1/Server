/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cloudletserver;
import java.awt.image.BufferedImage;
import java.io.DataInputStream;
import java.io.File;
import java.io.IOException;

import java.io.InputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.imageio.ImageIO;

/**
 *
 * @author iitr
 */
public class serverRecieve {
    private BufferedImage tmp;
    public void recieve(Socket connection,String appendIPAddress) throws IOException, InterruptedException, UnknownHostException, InvalidKeyException, InvalidAlgorithmParameterException, NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException, ClassNotFoundException
    {
            DataInputStream in = new DataInputStream(connection.getInputStream());
            System.out.println("server Recive"+appendIPAddress);
            tmp = ImageIO.read(in);
            //String filename = appendIPAddress + ".jpg.txt";
            //System.out.println(filename);
            File tmpImg=new File("/home/iitr/Result/" + appendIPAddress+".jpg");
            String filepath=appendIPAddress+".jpg";
            ImageIO.write(tmp, "png",tmpImg );
            System.out.println("Image recieved successfully");
            //// String cmd = "/home/iitr/Desktop/Project/tesseract.sh /home/iitr/" + filename;
            //String cmd = "/bin/tesseract.sh /home/iitr/Result/" + appendIPAddress+".jpg";
            //ProcessBuilder pb = new ProcessBuilder("bash", "-c", cmd);
            //pb.redirectErrorStream(true);
            //Process shell = pb.start();
            //InputStream shellIn = shell.getInputStream();
            //int shellExitStatus = shell.waitFor();
            //int c;
            //while ((c = shellIn.read()) != -1) {
            //    System.out.write(c);
           // }
            //tmpImg.delete();
            //try {
            //    shellIn.close();
            //} catch (IOException ignoreMe) {
           // }
            //tmpImg.delete();
            VirtualCommunication imgsend=new VirtualCommunication();
            imgsend.mainImageSend(filepath);
    }
    
}
