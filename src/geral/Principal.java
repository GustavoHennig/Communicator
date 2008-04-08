package geral;

import java.io.*;
import java.util.Hashtable;

import data.User;
import gui.MainScreen;
import tcp.TcpServerThread;
import udp.UdpClient;
import udp.UdpListener;

/**
 * <p>Title: Principal</p>
 *
 * <p>Description: Classe quase estática, que guarda variáveis globais que possarm
 * ser acessadas de todas threads.</p>
 *
 * <p>Copyright: Copyright (c) 2007</p>
 *
 * <p>Company: </p>
 *
 * @author Gustavo Augusto Hennig
 * @version 1.0
 */
public class Principal {

    public static String MyName = "Seu nome";
    public static Hashtable<String, User> Users = new Hashtable<String, User>();
    public static int TempoParaDesconectar = (5 * 60000); //5 minutos sem uso a conexao eh encerrada

    public static int TcpPort1 = 24516;
    public static int TcpPort2 = 24517;
    public static int CurrentTcpPort;

    public static int ListenPort1 = 44445;
    public static int ListenPort2 = 44446;
    public static int CurrentUdpPort;

    public static String GlobalMsgText = "";

    public static String Id="";

    private TcpServerThread server = new TcpServerThread();

    public Principal() {
        Id = String.valueOf(System.currentTimeMillis());
        Id = Id.substring(Id.length()-5);

    }


    public void StartServer() {
        //Inicia o tcp listener
        server.StartServer();
        //Inicia o Udp listener
        UdpListener u = new UdpListener();
        u.StartServer();

        //Inicia os clientes Udp que enviam pacotes para duas portas diferentes
        UdpClient c1 = new UdpClient(ListenPort1);
        UdpClient c2 = new UdpClient(ListenPort2);
        c1.StartServer();
        c2.StartServer();


    }

    public static void main(String[] args) {

        Principal principal = new Principal();
        LoadSettings();

        principal.StartServer();

        MainScreen ms = new MainScreen();

        ms.setVisible(true);

    }

    public static void LoadSettings() {

        try {



            if(!new File("data.txt").exists()){
                return;
            }

            FileReader fr = new FileReader("data.txt");
            BufferedReader br = new BufferedReader(fr);

            String  nome = br.readLine();


            if(nome != null){
                Principal.MyName =nome;
            }

            /*
            while ((record = br.readLine()) != null) {
                recCount++;
                System.out.println(recCount + ": " + record);
            }
            */

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    public static void SaveSettings() {

         try {

             FileWriter fw = new FileWriter("data.txt");
             BufferedWriter bw = new BufferedWriter(fw);

             bw.write(Principal.MyName);


             bw.close();
             fw.close();

         } catch (IOException e) {
             e.printStackTrace();
         }

    }

}
