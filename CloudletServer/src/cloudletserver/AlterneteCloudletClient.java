/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cloudletserver;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author iitr
 */
public class AlterneteCloudletClient {
    private String filename;

    public AlterneteCloudletClient(String name) {
        this.filename=name;
    }
    
    public void waitingToSend(){
        try {
            int port = 6119;
            ServerSocket socket1 = new ServerSocket(port);
            System.out.println("Waiting for client.....");
            Socket socket=socket1.accept();
            serverSend serversend=new serverSend();
            serversend.send(socket, this.filename);
        } catch (IOException ex) {
            Logger.getLogger(AlterneteCloudletClient.class.getName()).log(Level.SEVERE, null, ex);
        }
     
    
    }
    
}
