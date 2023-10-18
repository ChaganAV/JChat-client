import java.io.*;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.UnknownHostException;

public class Client implements Sender{
    private String url = "127.0.0.1";
    private int port = 55555;
    private String response;
    private boolean connection;
    private final String ErrorServer = "Сервер не доступен";
    private Socket client;

    @Override
    public void connected() {
        try {
            client = new Socket(getUrl(), getPort());
            setConnection(true);
        }catch (UnknownHostException e){
            System.out.println("Не удается найти сервер");
        }catch (IOException e){
            System.out.println("Ошибка при подключении к серверу");
        }
    }
    public void disconnected(){
        try {
            client.close();
            setConnection(false);
        }catch (IOException e){
            System.out.println("Ошибка при отключении от сервера");
        }
    }
    public String getResponse() {
        return response;
    }

    @Override
    public void sendMessage(String msg) {
        if(connection) {
            try {
                InputStream in = client.getInputStream();
                PrintWriter pout = new PrintWriter(client.getOutputStream(), true);
                pout.println(msg);
                pout.flush();
                BufferedReader bin = new BufferedReader(new InputStreamReader(in));
                response = bin.readLine();
            } catch (UnknownHostException e) {
                setResponse("Сервер недоступен UnknownHostException\n");
            } catch (IOException e) {
                setResponse("Сервер недоступен IOException\n");
            }
        }else {
            setResponse("Сервер не подключен");
        }
    }

    // region getters

    public String getErrorServer() {
        return ErrorServer;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public boolean getConnection() {
        return connection;
    }

    public void setConnection(boolean connected) {
        connection = connected;
    }
    // endregion
}
