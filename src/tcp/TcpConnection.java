package tcp;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Hashtable;

import data.User;
import geral.*;

/**
 * <p>Title: TcpConnection</p>
 *
 * <p>Description: Representa uma conexão com o servidor</p>
 *
 * <p>Copyright: Copyright (c) 2007</p>
 *
 * <p>Company: </p>
 *
 * @author Gustavo Augusto Hennig
 * @version 1.0
 */
public class TcpConnection extends BaseThread {

    public Socket sckConn;
    public String userName;
    private MessageParser mp = new MessageParser();

    public TcpConnection() {
    }


    public void run() {

        String data = "Toobie ornaught toobie";

        while (isRunning && isConnected()) {

            try {
                Hashtable<String, String>
                        ret = mp.getDataFromTcp(getRecievedData());
                String ss = ret.get("sender");
                boolean rej = false;

                for (User u : Principal.Users.values()) {
                    if (u.Nome.equals(ss) && u.Rejeitado) {
                        rej = true;
                        break;
                    }
                }
                if (!rej) {
                    Principal.GlobalMsgText += " << " + ret.get("sender") +
                            ":\n   " + ret.get("msg") + "\n";
                }
            } catch (Exception ex) {
                ex.printStackTrace();
                isRunning = false;
            }

        }
        if (isConnected()) {
            try {
                sckConn.close();
            } catch (IOException ex1) {
            }
        }
        isRunning = false;

    }

    public boolean VerifyConnection(User user) {

        if (!isConnected()) {
            try {
                sckConn = new Socket(user.Ip, user.ConectedTcpPort);
            } catch (UnknownHostException ex) {
                ex.printStackTrace();
            } catch (IOException ex) {
                ex.printStackTrace();
            }

        }

        return isConnected();

    }

    public String getRecievedData() {

        String buffer = "";

        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(
                    sckConn.
                    getInputStream()));

            while (!in.ready()) {
                //Para não atolar a CPU, liber para outros processos e threads do sistema operacional.
                Thread.sleep(1);
            }

            buffer = in.readLine();
            System.out.println(in.readLine()); // Read one line and output it

            System.out.print("'\n");
            in.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }

        return buffer;

    }

    public boolean SendData(String data) {
        try {
            PrintWriter out = new PrintWriter(sckConn.getOutputStream(), true);

            System.out.print("Sending string: '" + data + "'\n");

            out.print(mp.getStringMsg(data));
            out.close();
            return true;
        } catch (IOException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    public boolean isConnected() {
        if (sckConn == null) {
            return false;
        } else {
            return sckConn.isConnected() && !sckConn.isClosed();
        }
    }

    /**
     * sendMessage
     *
     * @param message String
     */
    public boolean sendMessage(User user, String message) {
        if (VerifyConnection(user)) {
            return SendData(message);

        } else {
            System.out.println("Erro ao enviar mensagem");
            return false;

        }
    }


}
