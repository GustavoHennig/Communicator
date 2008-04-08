package tcp;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import geral.BaseThread;
import geral.Principal;/**

 * <p>Title: TcpServerThread</p>
 *
 * <p>Description: Essa classe fica rondando enconto o programa esrtiver rodando.<br>
 * É responsável por receber requisições tcp e adicionar cada requisição em uma thread separada.</p>
 *
 * <p>Copyright: Copyright (c) 2007</p>
 *
 * <p>Company: </p>
 *
 * @author Gustavo Augusto Hennig
 * @version 1.0
 */
public class TcpServerThread extends BaseThread {


    //Esse servidor pode conter diversos clientes
    public ArrayList<TcpConnection> connections = new ArrayList<TcpConnection>();


    public TcpServerThread() {
    }

    public void run() {

        ServerSocket ss = getServer(Principal.TcpPort1);

        while (isRunning) {

            try {
                //Libera o processador
                Thread.sleep(1);

                //Aguarda uma nova conexão de um cliente
                Socket s = ss.accept();

                /*
                 Criar uma nova conexão
                 Inicia uma thread com essa conexão
                 Adiciona em uma lista de conexões existentes
                 E libera a thread atual para recever novas conexões
                */
                TcpConnection con = new TcpConnection();
                con.sckConn = s;
                con.StartServer();
                connections.add(con);
                con = null;

            } catch (IOException ex1) {
                ex1.printStackTrace();
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }

            try {
                for (TcpConnection c : this.connections) {
                    if (!c.isRunning()) {
                        connections.remove(c);
                        break;
                    }
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }

        }
    }


    private ServerSocket getServer(int port) {

        try {
            //Tenta utilizar a porta padrão
            Principal.CurrentTcpPort = port;
            return new ServerSocket(port);
        } catch (Exception e) {
            //Em caso de erro ele tenta novamente com uma segunda opção de porta
            if (port != Principal.TcpPort2) {

                return getServer(Principal.TcpPort2);
            }
            e.printStackTrace();
            return null;
        }
    }

}
