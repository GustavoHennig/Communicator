package gui;


import java.awt.*;
import java.awt.List;
import java.awt.event.*;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

import data.User;
import geral.Principal;
import java.awt.Rectangle;


/**
 * <p>Title: Main Screen</p>
 *
 * <p>Description: Tela principal, todo chat fica aqui!</p>
 *
 * <p>Copyright: Copyright (c) 2007</p>
 *
 * <p>Company: </p>
 *
 * @author Gustavo Augusto Hennig
 * @version 1.0
 */
public class MainScreen extends Frame implements Observer {
    List lstUsuarios = new List();
    TextField txtSend = new TextField();
    Label label1 = new Label();
    TextArea txtMSGs = new TextArea();
    Choice cmbDest = new Choice();
    Label label2 = new Label();
    Label label3 = new Label();
    Label label4 = new Label();
    Button btnSend = new Button();
    Label lblStatus = new Label();

    Thread thAtu = new Thread(new AtualizaTela(this));
    Label label5 = new Label();
    TextField txtNome = new TextField();
    JPopupMenu popupMenu1 = new JPopupMenu();
    JMenuItem jMenuItem1 = new JMenuItem();
    Button btnSendAll = new Button();
    Label lblStatus2 = new Label();

    public MainScreen() {
        try {
            jbInit();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    private void jbInit() throws Exception {
        this.setLayout(null);
        //      getContentPane().setLayout(null);
        setBackground(new Color(212, 208, 200));
        this.setTitle("Neliware Communicator v.1.0");
        this.setResizable(false);

        lstUsuarios.setBounds(new Rectangle(293, 109, 174, 243));
        lstUsuarios.addMouseListener(new MainScreen_lstUsuarios_mouseAdapter(this));
        label1.setText("Lista de Usuários");
        label1.setBounds(new Rectangle(293, 78, 120, 22));
        cmbDest.setBounds(new Rectangle(293, 389, 174, 24));
        label2.setText("Mensagem para enviar");
        label2.setBounds(new Rectangle(18, 367, 191, 22));
        txtMSGs.setBounds(new Rectangle(18, 109, 266, 243));
        label3.setText("Destinatário");
        label3.setBounds(new Rectangle(293, 368, 94, 21));
        label4.setText("Nick");
        label4.setBounds(new Rectangle(18, 44, 29, 21));
        btnSend.setLabel("Enviar");
        btnSend.setBounds(new Rectangle(295, 428, 71, 27));
        btnSend.addActionListener(new MainScreen_btnSend_actionAdapter(this));

        //        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        label5.setText("Mensagens");
        label5.setBounds(new Rectangle(18, 76, 172, 22));
        txtNome.setText("Seu nome");
        txtNome.setBounds(new Rectangle(52, 42, 234, 24));
        txtNome.addTextListener(new MainScreen_txtNome_textAdapter(this));
        txtSend.setBounds(new Rectangle(18, 390, 265, 23));
        txtSend.addKeyListener(new MainScreen_txtSend_keyAdapter(this));
        lblStatus.setText("...");

        popupMenu1.setLabel("Opções do Usuário");
        jMenuItem1.setText("Bloquear/Desbloquear");
        jMenuItem1.addActionListener(new MainScreen_jMenuItem1_actionAdapter(this));
        btnSendAll.setLabel("Enviar Todos");
        btnSendAll.setBounds(new Rectangle(372, 428, 97, 27));
        btnSendAll.addActionListener(new MainScreen_btnSendAll_actionAdapter(this));
        lblStatus2.setText("...");
        lblStatus2.setBounds(new Rectangle(19, 437, 262, 22));
        this.add(txtNome, java.awt.BorderLayout.NORTH);
        this.add(lstUsuarios, null);
        this.add(label1);
        this.add(label3);
        this.add(cmbDest);
        this.add(btnSend);
        this.add(btnSendAll);
        this.add(lblStatus2);
        this.add(lblStatus);
        popupMenu1.add(jMenuItem1);
        initComponents();
        this.setSize(480, 471);
        CarregaDadosInicias();
        lblStatus.setBounds(new Rectangle(18, 421, 262, 22));
        this.add(txtMSGs);
        this.add(lblStatus2);
        this.add(lblStatus);
        this.add(txtSend, java.awt.BorderLayout.SOUTH);
        this.add(label2);
        this.add(label5);
        this.add(label4);
    }

    private void initComponents() {

        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                exitForm(evt);
            }
        });

        pack();
    }

    /** Exit the Application */
    private void exitForm(java.awt.event.WindowEvent evt) {
        Principal.SaveSettings();
        System.exit(0);
    }

    void CarregaDadosInicias() {
        //cmbDest.addItem("Enviar para todos usuários");
        //cmbDest.addItem("Ops.");
        //lstUsuarios.add("Gustavo");
        //lstUsuarios.add("MIM");
        txtNome.setText(Principal.MyName);
        thAtu.start();
    }

    public void update(Observable o, Object arg) {

        if (arg instanceof ObjRefresh) {
            ObjRefresh obj = (ObjRefresh) arg;

            switch (obj.action) {
            case erAddUser:
                lstUsuarios.add(obj.valor);
                break;
            case erAddMsg:
                txtMSGs.append("\n" + obj.valor);
                break;
            case erRefreshUser:
                UpdateUserList();
                break;
            }

        }

    }

    private void UpdateUserList() {

        try {
            //  lstUsuarios.setVisible(false);
            //  lstUsuarios.removeAll();

            for (User user : Principal.Users.values()) {
                if (lstUsuarios.getItemCount() > user.Index) {

                    if (!lstUsuarios.getItem(user.Index).equals(user.toString())) {
                        lstUsuarios.replaceItem(user.toString(), user.Index);
                        cmbDest.remove(user.Index);
                        cmbDest.insert(user.toString(), user.Index);
                    }

                } else {
                    lstUsuarios.add(user.toString(), user.Index);
                    cmbDest.insert(user.toString(), user.Index);
                }

            }

            if (!txtMSGs.getText().equals(Principal.GlobalMsgText)) {
                //O if é para evitar refreshs
                txtMSGs.setText(Principal.GlobalMsgText);
                txtMSGs.setSelectionStart(Principal.GlobalMsgText.length());
            }

            String status1 = "Porta Tcp: ";
            status1 += Principal.CurrentTcpPort ;
            String status2 = "Porta Udp: ";
            status2 += Principal.CurrentUdpPort ;
            //status += "Connections: "+ Tcpser

            if(!lblStatus.getText().equals(status1)){
                //O if é para evitar refreshs
                lblStatus.setText(status1);
            }
            if(!lblStatus2.getText().equals(status2)){
                //O if é para evitar refreshs
                lblStatus2.setText(status2);
            }


            //    lstUsuarios.setVisible(true);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            //  lstUsuarios.setVisible(true);
        }

    }


    public void txtNome_textValueChanged(TextEvent e) {
        Principal.MyName = txtNome.getText();
    }

    public void btnSend_actionPerformed(ActionEvent e) {
        try {

            if (txtSend.getText().equals("")) {
                return;
            }
            btnSend.setEnabled(false);
            txtSend.setEnabled(false);

            User user = getSelectedDestUser();

            if (user == null) {
                throw new Exception("Usuário não encontrado, erro interno.");
            }

            if (!user.SendMessage(txtSend.getText())) {
                Principal.GlobalMsgText +=
                        " Não foi possível enviar a mensagem:\n" +
                        txtSend.getText() + "\n";
            } else {
                Principal.GlobalMsgText += " >> " + Principal.MyName + ":\n   " +
                        txtSend.getText() + "\n";
                txtSend.setText("");
            }

            txtSend.setEnabled(true);
            btnSend.setEnabled(true);

        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            txtSend.setEnabled(true);
            btnSend.setEnabled(true);
        }
        txtSend.requestFocus();

    }

    private User getSelectedDestUser() {

//    User ret = null;
        for (User user : Principal.Users.values()) {

            if (user.Index == cmbDest.getSelectedIndex()) {
                return user;
            }

        }

        return null;

    }


    public void txtSend_keyPressed(KeyEvent e) {
        if (e.getKeyCode() == 10) {
            btnSend_actionPerformed(null);
        }
    }


    private User getSelectedUserLst() {

        for (User user : Principal.Users.values()) {

            if (user.Index == lstUsuarios.getSelectedIndex()) {
                return user;
            }
        }
        return null;

    }

    public void lstUsuarios_mouseClicked(MouseEvent e) {
        if (e.getButton() == 3) {
//        if (e.isPopupTrigger()) {

            popupMenu1.show(e.getComponent(), e.getX(), e.getY());
        }
    }


    public void jMenuItem1_actionPerformed(ActionEvent e) {
        User u = getSelectedUserLst();
        if (u != null) {
            u.Rejeitado = !u.Rejeitado;
        }
    }

    public void btnSendAll_actionPerformed(ActionEvent e) {

        try {

            if (txtSend.getText().equals("")) {
                return;
            }

            btnSend.setEnabled(false);
            txtSend.setEnabled(false);

            for (User user : Principal.Users.values()) {

                if (user.isOnLine()) {

                    if (!user.SendMessage(txtSend.getText())) {
                        Principal.GlobalMsgText +=
                                " Não foi possível enviar a mensagem para: " +
                                user.Nome + "\n";
                    }
                }

            }

            Principal.GlobalMsgText += " >> " + Principal.MyName + ":\n   " +
                    txtSend.getText() + "\n";
            txtSend.setText("");

            txtSend.setEnabled(true);
            btnSend.setEnabled(true);

        }

        catch (Exception ex) {
            System.out.println(ex.getMessage());
            txtSend.setEnabled(true);
            btnSend.setEnabled(true);
        }
    }


}


class MainScreen_btnSendAll_actionAdapter implements ActionListener {
    private MainScreen adaptee;
    MainScreen_btnSendAll_actionAdapter(MainScreen adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.btnSendAll_actionPerformed(e);
    }
}


class MainScreen_jMenuItem1_actionAdapter implements ActionListener {
    private MainScreen adaptee;
    MainScreen_jMenuItem1_actionAdapter(MainScreen adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.jMenuItem1_actionPerformed(e);
    }
}


class MainScreen_lstUsuarios_mouseAdapter extends MouseAdapter {
    private MainScreen adaptee;
    MainScreen_lstUsuarios_mouseAdapter(MainScreen adaptee) {
        this.adaptee = adaptee;
    }

    public void mouseClicked(MouseEvent e) {
        adaptee.lstUsuarios_mouseClicked(e);
    }
}


class MainScreen_txtSend_keyAdapter extends KeyAdapter {
    private MainScreen adaptee;
    MainScreen_txtSend_keyAdapter(MainScreen adaptee) {
        this.adaptee = adaptee;
    }

    public void keyPressed(KeyEvent e) {
        adaptee.txtSend_keyPressed(e);
    }
}


class MainScreen_btnSend_actionAdapter implements ActionListener {
    private MainScreen adaptee;
    MainScreen_btnSend_actionAdapter(MainScreen adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.btnSend_actionPerformed(e);
    }
}


class MainScreen_txtNome_textAdapter implements TextListener {
    private MainScreen adaptee;
    MainScreen_txtNome_textAdapter(MainScreen adaptee) {
        this.adaptee = adaptee;
    }

    public void textValueChanged(TextEvent e) {
        adaptee.txtNome_textValueChanged(e);
    }
}
