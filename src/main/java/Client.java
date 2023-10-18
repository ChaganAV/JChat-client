import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client implements Sender{
    private String url = "127.0.0.1";
    private int port = 55555;
    private String response;
    private boolean connection;
    private final String ErrorServer = "Сервер не доступен";

    @Override
    public void connected() {
        setConnection(true);
    }
    public void disconnected(){
        setConnection(false);
    }
    public String getResponse() {
        return response;
    }

    @Override
    public void sendMessage(String msg) {
        Socket client;
        try {
            client = new Socket(getUrl(),getPort());
            InputStream in = client.getInputStream();
            PrintWriter pout = new PrintWriter(client.getOutputStream(), true);
            pout.println(msg);
            pout.flush();
            BufferedReader bin = new BufferedReader(new InputStreamReader(in));
            response = bin.readLine();
            in.close();
            bin.close();
            pout.close();
            client.close();
        }catch (UnknownHostException e) {
            setResponse("Сервер недоступен\n");
        }catch (IOException e){
            setResponse("Сервер недоступен\n");
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
