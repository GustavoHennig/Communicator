package data;

import tcp.TcpConnection;

/**
 * <p>
 * Title: User</p>
 *
 * <p>
 * Description: Representa um usuário conectado</p>
 *
 * <p>
 * Copyright: Copyright (c) 2007</p>
 *
 * <p>
 * Company: </p>
 *
 * @author Gustavo Augusto Hennig
 * @version 1.0
 */
public class User {

    public User() {

    }

    public String Ip;
    public String Nome;
    public long LastTimeViewed;
    public int Index;
    public boolean isConnected;
    public TcpConnection connection = new TcpConnection();
    public int UdpPort;
    public int ConectedTcpPort;
    public boolean idServer;
    public boolean Rejeitado;
    public String InstanceId;

    public void UpdateVisible() {
        LastTimeViewed = System.currentTimeMillis();
    }

    public int hashCode() {
        return Key().hashCode();
    }

    public String Key() {
        return Ip + UdpPort + ConectedTcpPort;// + InstanceId;
    }

    public boolean isOnLine() {
        //É considerado usuário online aquele que respondeu nos últimos 10 segundos.
        return ((System.currentTimeMillis() - LastTimeViewed) < 10000);
    }

    public String toString() {
        String ret = Nome;

        if (!isOnLine()) {
            ret += " (offline)";
        } else if (Rejeitado) {
            ret += " (bloqueado)";
        }

        return ret;
    }

    public boolean isMe() {
        return false;//Key().equals(Principal. )
    }

    public boolean SendMessage(String message) {

        return connection.sendMessage(this, message);
    }

}
