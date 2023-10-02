import javax.swing.*;
import javax.swing.text.Document;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;

public class ChatClient extends JFrame {
    private static final int WINDOW_HEIGHT = 420;
    private static final int WINDOW_WIDTH = 360;
    private static final int WINDOW_POSX = 800;
    private static final int WINDOW_POSY = 300;

    // header
    JPanel pnlHeader = new JPanel(new GridLayout(2,1));
    JPanel pnlAddress = new JPanel(new GridLayout(1,3));
    JPanel pnlLogin = new JPanel(new GridLayout(1,3));
    JPanel pnlFooter = new JPanel(new GridLayout(1,5));
    JTextField textAddress = new JTextField("127.0.0.1");
    JTextField textPort = new JTextField("56565");
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
        btnInput.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                textMessages.append(textInput.getText()+"\n");
                textInput.setText("");
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
        setTitle("Chat server");
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
    public void keyEnter(ActiveEvent e){

    }
}