import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;

public class Server extends Thread{
    private String IP;
    private int port;
    private List<String> resources;
    private Socket sock;
    private int superPeerPort;



    public Server( String ip, int port, int localPort, List<String> resourceList) throws IOException {
        setIP(ip);
        setPort(localPort);
        setResources(resourceList);
        setSock(new Socket(ip,port));
        setSuperPeerPort(port);
    }

    @Override
    public void run() {
        try {
            listen();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void listen() throws IOException {
        do {
            ServerSocket serverSocket = new ServerSocket(port);
            Socket socket = serverSocket.accept();
            ServerThread thread = new ServerThread(socket,resources);
            thread.start();
        }while (true);
    }

    void register() throws IOException {
        OutputStream outputStream = sock.getOutputStream();
        PrintWriter out = new PrintWriter(outputStream, true);
        out.println(ServiceType.REGISTER.getValue());
        //out.println(sock.getLocalAddress().getHostAddress());
        out.println("test.mp4");
        out.println(getPort());

        sock.close();
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

    public Socket getSock() {
        return sock;
    }

    public void setSock(Socket sock) {
        this.sock = sock;
    }

    public int getSuperPeerPort() {
        return superPeerPort;
    }

    public void setSuperPeerPort(int superPeerPort) {
        this.superPeerPort = superPeerPort;
    }
}
