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
    private static final int WINDOW_HEIGHT = 420;
    private static final int WINDOW_WIDTH = 360;
    private static final int WINDOW_POSX = 800;
    private static final int WINDOW_POSY = 300;
    private static final String URL = "192.168.9.104";
    private static final int PORT = 56565;

    // header
    JPanel pnlHeader = new JPanel(new GridLayout(2,1));
    JPanel pnlAddress = new JPanel(new GridLayout(1,3));
    JPanel pnlLogin = new JPanel(new GridLayout(1,3));
    JPanel pnlFooter = new JPanel(new GridLayout(1,5));
    JTextField textAddress = new JTextField(URL);
    JTextField textPort = new JTextField(String.valueOf(PORT));
    JTextField textLogin = new JTextField();
    JTextField textPassword = new JTextField();
    JButton btnLogin = new JButton("login");

    // центр
    JPanel pnlCenter = new JPanel();
    JTextArea textMessages = new JTextArea();
    Document doc = textMessages.getDocument();

    // footer
    Container boxHorizonal = Box.createHorizontalBox();
    JTextField textInput = new JTextField(20);
    JButton btnInput = new JButton("send");

    ChatClient(){
        setting();

        pnlAddress.add(textAddress);
        pnlAddress.add(textPort);
        pnlAddress.add(Box.createHorizontalStrut(10));

        pnlLogin.add(textLogin);
        pnlLogin.add(textPassword);
        pnlLogin.add(btnLogin);

        pnlHeader.add(pnlAddress);
        pnlHeader.add(pnlLogin);

        textInput.setSize(new Dimension(300,35));
        textInput.setFont(new Font("Times New Roman",Font.BOLD,18));
        textInput.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(e.getActionCommand() != null) {
                    if(textInput.getText().trim().length()>0) {
                        if(sendMessage(textInput.getText().trim())){
                            textMessages.append(textInput.getText() + "\n");
                            textInput.setText("");
                        }
                    }
                }
            }
        });
        btnInput.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                if(textInput.getText().trim().length()>0) {
                    if(sendMessage(textInput.getText().trim())) {
                        textMessages.append(textInput.getText() + "\n");
                        textInput.setText("");
                    }
                }
            }

            void revalidate() {

            }
        });

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
    private void responseMessage(String msg){
        textMessages.append(msg + "\n");
    }
    private boolean sendMessage(String msg){
        Socket client;
        try {
            client = new Socket(textAddress.getText(),Integer.parseInt(textPort.getText()));
            InputStream in = client.getInputStream();
            PrintWriter pout = new PrintWriter(client.getOutputStream(), true);
            pout.println(msg);
            pout.flush();
            BufferedReader bin = new BufferedReader(new InputStreamReader(in));
            String response = bin.readLine();
            responseMessage(response);
            in.close();
            bin.close();
            pout.close();
            client.close();
            return true;
        }catch (UnknownHostException e) {
            textMessages.append("Сервер недоступен\n");
            return false;
        }catch (IOException e){
            textMessages.append("Сервер недоступен\n");
            return false;
        }
    }
}