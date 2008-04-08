package geral;

import java.util.Hashtable;

/**
 * <p>Title: MessageParser</p>
 *
 * <p>Description: Monta e desmonta as mensagens recebida via socket.</p>
 *
 * <p>Copyright: Copyright (c) 2007</p>
 *
 * <p>Company: </p>
 *
 * @author not attributable
 * @version 1.0
 */
public class MessageParser {
    public MessageParser() {
    }

    public final String fIni = "<!#ini_>";
    public final String fSep = "<!#meio_>";
    public final String fFin = "<!#fin_>";

    void RecString(String rec) {

        //rec.indexOf(fIni)

        String[] vet = rec.split(fFin);

        for (String s : vet) {
            String[] msg = rec.split(fSep);
            //msg[0] User
            //Nome


        }

    }

    public String getStringMsg(String mensagem) {
        String msg = "";

        msg += this.fIni;
        msg += mensagem;
        msg += this.fSep;
        msg += Principal.MyName;
        msg += this.fFin;

        return msg;
    }

    public Hashtable<String, String> getDataFromUdp(String sData) {

        sData = sData.replaceAll("\0", "");

        int inicio = sData.indexOf(fIni);
        int fim = sData.indexOf(fFin);

        String sub = sData.substring(inicio + fIni.length(), fim);
        String[] vet = sub.split(fSep);

        Hashtable<String, String> ret = new Hashtable<String, String>();

        ret.put("nome", vet[0]);
        ret.put("portatcp", vet[1]);
        ret.put("portaudp", vet[2]);
        ret.put("instanceid",vet[3]);

        return ret;
    }

    public String getStringToSendUdp() {
        String msg = "";
        msg += this.fIni;
        msg += Principal.MyName;
        msg += this.fSep;
        msg += Principal.CurrentTcpPort;
        msg += this.fSep;
        msg += Principal.CurrentUdpPort;
        msg += this.fSep;
        msg += Principal.Id;
        msg += this.fFin;

        return msg;
    }

    /**
     * getDataFromTcp
     *
     * @param string String
     */
    public Hashtable<String, String> getDataFromTcp(String sData) {

        sData = sData.replaceAll("\0", "");

        int inicio = sData.indexOf(fIni);
        int fim = sData.indexOf(fFin);

        String sub = sData.substring(inicio + fIni.length(), fim);
        String[] vet = sub.split(fSep);

        Hashtable<String, String> ret = new Hashtable<String, String>();

        ret.put("msg", vet[0]);
        ret.put("sender", vet[1]);

        return ret;

    }

}
