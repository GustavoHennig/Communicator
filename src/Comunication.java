/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2007</p>
 *
 * <p>Company: </p>
 *
 * @author not attributable
 * @version 1.0
 */

import java.net.Socket;
import java.net.ServerSocket;
import java.net.*;
import java.io.*;

public class Comunication {



    public Comunication() {
    }




    public void Conecta(){
        Socket s = null;
        try {
s= new Socket("127.0.0.1", 12345);
        } catch (UnknownHostException ex) {

        } catch (IOException ex) {

        }







    }


}
