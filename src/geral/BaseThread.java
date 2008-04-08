package geral;

/**
 * <p>
 * Title: Base Thread</p>
 *
 * <p>
 * Description: Todas as threads desse programa herdam dessa classe</p>
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
public class BaseThread extends Thread {

    protected boolean isRunning = false;

    public void StartServer() {
        isRunning = true;
        this.start();
    }

    public boolean isRunning() {
        return isRunning;
    }

    public void StopServer() {
        isRunning = false;
    }

}
