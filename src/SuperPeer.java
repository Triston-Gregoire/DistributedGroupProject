import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.CopyOnWriteArrayList;

public class SuperPeer {
    ServerSocket serverSocket;
    CopyOnWriteArrayList<PeerCB> peerTable = new CopyOnWriteArrayList<>();
    CopyOnWriteArrayList<SuperPeerCB> superPeerTable = new CopyOnWriteArrayList<>();
    public SuperPeer(){

    }

    public static void main(String[] args) {
        SuperPeer s = new SuperPeer();
        SuperPeerCB superPeerCB = new SuperPeerCB("192.168.1.76", 6001);
        s.superPeerTable.add(superPeerCB);
        try {
            s.listen(6000);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void listen(int port) throws IOException {
        serverSocket = new ServerSocket(port);
        do{
            Socket sock = serverSocket.accept();
            SuperPeerThread thread = new SuperPeerThread(peerTable, superPeerTable, sock);
            thread.start();

        }while (true);
    }
}
