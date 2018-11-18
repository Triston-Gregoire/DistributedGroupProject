import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;

public class Server extends Thread{
    private String IP;
    private int port;
    private List<String> resources;
    public Server( int port, List<String> resourceList) throws IOException {
        setPort(port);
        setResources(resourceList);
    }

    @Override
    public void run() {
        try {
            listen(port);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void listen(int port) throws IOException {
        do {
            ServerSocket serverSocket = new ServerSocket(port);
            Socket sock = serverSocket.accept();
            ServerThread thread = new ServerThread(sock,resources);
            thread.start();
        }while (true);
    }

    public String getIP() {
        return IP;
    }

    public void setIP(String IP) {
        this.IP = IP;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public List<String> getResources() {
        return resources;
    }

    public void setResources(List<String> resources) {
        this.resources = resources;
    }
}
