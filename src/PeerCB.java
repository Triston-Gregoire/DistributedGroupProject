import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class PeerCB {
    private Socket socket;
    private List<String> resource = new ArrayList<>();
    private String IP;
    private int port;
    private int size;

    public PeerCB(String ip, String port){
        setPort(port);
        setIP(ip);
    }
    public PeerCB(Socket sock, List<String> resource, String port){
        setSocket(sock);
        setIP(sock.getInetAddress().getHostAddress());
        setResource(resource);
        setPort(port);
    }

    public Socket getSocket() {
        return socket;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    public List<String> getResource() {
        return resource;
    }

    public void setResource(List<String> resource) {
        this.resource = resource;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }
    public void setPort(String port){
        this.port = Integer.parseInt(port);
    }

    public String getIP() {
        return IP;
    }

    public void setIP(String IP) {
        this.IP = IP;
    }
}
