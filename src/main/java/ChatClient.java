import javax.swing.*;
import javax.swing.text.Document;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;

public class ChatClient extends JFrame {
    // region final
    private static final int WINDOW_HEIGHT = 420;
    private static final int WINDOW_WIDTH = 360;
    private static final int WINDOW_POSX = 800;
    private static final int WINDOW_POSY = 300;
//    private static final String URL = "158.160.82.178";
//    private static final int PORT = 55555;
    // endregion

    // region header
    private Client client;
    JPanel pnlHeader = new JPanel(new GridLayout(2,1));
    JPanel pnlAddress = new JPanel(new GridLayout(1,3));
    JPanel pnlLogin = new JPanel(new GridLayout(1,3));
    JPanel pnlFooter = new JPanel(new GridLayout(1,5));
    JTextField textAddress;
    JTextField textPort;
    JTextField textLogin = new JTextField("NICK");
    JButton btnBoard = new JButton("Disconnected");
    Color colorDefault = btnBoard.getBackground();
    JTextField textPassword = new JTextField();
    JButton btnLogin = new JButton("Connect");
    // endregion

    // region center
    JPanel pnlCenter = new JPanel();
    JTextArea textMessages = new JTextArea();
    Document doc = textMessages.getDocument();
    // endregion

    // region footer
    Container boxHorizonal = Box.createHorizontalBox();
    JTextField textInput = new JTextField(20);
    JButton btnInput = new JButton("send");
    // endregion

    ChatClient(Client client){
        this.client = client;
        setting();

        textAddress = new JTextField(client.getUrl());
        textPort = new JTextField(String.valueOf(client.getPort()));

        pnlAddress.add(textAddress);
        pnlAddress.add(textPort);
        btnBoard.setEnabled(false);
        pnlAddress.add(btnBoard);

        pnlLogin.add(textLogin);
        pnlLogin.add(textPassword);
        pnlLogin.add(btnLogin);

        pnlHeader.add(pnlAddress);
        pnlHeader.add(pnlLogin);

        textInput.setSize(new Dimension(300,35));
        textInput.setFont(new Font("Times New Roman",Font.BOLD,18));
        // region actions
        btnLogin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(!client.getConnection()) {
                    connected();
                }else{
                    client.disconnected();
                    btnBoard.setBackground(colorDefault);
                    btnBoard.setText("Disconnected");
                    btnLogin.setText("Login");
                }
            }
        });
        textInput.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(e.getActionCommand() != null) {
                    if(textInput.getText().trim().length()>0) {
                        sendMessage(textInput.getText());
                        textMessages.append(textInput.getText() + "\n");
                        textInput.setText("");
                    }
                }
            }
        });
        btnInput.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                if(textInput.getText().trim().length()>0) {
                    sendMessage(textInput.getText());
                    textMessages.append(textInput.getText() + "\n");
                    textInput.setText("");
                }
            }

            void revalidate() {

            }
        });
        // endregion

        boxHorizonal.add(textInput);
        boxHorizonal.add(btnInput);
        pnlFooter.add(boxHorizonal);

        pnlCenter.add(textMessages);

        add(pnlHeader,BorderLayout.NORTH);
        add(pnlCenter,BorderLayout.CENTER);
        add(pnlFooter,BorderLayout.SOUTH);
        revalidate();
        setVisible(true);
    }
    private void setting(){
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocation(WINDOW_POSX, WINDOW_POSY);
        setSize(WINDOW_WIDTH,WINDOW_HEIGHT);
        setTitle("Chat client");
        setResizable(false); // запрет на изменение размера
    }
    private Component settingHeader(){
        JPanel panel = new JPanel(new GridLayout(2,1));
        pnlAddress.add(textAddress);
        pnlAddress.add(textPort);
        pnlAddress.add(Box.createHorizontalStrut(10));

        pnlLogin.add(textLogin);
        pnlLogin.add(textPassword);
        pnlLogin.add(btnLogin);

        pnlHeader.add(pnlAddress);
        pnlHeader.add(pnlLogin);
        return panel;
    }
    private void getMessage(){
        String msg = client.getResponse();
        textMessages.append(msg + "\n");
    }
    private void connected(){
            client.connected();
            if (client.getConnection()) {
                btnBoard.setBackground(Color.GREEN);
                btnBoard.setText("Connected");
                btnLogin.setText("Logout");
                //getLog();
            } else {
                textMessages.append(client.getErrorServer() + "\n");
            }
    }
    private void sendMessage(String msg){
        client.sendMessage(msg);
        getMessage();
    }
}