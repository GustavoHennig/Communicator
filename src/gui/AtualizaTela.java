package gui;

import java.util.Observable;
import java.util.Observer;

/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2007</p>
 *
 * <p>Company: </p>
 *
 * @author Gustavo Augusto Hennig
 * @version 1.0
 */
public class AtualizaTela extends Observable implements Runnable {


    public AtualizaTela(Observer obs) {
        addObserver(obs);

    }

    public void run() {

        while (true) {

            try {
                Thread.sleep(1000);

                //Thread que aciona eventos para atualiza a tela
                notifyObservers(new ObjRefresh(EnuRefresh.erRefreshUser, ""));
                setChanged();


            } catch (InterruptedException ex) {

            }


        }

    }



}
