package udp;

import java.io.IOException;
import java.net.*;

import geral.*;


/*
 * <p>Title: UdpClient</p>
 *
 * <p>Description: Envia pacotes Udp se identificando</p>
 *
 * <p>Copyright: Copyright (c) 2007</p>
 *
 * <p>Company: </p>
 *
 * @author Gustavo Augusto Hennig
 * @version 1.0
 */
public class UdpClient extends BaseThread {

    private int _port;

    public UdpClient(int port) {
        _port = port;
    }

    public void run() {

        while (isRunning) {
            try {
                byte[] buf = new byte[UdpListener.BufferSize];

                //A mensagem
                String dString = new MessageParser().getStringToSendUdp();

                buf = dString.getBytes();

//                DatagramSocket socket = null;
                MulticastSocket socket = null;

                socket = new MulticastSocket();

                InetAddress address = InetAddress.getByName("224.0.0.1");
                //SocketAddress sa = new InetSocketAddress(_port);
                socket.joinGroup(address);

                //Envia a Mensagem multicast
                DatagramPacket packet = new DatagramPacket(buf, buf.length,
                        address,
                        _port);

                //

                socket.send(packet);
                socket.close();

                this.sleep(3000); //Espera 3 segundos para não entupir a rede de OIs
            } catch (SocketException ex) {
              ex.printStackTrace();
            } catch (IOException ex) {
                ex.printStackTrace();
             } catch (InterruptedException ex) {
                ex.printStackTrace();
             }

        }
    }
}
