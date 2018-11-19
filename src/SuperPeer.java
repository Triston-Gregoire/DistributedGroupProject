import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.CopyOnWriteArrayList;

public class SuperPeer {
    private ServerSocket serverSocket;
    private CopyOnWriteArrayList<PeerCB> peerTable = new CopyOnWriteArrayList<>();
    private CopyOnWriteArrayList<SuperPeerCB> superPeerTable = new CopyOnWriteArrayList<>();
    public SuperPeer(){

    }

    public static void main(String[] args) {
        SuperPeer s = new SuperPeer();
        SuperPeerCB superPeerCB = new SuperPeerCB("192.168.1.66", 6000);
        s.superPeerTable.add(superPeerCB);
        try {
            s.listen(6000);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void listen(int port) throws IOException {
        serverSocket = new ServerSocket(port);
        do{
            Socket sock = serverSocket.accept();
            SuperPeerThread thread = new SuperPeerThread(peerTable, superPeerTable, sock);
            thread.start();

        }while (true);
    }
}
