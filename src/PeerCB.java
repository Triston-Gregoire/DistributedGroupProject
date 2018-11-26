/*
 * @Author Triston Gregoire
 */
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class PeerCB {
    private Socket socket;
    private List<FileCB> resource = new ArrayList<>();
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
        setResource(parseList(resource));
        setPort(port);
    }

    public List<FileCB> parseList(List<String> list){
        List<FileCB> fileList = new ArrayList<>();
        FileCB file = new FileCB();
        for (int i = 0; i < list.size(); i = i+3) {
            String currentString = list.get(0);
            if (ServiceType.META.getValue().equals(currentString)){
                fileList.add(new FileCB(list.get(i+1), Integer.parseInt(list.get(i+2))));
                continue;
            }
        }
    return fileList;
    }

    public Socket getSocket() {
        return socket;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    public List<FileCB> getResource() {
        return resource;
    }

    public void setResource(List<FileCB> resource) {
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
