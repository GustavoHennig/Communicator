package udp;

import java.io.IOException;
import java.net.*;
import java.util.Hashtable;

import data.User;
import geral.*;

/**
 * <p>Title: UdpListener</p>
 *
 * <p>Description: Fica esperando pacotes udp de outros clientes</p>
 *
 * <p>Copyright: Copyright (c) 2007</p>
 *
 * <p>Company: </p>
 *
 * @author Gustavo Augusto Hennig
 * @version 1.0
 */
public class UdpListener extends BaseThread {

    public static final int BufferSize = 128;


    public UdpListener() {
        UdpListener(Principal.ListenPort1);
    }

    private MulticastSocket socket = null;
    private MessageParser mp = new MessageParser();


    private void UdpListener(int port) {
        try {
            Principal.CurrentUdpPort = port;
            socket = new MulticastSocket(port);
            InetAddress address = InetAddress.getByName("224.0.0.1");
            socket.joinGroup(address);

        } catch (SocketException ex) {
            if (port != Principal.ListenPort2) {
                UdpListener(Principal.ListenPort2);
            } else {
                ex.printStackTrace();
                isRunning = false;
            }
        } catch (IOException ex) {
            if (port != Principal.ListenPort2) {
                UdpListener(Principal.ListenPort2);
            } else {
                ex.printStackTrace();
                isRunning = false;
            }

        }

    }

    public void run() {

        while (isRunning) {

            try {
                byte[] buf = new byte[BufferSize];

                // receive request
                DatagramPacket packet = new DatagramPacket(buf, buf.length);
                socket.receive(packet);

                if (packet.getLength() > 0) {

                    //Cada pacote recebido é interpretado e atualiza a lista de usuários do programa

                    Hashtable<String, String>
                            ret = mp.getDataFromUdp(new String(packet.getData()));
                    String nome = ret.get("nome");

                    if (!nome.equals("")) {


                        User user = new User();
                        user.Nome = nome;
                        user.Ip = packet.getAddress().getHostAddress();
                        user.ConectedTcpPort = Integer.valueOf(ret.get(
                                "portatcp"));
                        user.UdpPort = Integer.valueOf(ret.get("portaudp"));
                        user.InstanceId = ret.get("instanceid");

                        if (Principal.Users.containsKey(user.Key())) {
                            //Já existe
                            user = (User) Principal.Users.get(user.Key());
                            user.Nome = nome;
                            user.UpdateVisible();
                        } else {
                            //Não existe
                            user.Index = Principal.Users.size();
                            Principal.Users.put(user.Key(), user);
                            user.UpdateVisible();
                        }

//                        String dString = "Oi, sou um servidor UDP";
                        // buf = dString.getBytes();
                        // send the response to the client at "address" and "port"

                        /*
                          InetAddress address = packet.getAddress();

                         System.out.println("Legal, encontrei um mané! " + ret +
                         packet.getAddress().getHostAddress());
                         */
                        // Não ha necessidade de responder, apenas quero saber quem pode estar online
                        /*
                                                 int port = packet.getPort();
                         packet = new DatagramPacket(buf, buf.length, address,
                                port);
                         String teste2 = new String(packet.getData());
                                                 socket.send(packet);
                         */
                    }
                }

                this.sleep(100);

            } catch (Exception e) {
                e.printStackTrace();
                isRunning = false;
            }

        }
        if (socket.isConnected()) {
            try {
                socket.close();
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }

        }

    }


}
